package com.imooc.passbook.passbook.vo;

import com.imooc.passbook.passbook.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户领取的优惠券信息
 * @Author liforever
 * @Date 2019/3/27 15:07
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassInfo {

    /**
     * 优惠券-用户  关系
     */
    private Pass pass;
    /**
     * 优惠券对应的模板
     */
    private PassTemplate passTemplate;
    /**
     * 优惠券对应的商户
     */
    private Merchants merchants;
}
