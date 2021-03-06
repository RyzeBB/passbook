package com.imooc.passbook.passbook.service.impl;

import com.imooc.passbook.passbook.constant.Constants;
import com.imooc.passbook.passbook.dao.MerchantsDao;
import com.imooc.passbook.passbook.entity.Merchants;
import com.imooc.passbook.passbook.mapper.PassTemplateRowMapper;
import com.imooc.passbook.passbook.service.IInventoryService;
import com.imooc.passbook.passbook.service.IUserPassService;
import com.imooc.passbook.passbook.utils.RowKeyGenUtil;
import com.imooc.passbook.passbook.vo.*;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.LongComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取库存信息，只返回用户没有领取的   扩展：分页  redis缓存
 * @Author liforever
 * @Date 2019/3/27 20:36
 **/
@Slf4j
@Service
public class InventoryServiceImpl implements IInventoryService {

    private final MerchantsDao merchantsDao;
    private final HbaseTemplate hbaseTemplate;
    private final IUserPassService userPassService;

    @Autowired
    public InventoryServiceImpl(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao, IUserPassService userPassService) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
        this.userPassService = userPassService;
    }

    /**
     * 获取库存信息，排除已领取后剩余可领取的
     *
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public Response getInventoryInfo(Long userId) throws Exception {
        //用户已领取的所有优惠券,包含pass领取信息
        Response allUserPass = userPassService.getUserAllPassInfo(userId);
        List<PassInfo> passInfos = (List<PassInfo>)allUserPass.getData();
        List <PassTemplate> excludeObject = passInfos.stream().map(
                PassInfo::getPassTemplate
        ).collect(Collectors.toList());
        List<String> excludeIds = new ArrayList <>();

        excludeObject.forEach(e -> excludeIds.add(RowKeyGenUtil.genPassTemplateRowKey(e)));

        return new Response(new InventoryResponse(userId,buildPassTemplateInfo(getAvailablePassTemplate(excludeIds))));
    }

    /**
     * 获取系统中可用的优惠券，排除用户已领取的优惠券
     * @param excludeIds 需要排除的优惠券
     * @return {@link PassTemplate}
     */
    private List<PassTemplate> getAvailablePassTemplate(List<String> excludeIds){

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);
        filterList.addFilter(new SingleColumnValueFilter(
                Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C),
                Bytes.toBytes(Constants.PassTemplateTable.LIMIT),
                CompareFilter.CompareOp.GREATER,
                new LongComparator(0L)
        ));

        filterList.addFilter(new SingleColumnValueFilter(
                Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C),
                Bytes.toBytes(Constants.PassTemplateTable.LIMIT),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("-1")
        ));

        Scan scan  = new Scan();
        scan.setFilter(filterList);
        List<PassTemplate> validTemplates = hbaseTemplate.find(
                Constants.PassTemplateTable.TABLE_NAME,scan,new PassTemplateRowMapper()
        );

        List<PassTemplate> avaliablePassTemplates = new ArrayList <>();
        Date cur = new Date();
        for (PassTemplate validTemplate : validTemplates){
            if(excludeIds.contains(RowKeyGenUtil.genPassTemplateRowKey(validTemplate))){
                continue;
            }
            if(cur.getTime()>=validTemplate.getStart().getTime() && cur.getTime() <= validTemplate.getEnd().getTime()){
                avaliablePassTemplates.add(validTemplate);
            }
        }
        return avaliablePassTemplates;

    }

    /**
     * 构造优惠券信息
     * @param passTemplates {@link PassTemplate}
     * @return {@link PassTemplateInfo}
     */
    private List<PassTemplateInfo> buildPassTemplateInfo(List<PassTemplate> passTemplates){

        Map<Integer, Merchants> merchantsMap = new HashMap <>();
        List <Integer> merchantsIds = passTemplates.stream().map(
                PassTemplate::getId
        ).collect(Collectors.toList());

        List <Merchants> merchants = merchantsDao.findByIdIn(merchantsIds);
        merchants.forEach(m -> merchantsMap.put(m.getId(),m));

        List<PassTemplateInfo> result = new ArrayList <>(passTemplates.size());
        for (PassTemplate passTemplate : passTemplates){
            Merchants mc = merchantsMap.getOrDefault(passTemplate.getId(), null);
            if(null == mc){
                log.error("Merchants Error : {}",passTemplate.getId());
                continue;
            }
            result.add(new PassTemplateInfo(passTemplate,mc));
        }
        return result;
    }
}
