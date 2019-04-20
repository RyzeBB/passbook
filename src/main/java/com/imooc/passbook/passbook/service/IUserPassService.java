package com.imooc.passbook.passbook.service;

import com.imooc.passbook.passbook.vo.Pass;
import com.imooc.passbook.passbook.vo.Response;

/**
 * 获取用户个人优惠券信息，包含all,unused,used 三种状态。  unused 不等用于库存信息
 * @Author liforever
 * @Date 2019/3/27 15:20
 **/
public interface IUserPassService {
    /**
     * 获取用户当前可以消费的优惠券信息,即我的优惠券功能实现
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response getUserPassInfo(Long userId) throws Exception;

    /**
     * 获取用户已经消费的优惠券信息，即已使用优惠券功能实现
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response getUserUsedPassInfo(Long userId) throws Exception;

    /**
     * 获取用户所有的优惠券信息
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    Response getUserAllPassInfo(Long userId) throws Exception;

    /**
     * 用户使用优惠券
     * @param pass {@link Pass}
     * @return {@link Response}
     */
    Response userUsePass(Pass pass);
}
