package com.imooc.passbook.passbook.service;

import com.alibaba.fastjson.JSON;
import com.imooc.passbook.passbook.vo.GainPassTemplateRequest;
import com.imooc.passbook.passbook.vo.PassTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 用户领取优惠券服务测试
 * @Author liforever
 * @Date 2019/4/3 21:28
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class GainPassTemplaterServiceTest extends AbstractServiceTest{

    @Autowired
    private IGainPassTemplateService iGainPassTemplateService;
    @Test
    public void testGainPassTemplate() throws Exception{
        PassTemplate target = new PassTemplate();
        target.setId(2);
        target.setTitle("title:慕课");
        target.setHasToken(true);

        System.out.println(
                JSON.toJSONString(
                        iGainPassTemplateService.gainPassTemplate(new GainPassTemplateRequest(userId,target))
                )
        );
    }
}
