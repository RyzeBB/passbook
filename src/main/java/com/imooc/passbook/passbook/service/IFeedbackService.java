package com.imooc.passbook.passbook.service;

import com.imooc.passbook.passbook.vo.Feedback;
import com.imooc.passbook.passbook.vo.Response;

/**
 * 评论功能：即用户评论相关功能实现
 * @Author liforever
 * @Date 2019/3/27 14:27
 **/
public interface IFeedbackService {
    /**
     * 创建评论
     * @param feedback {@link Feedback}
     * @return {@link Response}
     */
    Response createFeedback(Feedback feedback);

    /**
     * 获取用户评论
     * @param userId 用户id
     * @return {@link Response}
     */
    Response getFeedback(Long userId);

}
