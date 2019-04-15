package com.imooc.passbook.passbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户领取的优惠券
 * @Author liforever
 * @Date 2019/3/27 9:15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pass {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * pass 在HBase 中的RowKey
     */
    private String rowKey;
    /**
     * PassTemplate 在HBase RowKey
     */
    private String templateId;
    /**
     * 优惠券token,有可能是null, 则填充-1
     */
    private String token;
    /**
     * 领取日期
     */
    private Date assignedDate;
    /**
     * 消费日期
     */
    private Date conDate;
}
