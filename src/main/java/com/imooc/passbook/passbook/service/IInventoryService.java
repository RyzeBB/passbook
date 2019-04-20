package com.imooc.passbook.passbook.service;

import com.imooc.passbook.passbook.vo.Response;

/**
 * 获取库存信息：只返回用户没有领取的,且有效的，即优惠券库存功能实现接口定义
 * @Author liforever
 * @Date 2019/3/27 15:12
 **/
public interface IInventoryService {

    /**
     * 获取库存信息
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response getInventoryInfo(Long userId) throws Exception;
}
