package com.yiyun.web.order.service.impl;

import com.yiyun.dao.cluster.ClusterOOrderDao;
import com.yiyun.dao.master.OOrderDao;
import com.yiyun.domain.OOrder;
import com.yiyun.web.order.service.OOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OOrderServiceImpl implements OOrderService {
    @Autowired
    private OOrderDao oOrderDao;

    @Autowired
    private ClusterOOrderDao clusterOOrderDao;

    @Override
    public OOrder get(Long id) {
        return clusterOOrderDao.get(id);
    }

    @Override
    public List<OOrder> list(Map<String, Object> map) {
        return clusterOOrderDao.listResp(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterOOrderDao.countResp(map);
    }

    @Override
    public int save(OOrder oOrder) {
        return oOrderDao.save(oOrder);
    }

    @Override
    public int update(OOrder oOrder) {
        return oOrderDao.update(oOrder);
    }

    @Override
    public int remove(Long id) {
        return oOrderDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return oOrderDao.batchRemove(ids);
    }

}
