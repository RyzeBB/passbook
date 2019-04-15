package com.imooc.passbook.passbook.constant;

/**
 * 优惠券的状态
 * @Author liforever
 * @Date 2019/3/26 20:58
 **/
public enum PassStatus {
    UNUSED(1,"未被使用的"),
    USED(2,"已经使用的"),
    ALL(3,"全部领取的");

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态描述
     */
    private String desc;

    PassStatus(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
