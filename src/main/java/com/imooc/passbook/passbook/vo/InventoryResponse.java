package com.imooc.passbook.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 库存请求响应
 * @Author liforever
 * @Date 2019/3/27 14:59
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponse {

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 优惠券模板信息   用户能够看到的且没有过期的
     */
    private List<PassTemplateInfo> passTemplateInfos;
}
