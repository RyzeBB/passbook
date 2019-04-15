package com.imooc.passbook.passbook.service;

import com.alibaba.fastjson.JSON;
import com.imooc.passbook.passbook.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试有问题
 * 创建用户test
 * @Author liforever
 * @Date 2019/3/29 13:39
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserService {

    @Autowired
    private IUserService iUserService;
    @Test
    //{"data":{"baseInfo":{"age":12,"name":"imooc-1","sex":"w"},"id":24643151,"otherInfo":{"address":"银川","phone":"1111"}}
    public void testCreateUser() throws Exception{
        User user = new User();
        user.setBaseInfo(
                new User.BaseInfo("imooc-1",12,"w")
        );
        user.setOtherInfo(
                new User.OtherInfo("1111","银川")
        );

        System.out.println(JSON.toJSONString(iUserService.createUser(user)));
    }
}
