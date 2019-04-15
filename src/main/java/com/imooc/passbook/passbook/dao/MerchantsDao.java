package com.imooc.passbook.passbook.dao;


import com.imooc.passbook.passbook.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * MerchantsDao 接口
 */
public interface MerchantsDao extends JpaRepository<Merchants,Integer> {

    /**
     * 通过 id 获取商户对象
     * @param id 商户id
     * @return {@link Merchants}
     */
    Merchants findById(Integer id);


    /**
     * 通过商户名称获取商户对象
     * @param name 商户名称
     * @return {@link Merchants}
     */
    Merchants findByName(String name);

    /**
     * 根据商户ids 获取商户对象
     * @param ids 商户ids
     * @return {@link Merchants}
     */
    List<Merchants> findByIdIn(List<Integer> ids);
}
