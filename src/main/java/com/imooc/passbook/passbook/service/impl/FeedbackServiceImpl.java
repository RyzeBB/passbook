package com.imooc.passbook.passbook.service.impl;

import com.alibaba.fastjson.JSON;
import com.imooc.passbook.passbook.constant.Constants;
import com.imooc.passbook.passbook.mapper.FeedbackRowMapper;
import com.imooc.passbook.passbook.service.IFeedbackService;
import com.imooc.passbook.passbook.utils.RowKeyGenUtil;
import com.imooc.passbook.passbook.vo.Feedback;
import com.imooc.passbook.passbook.vo.Response;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论功能实现
 * @Author liforever
 * @Date 2019/3/27 14:31
 **/
@Slf4j
@Service
public class FeedbackServiceImpl implements IFeedbackService {
    private final HbaseTemplate hbaseTemplate;

    @Autowired
    public FeedbackServiceImpl(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    /**
     * 创建评论
     *
     * @param feedback {@link Feedback}
     * @return {@link Response}
     */
    @Override
    public Response createFeedback(Feedback feedback) {
        if(!feedback.validate()){
            log.error("Feedback Error: {}", JSON.toJSONString(feedback));
            return Response.failure("Feedback Error");
        }

        Put put = new Put(Bytes.toBytes(RowKeyGenUtil.genFeedbackRowKey(feedback)));
        put.addColumn(
                Bytes.toBytes(Constants.FeedbackTable.FAMILY_I),
                Bytes.toBytes(Constants.FeedbackTable.USER_ID),
                Bytes.toBytes(feedback.getUserId())
        );
        put.addColumn(
                Bytes.toBytes(Constants.FeedbackTable.FAMILY_I),
                Bytes.toBytes(Constants.FeedbackTable.TYPE),
                Bytes.toBytes(feedback.getType())
        );
        put.addColumn(
                Bytes.toBytes(Constants.FeedbackTable.FAMILY_I),
                Bytes.toBytes(Constants.FeedbackTable.TEMPLATE_ID),
                Bytes.toBytes(feedback.getTemplateId())
        );
        put.addColumn(
                Bytes.toBytes(Constants.FeedbackTable.FAMILY_I),
                Bytes.toBytes(Constants.FeedbackTable.COMMENT),
                Bytes.toBytes(feedback.getComment())
        );

        hbaseTemplate.saveOrUpdate(Constants.FeedbackTable.TABLE_NAME,put);
        return Response.success();
    }

    /**
     * 获取用户评论
     *
     * @param userId 用户id
     * @return {@link Response}
     *
     * hbaseTemplate.get 获取一条 获取不到抛出异常
     * hbaseTemplate.find 获取多条  获取不到返回null
     *
     */
    @Override
    public Response getFeedback(Long userId) {
        byte[] reverseUserId = new StringBuilder(String.valueOf(userId)).reverse().toString().getBytes();
        Scan scan = new Scan();
        scan.setFilter(new PrefixFilter(reverseUserId));
        List <Feedback> feedbacks = hbaseTemplate.find(Constants.FeedbackTable.TABLE_NAME, scan, new FeedbackRowMapper());

        return new Response(feedbacks);
    }
}
