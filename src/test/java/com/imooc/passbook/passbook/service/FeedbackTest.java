package com.imooc.passbook.passbook.service;

import com.alibaba.fastjson.JSON;
import com.imooc.passbook.passbook.constant.FeedbackType;
import com.imooc.passbook.passbook.vo.Feedback;
import com.imooc.passbook.passbook.vo.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackTest extends AbstractServiceTest{

    @Autowired
    private IFeedbackService iFeedbackService;

    @Test
    public void FeedBackTest(){
       /* Feedback feedback = new Feedback();
        feedback.setUserId(userId);
        feedback.setComment("test feedback");
        feedback.setType(FeedbackType.APP.getCode());
        feedback.setTemplateId("");
        Response response = iFeedbackService.createFeedback(feedback);
        System.out.println(JSON.toJSONString(response));
*/

        Feedback f= new Feedback();
        f.setUserId(userId);
        f.setComment("test feedback");
        f.setType(FeedbackType.PASS.getCode());
        f.setTemplateId("f627660a5318b4c816be14a15b21f8f6");
        iFeedbackService.createFeedback(f);

    }

    @Test
    public void getFeedBack(){
        Response feedback = iFeedbackService.getFeedback(userId);
        System.out.println(JSON.toJSONString(feedback));
    }
}
