package com.yiyun.app.order.service.impl;

import com.yiyun.app.order.service.OReserveService;
import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.ReserveConstants;
import com.yiyun.dao.cluster.ClusterMMemberDao;
import com.yiyun.dao.cluster.ClusterOReserveDao;
import com.yiyun.dao.master.MMemberDao;
import com.yiyun.dao.master.OReserveDao;
import com.yiyun.domain.MMember;
import com.yiyun.domain.OReserve;
import com.yiyun.req.ReserveReq;
import com.yiyun.utils.DateUtil;
import com.yiyun.vo.ReserveDetailResp;
import com.yiyun.vo.ReserveResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class OReserveServiceImpl implements OReserveService {
    @Autowired
    private OReserveDao oReserveDao;

    @Autowired
    private ClusterOReserveDao clusterOReserveDao;

    @Autowired
    private ClusterMMemberDao clusterMMemberDao;

    @Autowired
    private MMemberDao mMemberDao;

    @Override
    public OReserve get(Long id) {
        return clusterOReserveDao.get(id);
    }

    @Override
    public ReserveDetailResp getById(Long id) {
        return clusterOReserveDao.getById(id);
    }

    @Override
    public List<OReserve> list(Map<String, Object> map) {
        return clusterOReserveDao.list(map);
    }

    @Override
    public void commitReserve(ReserveReq req) throws ParseException {

        MMember byPhone = clusterMMemberDao.getByPhone(req.getUserPhone());
        if (null == byPhone) {
            MMember member = new MMember(req.getUserPhone());
            mMemberDao.save(member);
        }

        OReserve oReserve = new OReserve(req.getProductId(), req.getUserPhone(), req.getReserveTime(), req.getLoginPhone());
        oReserveDao.save(oReserve);
    }

    @Override
    public List<ReserveResp> listReserve(String phone) throws ParseException {
        List<ReserveResp> byPhone = clusterOReserveDao.getByPhone(phone);
        for (ReserveResp re : byPhone) {
            re.setStatusRemark(ReserveConstants.getStatusToAppRemark(Integer.valueOf(re.getStatus())));
            re.setStatus(ReserveConstants.getStatusToApp(Integer.valueOf(re.getStatus())));
            if (re.getStatus() == 1 || re.getStatus() == 2) {
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

    @Override
    public void cancelOrUpdateById(ReserveReq req) throws ParseException {
        OReserve oReserve = clusterOReserveDao.get(Long.valueOf(req.getOrderId()));
        if ("1".equals(req.getType())) {//取消操作
            oReserve.setReserveStauts(ReserveConstants.RESERVE_STATUS_YHQX);
        } else {//修改操作
            oReserve.setReserveTime(DateUtil.stringToLong(req.getReserveTime(), DateUtil.YYYY_MM_DD_HH_MM));
            oReserve.setProductId(Long.valueOf(req.getProductId()));
        }
        oReserveDao.update(oReserve);
    }

    @Override
    public Boolean isExistCommitOrder(String userPhone) {
        Integer existCommitOrder = clusterOReserveDao.isExistCommitOrder(userPhone);
        return existCommitOrder > 0;
    }

    @Override
    public void commentOrder(ReserveReq req) {
        OReserve oReserve = clusterOReserveDao.get(Long.parseLong(req.getOrderId()));
        oReserve.setComment(req.getComment());
        oReserve.setCommentTime(System.currentTimeMillis());
        oReserve.setReserveStauts(ReserveConstants.RESERVE_STATUS_YPJ);
        oReserveDao.update(oReserve);
    }

}
