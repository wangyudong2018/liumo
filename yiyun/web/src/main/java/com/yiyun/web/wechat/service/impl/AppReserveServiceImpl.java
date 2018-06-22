package com.yiyun.web.wechat.service.impl;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.ReserveConstants;
import com.yiyun.dao.cluster.ClusterOReserveDao;
import com.yiyun.utils.DateUtil;
import com.yiyun.vo.ReserveResp;
import com.yiyun.web.wechat.service.AppReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class AppReserveServiceImpl implements AppReserveService {

    @Autowired
    private ClusterOReserveDao clusterOReserveDao;

    @Override
    public List<ReserveResp> listReserve(String phone) throws ParseException {
        List<ReserveResp> byPhone = clusterOReserveDao.getByCommitPhone(phone);
        for (ReserveResp re : byPhone) {
            re.setStatus(ReserveConstants.getStatusToApp(Integer.valueOf(re.getStatus())));
            re.setStatusRemark(ReserveConstants.getStatusToAppRemark(Integer.valueOf(re.getStatus())));
            if (re.getStatus() == 2 || re.getStatus() == 1) {
                long l1 = DateUtil.stringToLong(re.getReserveTime(), DateUtil.YYYY_MM_DD_HH_MM) / 1000;
                if (l1 <= System.currentTimeMillis() / 1000) {
                    re.setIsCancel(CommonConstants.NO);
                } else {
                    re.setIsCancel(CommonConstants.YES);
                }
            }
        }
        return byPhone;
    }
}
