package com.imooc.passbook.passbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.passbook.passbook.constant.Constants;
import com.imooc.passbook.passbook.constant.PassStatus;
import com.imooc.passbook.passbook.dao.MerchantsDao;
import com.imooc.passbook.passbook.entity.Merchants;
import com.imooc.passbook.passbook.mapper.PassRowMapper;
import com.imooc.passbook.passbook.service.IUserPassService;
import com.imooc.passbook.passbook.vo.Pass;
import com.imooc.passbook.passbook.vo.PassInfo;
import com.imooc.passbook.passbook.vo.PassTemplate;
import com.imooc.passbook.passbook.vo.Response;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Author liforever
 * @Date 2019/3/27 16:23
 **/
@Slf4j
@Service
public class UserPassServiceImpl implements IUserPassService {

    /**
     * Hbase 客户端
     */
    private final HbaseTemplate hbaseTemplate;

    /**
     *  数据库 orm
     */
    private final MerchantsDao merchantsDao;
    @Autowired
    public UserPassServiceImpl(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
    }

    /**
     * 获取用户当前可以消费的优惠券信息,即我的优惠券功能实现
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @Override
    public Response getUserPassInfo(Long userId) throws Exception {

        return getPassInfoByStatus(userId,PassStatus.UNUSED);
    }

    /**
     * 获取用户已经消费的优惠券信息，即已使用优惠券功能实现
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @Override
    public Response getUserUsedPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId,PassStatus.USED);
    }

    /**
     * 获取用户所有的优惠券信息
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @Override
    public Response getUserAllPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId,PassStatus.ALL);
    }

    /**
     * 用户使用优惠券
     *
     * @param pass {@link Pass}
     * @return {@link Response}
     */
    @Override
    public Response userUsePass(Pass pass) {
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(pass.getUserId())).reverse().toString());
        Scan scan = new Scan();
        List<Filter> filterList = new ArrayList <>();
        filterList.add(new PrefixFilter(rowPrefix));
        filterList.add(new SingleColumnValueFilter(
                Constants.PassTable.FAMILY_I.getBytes(),
                Constants.PassTable.TEMPLATE_ID.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(pass.getTemplateId())
        ));
        filterList.add(new SingleColumnValueFilter(
                Constants.PassTable.FAMILY_I.getBytes(),
                Constants.PassTable.CON_DATE.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("-1")
        ));
        scan.setFilter(new FilterList(filterList));

        List<Pass> passes = hbaseTemplate.find(Constants.PassTable.TABLE_NAME,scan,new PassRowMapper());
        if(null == passes || passes.size() != 1){
            log.error("UserUserPass Error: {}", JSON.toJSONString(passes));
        }

        byte[] FAMILY_I = Constants.PassTable.FAMILY_I.getBytes();
        byte[] CON_DATE = Constants.PassTable.CON_DATE.getBytes();
        List<Mutation> datas = new ArrayList <>();
        Put put = new Put(passes.get(0).getRowKey().getBytes());
        put.addColumn(FAMILY_I,CON_DATE,Bytes.toBytes(DateFormatUtils.ISO_DATE_FORMAT.format(new Date())));
        datas.add(put);
        hbaseTemplate.saveOrUpdates(Constants.PassTable.TABLE_NAME,datas);

        return Response.success();
    }

    /**
     * 根据优惠券状态查询用户已领取的优惠券 unused,used,all
     * @param userId 用户id
     * @param passStatus {@link PassStatus}
     * @return {@link Response}
     */
    private Response getPassInfoByStatus(Long userId, PassStatus passStatus) throws Exception {

        //根据UserId 构造行键前缀
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(userId)).reverse().toString());
        CompareFilter.CompareOp compareOp =
                passStatus == PassStatus.UNUSED ? CompareFilter.CompareOp.EQUAL:CompareFilter.CompareOp.NOT_EQUAL;

        Scan scan = new Scan();
        List<Filter> filterList = new ArrayList <>();
        //1.行键前缀过滤器，找到特定用户的优惠券
        filterList.add(new PrefixFilter(rowPrefix));
        //2.基于列单元值的过滤器，找到未使用的优惠券
        if(passStatus != PassStatus.ALL){
            filterList.add(new SingleColumnValueFilter(
                    Constants.PassTable.FAMILY_I.getBytes(),
                    Constants.PassTable.CON_DATE.getBytes(),
                    compareOp,
                    Bytes.toBytes("-1")));
        }

        scan.setFilter(new FilterList(filterList));
        List<Pass> passes = hbaseTemplate.find(Constants.PassTable.TABLE_NAME,scan, new PassRowMapper());
        Map<String,PassTemplate> passTemplateMap = buildPassTemplateMap(passes);
        Map<Integer,Merchants> merchantsMap = buildMerchantsMap(new ArrayList <>(passTemplateMap.values()));

        List<PassInfo> result = new ArrayList <>();
        for (Pass pass : passes){
            PassTemplate passTemplate = passTemplateMap.getOrDefault(pass.getTemplateId(),null);
            if(null == passTemplate){
                log.error("PassTemplate Null : {}",pass.getTemplateId());
                continue;
            }
            Merchants merchants = merchantsMap.getOrDefault(passTemplate.getId(), null);
            if(null == merchants){
                log.error("Merchants Null : {}",pass.getUserId());
                continue;
            }
            result.add(new PassInfo(pass,passTemplate,merchants));

        }

        return new Response(result);
    }

    /**
     * 通过获取的Pass对象构造 Map
     * @param passes {@link Pass}
     * @return Map {@link PassTemplate}
     * @throws Exception
     */
    private Map<String, PassTemplate> buildPassTemplateMap(List<Pass> passes) throws Exception{

        String[] patterns = new String[]{"yyyy-MM-dd"};

        byte[] FAMILY_B = Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B);
        byte[] ID = Bytes.toBytes(Constants.PassTemplateTable.ID);
        byte[] TITLE = Bytes.toBytes(Constants.PassTemplateTable.TITLE);
        byte[] SUMMARY = Bytes.toBytes(Constants.PassTemplateTable.SUMMARY);
        byte[] DESC = Bytes.toBytes(Constants.PassTemplateTable.DESC);
        byte[] HAS_TOKEN = Bytes.toBytes(Constants.PassTemplateTable.HAS_TOKEN);
        byte[] BACKGROUND = Bytes.toBytes(Constants.PassTemplateTable.BACKGROUND);

        byte[] FAMILE_C = Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C);
        byte[] LIMIT = Bytes.toBytes(Constants.PassTemplateTable.LIMIT);
        byte[] START = Bytes.toBytes(Constants.PassTemplateTable.START);
        byte[] END = Bytes.toBytes(Constants.PassTemplateTable.END);

        List<String> templateIds = passes.stream().map(
                Pass::getTemplateId
        ).collect(Collectors.toList());

        List<Get> templateGets = new ArrayList <>(templateIds.size());
        templateIds.forEach(t -> templateGets.add(new Get(Bytes.toBytes(t))));

        Result[] results = hbaseTemplate.getConnection().getTable(TableName.valueOf(Constants.PassTemplateTable.TABLE_NAME))
                .get(templateGets);

        //构造PassTemplateId -> PassTemplate Object 的map,用于构造PassInfo
        Map<String,PassTemplate> templateId2Object = new HashMap<>();
        for (Result result : results) {
            PassTemplate passTemplate = new PassTemplate();
            passTemplate.setId(Bytes.toInt(result.getValue(FAMILY_B,ID)));
            passTemplate.setTitle(Bytes.toString(result.getValue(FAMILY_B,TITLE)));
            passTemplate.setSummary(Bytes.toString(result.getValue(FAMILY_B,SUMMARY)));
            passTemplate.setDesc(Bytes.toString(result.getValue(FAMILY_B,DESC)));
            passTemplate.setHasToken(Bytes.toBoolean(result.getValue(FAMILY_B,HAS_TOKEN)));
            passTemplate.setBackground(Bytes.toInt(result.getValue(FAMILY_B,BACKGROUND)));

            passTemplate.setLimit(Bytes.toLong(result.getValue(FAMILE_C,LIMIT)));
            passTemplate.setStart(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILE_C,START)),patterns));
            passTemplate.setEnd(DateUtils.parseDate(Bytes.toString(result.getValue(FAMILE_C,END)),patterns));

            templateId2Object.put(Bytes.toString(result.getRow()),passTemplate);
        }

        return templateId2Object;

    }

    /**
     * 通过获取的PassTemplate 对象构造 Merchants Map
     * @param passTemplates {@link PassTemplate}
     * @return  Map {@link Merchants}
     */
    private Map<Integer, Merchants> buildMerchantsMap(List<PassTemplate> passTemplates){
        Map<Integer,Merchants> merchantsMap = new HashMap <>();
        List <Integer> merchantsIds = passTemplates.stream().map(
                PassTemplate::getId
        ).collect(Collectors.toList());
        List <Merchants> merchants = merchantsDao.findByIdIn(merchantsIds);
        merchants.forEach(m -> merchantsMap.put(m.getId(),m));
        return merchantsMap;
    }
}
