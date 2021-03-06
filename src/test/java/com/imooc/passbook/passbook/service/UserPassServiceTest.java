package com.imooc.passbook.passbook.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.imooc.passbook.passbook.vo.Pass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author liforever
 * @Date 2019/4/3 21:46
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserPassServiceTest extends AbstractServiceTest {

    @Autowired
    private IUserPassService iUserPassService;

/*
{
    "data":[
        {
            "merchants":{
                "address":"北京",
                "businessLicenseUrl":"www.imooc.com",
                "id":2,
                "isAudit":true,
                "logoUrl":"www.imooc.com",
                "name":"慕课Imooc",
                "phone":"111111"
            },
            "pass":{
                "assignedDate":1555603200000,
                "templateId":"f627660a5318b4c816be14a15b21f8f6",
                "token":"token - 3",
                "userId":24643151
            },
            "passTemplate":{
                "background":2,
                "desc":"详情：慕课",
                "end":1556208000000,
                "hasToken":true,
                "id":2,
                "limit":9995,
                "start":1555344000000,
                "summary":"简介：慕课",
                "title":"title:慕课"
            }
        },
        {
            "merchants":{
                "address":"北京",
                "businessLicenseUrl":"www.imooc.com",
                "id":2,
                "isAudit":true,
                "logoUrl":"www.imooc.com",
                "name":"慕课Imooc",
                "phone":"111111"
            },
            "pass":{
                "assignedDate":1555516800000,
                "templateId":"f627660a5318b4c816be14a15b21f8f6",
                "token":"token - 2",
                "userId":24643151
            },
            "passTemplate":{
                "background":2,
                "desc":"详情：慕课",
                "end":1556208000000,
                "hasToken":true,
                "id":2,
                "limit":9995,
                "start":1555344000000,
                "summary":"简介：慕课",
                "title":"title:慕课"
            }
        }
    ],
    "errorCode":0,
    "errorMsg":""
}
*/

    /**
     * 剩余可用的优惠券
     * @throws Exception
     */
    @Test
    public void testGainUserPassInfo() throws Exception{
        System.out.println(
                JSON.toJSONString(iUserPassService.getUserPassInfo(userId), SerializerFeature.DisableCircularReferenceDetect)
        );
    }

    /**
     * 已使用的优惠券
     * @throws Exception
     */

/*    {"data":[],"errorCode":0,"errorMsg":""}*/
    @Test
    public void testGainUserUserdPassInfo() throws Exception{
        System.out.println(
                JSON.toJSONString(
                        iUserPassService.getUserUsedPassInfo(userId)
                )
        );
    }

    /**
     * 所有的优惠券
     * @throws Exception
     */

   /* {
        "data": [{
        "merchants": {
            "address": "北京",
                    "businessLicenseUrl": "www.imooc.com",
                    "id": 1,
                    "isAudit": true,
                    "logoUrl": "www.imooc.com",
                    "name": "慕课",
                    "phone": "111111"
        },
        "pass": {
            "assignedDate": 1554220800000,
                    "templateId": "75f85d1a102cb60f44b40b99c3b94855",
                    "token": "token-3",
                    "userId": 24643151
        },
        "passTemplate": {
            "background": 2,
                    "desc": "详情：慕课",
                    "end": 1555084800000,
                    "hasToken": true,
                    "id": 1,
                    "limit": 9999,
                    "start": 1554220800000,
                    "summary": "简介：慕课",
                    "title": "title:慕课"
        }
    }],
        "errorCode": 0,
            "errorMsg": ""
    }*/

    @Test
    public void testGainUserAllPassInfo() throws Exception{
        System.out.println(
                JSON.toJSONString(
                        iUserPassService.getUserAllPassInfo(userId)
                )
        );
    }


    @Test
    public void testUserUsePass() throws Exception{

        Pass pass = new Pass();
        pass.setTemplateId("f627660a5318b4c816be14a15b21f8f6");
        pass.setUserId(24643151L);
        System.out.println(

                JSON.toJSONString(iUserPassService.userUsePass(pass))
        );
    }
}
