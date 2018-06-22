package com.yiyun.app.mapp.service;

import com.yiyun.domain.Loan;

import java.util.List;
import java.util.Map;

/**
 * @title 订单
 * @author Xing
 * @date Fri Jun 08 20:54:55 CST 2018
 */
public interface MLoanService {

    Loan get(Long id);

    List<Loan> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Loan loan);

    int update(Loan loan);

    int remove(Long id);

    int batchRemove(Long[] ids);

    String getOrderSn();
}