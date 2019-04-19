package com.imooc.passbook.passbook.utils;

import com.imooc.passbook.passbook.vo.Feedback;
import com.imooc.passbook.passbook.vo.GainPassTemplateRequest;
import com.imooc.passbook.passbook.vo.PassTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * RowKey 生成器工具类
 * @Author liforever
 * @Date 2019/3/27 10:58
 **/
@Slf4j
public class RowKeyGenUtil {
    /**
     * 根据提供的passtemplate 对象生成RowKey
     * @param passTemplate {@link PassTemplate}
     * @return String RowKey
     */
    public static String genPassTemplateRowKey(PassTemplate passTemplate){
        String passInfo = passTemplate.getId() + "_" + passTemplate.getTitle();
        String rowKey = DigestUtils.md5Hex(passInfo);
        log.info("GenPassTemplateRowKey: {} {}",passInfo,rowKey);
        return rowKey;
    }

    /**
     * 根据提供的领取优惠券请求生成Rowkey,只可以在领取优惠券时使用
     * Pass RowKey = reversed(userId) + inverser(timestamp) + PassTemplate Rowkey (便于判断那些用户领取了该优惠券)
     * @param request {@GainPassTemplateRequest}
     * @return String RowKey
     */
    public static String genPassRowKey(GainPassTemplateRequest request){

        return new StringBuilder(String.valueOf(request.getUserId())).reverse().toString() + Long.valueOf(Long.MAX_VALUE - System.currentTimeMillis()) + genPassTemplateRowKey(request.getPassTemplate());

    }
    /**
     * 根据feedback 构造RowKey
     * @param feedback {@link Feedback}
     * @return String RowKey
     */
    public static String genFeedbackRowKey(Feedback feedback){
        return new StringBuilder(String.valueOf(feedback.getUserId())).reverse().toString()+(Long.MAX_VALUE - System.currentTimeMillis());
    }


    public static void main(String[] args) {
        System.out.printf(DigestUtils.md5Hex("1"));
    }
}
