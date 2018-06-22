package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.Loan;

import java.util.List;
import java.util.Map;

/**
 * @title 订单
 * @author Xing
 * @date Fri Jun 08 20:54:55 CST 2018
 */
public interface ClusterLoanDao extends MyMapper<Loan> {

    Loan get(Long id);

    List<Loan> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    String selectMaxSn(String pref);
}