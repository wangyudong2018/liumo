package com.yiyun.web.mongodb.repository;


import com.yiyun.web.mongodb.entity.RiskData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author YanXiaoGuang
 * @version 1.0
 * @Title 风控数据
 * @Description
 * @createDate 2017年12月1日 下午4:06:49
 * @modifier`
 * @modifyDate
 */
public interface RiskDataRepository extends MongoRepository<RiskData, String> {

    RiskData findByUserId(Long userId);


}
