package com.yiyun.app.mapp.service.impl;

import com.yiyun.app.mapp.service.MLoanService;
import com.yiyun.dao.cluster.ClusterLoanDao;
import com.yiyun.dao.master.LoanDao;
import com.yiyun.domain.Loan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class MLoanServiceImpl implements MLoanService {
    @Autowired
    private LoanDao loanDao;

    @Autowired
    private ClusterLoanDao clusterLoanDao;

    @Override
    public Loan get(Long id) {
        return clusterLoanDao.get(id);
    }

    @Override
    public List<Loan> list(Map<String, Object> map) {
        return clusterLoanDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterLoanDao.count(map);
    }

    @Override
    public int save(Loan loan) {
        return loanDao.save(loan);
    }

    @Override
    public int update(Loan loan) {
        return loanDao.update(loan);
    }

    @Override
    public int remove(Long id) {
        return loanDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return loanDao.batchRemove(ids);
    }

    @Override
    public String getOrderSn() {
        String pref = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String maxSn = clusterLoanDao.selectMaxSn(pref);
        if (StringUtils.isBlank(maxSn)) {//当日还没订单 , 取001
            return pref + "0001";
        }

        Long max = Long.valueOf(maxSn);
        return String.valueOf(max + 1);
    }

}