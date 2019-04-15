package com.imooc.passbook.passbook.service;

import com.imooc.passbook.passbook.vo.PassTemplate;

/**
 * Pass HBase 服务
 * @Author liforever
 * @Date 2019/3/27 12:15
 **/
public interface IHBasePassService {

    /**
     * 将PassTemplate 写入 HBase
     * @param passTemplate {@link PassTemplate}
     * @return true/false
     */
    boolean dropPassTemplateToHBase(PassTemplate passTemplate);
}
