package com.imooc.passbook.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户领取优惠券的请求对象
 * @Author liforever
 * @Date 2019/3/27 15:04
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GainPassTemplateRequest {

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 优惠券对象
     */
    private PassTemplate passTemplate;

}
