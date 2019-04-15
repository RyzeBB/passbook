package com.imooc.passbook.passbook.controller;

import com.imooc.passbook.passbook.log.LogConstants;
import com.imooc.passbook.passbook.log.LogGenerator;
import com.imooc.passbook.passbook.service.IUserService;
import com.imooc.passbook.passbook.vo.Response;
import com.imooc.passbook.passbook.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @Author liforever
 * @Date 2019/3/28 20:56
 **/
@Slf4j
@RestController("/passbook")
public class CreateUserController {
    /**
     * 创建用户服务
     */
    private final IUserService iUserService;

    /**
     * HttpServletRequest
     */
    private final HttpServletRequest httpServletRequest;
    @Autowired
    public CreateUserController(IUserService iUserService, HttpServletRequest httpServletRequest) {
        this.iUserService = iUserService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * 创建用户
     * @param user {@link User}
     * @return {@link Response}
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/createuser")
    Response createUser(@RequestBody User user) throws Exception{
        LogGenerator.genLog(httpServletRequest,-1L, LogConstants.ActionName.CREATE_USER,user);
        return iUserService.createUser(user);
    }
}
