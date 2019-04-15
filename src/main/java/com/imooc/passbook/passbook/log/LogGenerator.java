package com.imooc.passbook.passbook.log;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志生成器
 * @Author liforever
 * @Date 2019/3/27 8:48
 **/
@Slf4j
public class LogGenerator {
    /**
     * 生成log
     * @param request {@link HttpServletRequest}
     * @param userId 用户id
     * @param action 日志类型
     * @param info 日志信息 可以为null
     */
    public static void genLog(HttpServletRequest request,Long userId,String action,Object info){

        log.info(
                JSON.toJSONString(new LogObject(action,userId,System.currentTimeMillis(),request.getRemoteAddr(),info))

        );
    }
}
