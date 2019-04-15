package com.imooc.passbook.passbook.service;

import com.imooc.passbook.passbook.vo.GainPassTemplateRequest;
import com.imooc.passbook.passbook.vo.Response;

/**
 * 用户领取优惠券功能实现
 * @Author liforever
 * @Date 2019/3/27 15:14
 **/
public interface IGainPassTemplateService {
    /**
     * 用户领取优惠券
     * @param request {@link GainPassTemplateRequest}
     * @return {@link Response}
     * @throws Exception
     */
    Response gainPassTemplate(GainPassTemplateRequest request) throws Exception;
}
