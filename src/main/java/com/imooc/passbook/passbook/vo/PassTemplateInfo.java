package com.imooc.passbook.passbook.vo;

import com.imooc.passbook.passbook.entity.Merchants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 优惠券模板信息
 * @Author liforever
 * @Date 2019/3/27 14:57
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PassTemplateInfo extends PassTemplate{

    /**
     * 优惠券模板
     */
    private PassTemplate passTemplate;
    /**
     * 优惠券对应的商户
     */
    private Merchants merchants;
}
