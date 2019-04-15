package com.imooc.passbook.passbook.service;

import com.imooc.passbook.passbook.vo.Response;
import com.imooc.passbook.passbook.vo.User;

/**
 * 用户服务:创建 User 服务
 * @Author liforever
 * @Date 2019/3/27 13:46
 **/
public interface IUserService {

    /**
     * 创建用户
     * @param user {@link User}
     * @return {@link Response}
     * @throws Exception
     */
    Response createUser(User user) throws Exception;
}
