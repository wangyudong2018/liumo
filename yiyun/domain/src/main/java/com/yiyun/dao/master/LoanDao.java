package com.yiyun.dao.master;

import com.yiyun.domain.Loan;

import java.util.List;
import java.util.Map;

import com.yiyun.common.MyMapper;

/**
 * @title 订单
 * @author Xing
 * @date Fri Jun 08 20:54:55 CST 2018
 */
public interface LoanDao extends MyMapper<Loan> {
    int save(Loan loan);

    int update(Loan loan);

    int remove(Long id);

    int batchRemove(Long[] ids);
}