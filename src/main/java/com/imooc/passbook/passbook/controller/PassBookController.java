package com.imooc.passbook.passbook.controller;

import com.imooc.passbook.passbook.log.LogConstants;
import com.imooc.passbook.passbook.log.LogGenerator;
import com.imooc.passbook.passbook.service.IFeedbackService;
import com.imooc.passbook.passbook.service.IGainPassTemplateService;
import com.imooc.passbook.passbook.service.IInventoryService;
import com.imooc.passbook.passbook.service.IUserPassService;
import com.imooc.passbook.passbook.vo.Feedback;
import com.imooc.passbook.passbook.vo.GainPassTemplateRequest;
import com.imooc.passbook.passbook.vo.Pass;
import com.imooc.passbook.passbook.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Passbook Rest Controller
 * @Author liforever
 * @Date 2019/3/28 20:18
 **/
@Slf4j
@RestController
@RequestMapping("/passbook")
public class PassBookController {
    /**
     * 用户优惠券服务
     */
    private final IUserPassService iUserPassService;

    /**
     * 优惠券库存服务
     */
    private final IInventoryService iInventoryService;
    /**
     * 领取优惠券服务
     */
    private final IGainPassTemplateService iGainPassTemplateService;
    /**
     * 用户反馈服务
     */
    private final IFeedbackService iFeedbackService;
    /**
     * HttpServletRequest
     */
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public PassBookController(IUserPassService iUserPassService, IInventoryService iInventoryService, IGainPassTemplateService iGainPassTemplateService, IFeedbackService iFeedbackService, HttpServletRequest httpServletRequest) {
        this.iUserPassService = iUserPassService;
        this.iInventoryService = iInventoryService;
        this.iGainPassTemplateService = iGainPassTemplateService;
        this.iFeedbackService = iFeedbackService;
        this.httpServletRequest = httpServletRequest;
    }


    /**
     * 获取用户的个人的优惠券信息
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/userpassinfo")
    Response userPassInfo(Long userId) throws Exception{
        LogGenerator.genLog(httpServletRequest,userId, LogConstants.ActionName.USER_PASS_INFO,null);
        return iUserPassService.getUserPassInfo(userId);
    }

    /**
     * 获取用户使用了的优惠券信息
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/userusedpassinfo")
    Response userUsedPassInfo(Long userId) throws Exception{
        LogGenerator.genLog(httpServletRequest,userId,LogConstants.ActionName.USER_USED_PASS_INFO,null);
        return iUserPassService.getUserUsedPassInfo(userId);
    }

    /**
     * 用户使用优惠券
     * @param pass {@link Pass}
     * @return {@link Response}
     */
    @ResponseBody
    @RequestMapping("/useruserpass")
    Response userUsePass(Pass pass){
        LogGenerator.genLog(httpServletRequest,pass.getUserId(),LogConstants.ActionName.USER_USE_PASS,pass);
        return iUserPassService.userUsePass(pass);
    }

    /**
     * 获取库存信息
     * @param userId 用户id
     * @return {@link Response}
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/inventoryinfo")
    Response inventoryInfo(Long userId) throws Exception{
        LogGenerator.genLog(httpServletRequest,userId,LogConstants.ActionName.INVENTORY_INFO,null);
        return iInventoryService.getInventoryInfo(userId);
    }

    /**
     * 用户领取优惠券
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/gainpasstemplate")
    Response gainPassTemplate(@RequestBody GainPassTemplateRequest request) throws Exception{
        LogGenerator.genLog(httpServletRequest,request.getUserId(),LogConstants.ActionName.GAIN_PASS_TEMPLATE,request);
        return iGainPassTemplateService.gainPassTemplate(request);
    }

    /**
     * 用户创建评论
     * @param feedback
     * @return
     */
    @ResponseBody
    @RequestMapping("/createfeedback")
    Response createFeedback(Feedback feedback){
        LogGenerator.genLog(httpServletRequest,feedback.getUserId(),LogConstants.ActionName.CREATE_FEEDBACK,feedback);
        return iFeedbackService.createFeedback(feedback);
    }

    /**
     * 用户获取评论
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping
    Response getFeedback(Long userId){
        LogGenerator.genLog(httpServletRequest,userId,LogConstants.ActionName.GET_FEEDBACK,null);
        return iFeedbackService.getFeedback(userId);
    }

    /**
     * 异常演示接口
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/exception")
    Response exception() throws Exception{
        throw new Exception("Welcome to IMOOC");
    }
}
