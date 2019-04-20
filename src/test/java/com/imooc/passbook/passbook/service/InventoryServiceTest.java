package com.imooc.passbook.passbook.service;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 库存服务测试   用户可以领取的优惠券
 * @Author liforever
 * @Date 2019/4/3 20:46
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryServiceTest extends AbstractServiceTest{
    @Autowired
    private IInventoryService iInventoryService;
    @Test
    public void testGetInventoryInfo() throws Exception{
        System.out.println(
                JSON.toJSONString(iInventoryService.getInventoryInfo(userId))
        );
    }
}
