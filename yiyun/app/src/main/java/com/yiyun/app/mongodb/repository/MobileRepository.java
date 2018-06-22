package com.yiyun.app.mongodb.repository;


import com.yiyun.app.mongodb.entity.Mobile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Title 运营商mongodb 查询 存储方法
 * @Description
 * @author YanXiaoGuang
 * @createDate 2017年12月1日 下午4:06:49
 * @modifier`
 * @modifyDate
 * @version 1.0
 */
    public interface MobileRepository extends MongoRepository<Mobile, String> {

        Mobile findByMemberId(Long memberId);


    }
