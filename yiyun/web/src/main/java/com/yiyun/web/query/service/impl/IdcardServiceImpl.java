package com.yiyun.web.query.service.impl;

import com.yiyun.dao.cluster.ClusterIdcardDao;
import com.yiyun.dao.master.IdcardDao;
import com.yiyun.domain.Idcard;
import com.yiyun.web.query.service.IdcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IdcardServiceImpl implements IdcardService {
    @Autowired
    private IdcardDao idcardDao;

    @Autowired
    private ClusterIdcardDao clusterIdcardDao;

    @Override
    public Idcard get(Long id) {
        return clusterIdcardDao.get(id);
    }

    @Override
    public List<Idcard> list(Map<String, Object> map) {
        return clusterIdcardDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterIdcardDao.count(map);
    }

    @Override
    public int save(Idcard idcard) {
        return idcardDao.save(idcard);
    }

    @Override
    public int update(Idcard idcard) {
        return idcardDao.update(idcard);
    }

    @Override
    public int remove(Long id) {
        return idcardDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return idcardDao.batchRemove(ids);
    }

}
