package com.yiyun.web.system.service.impl;

import com.yiyun.dao.cluster.ClusterSysAdShareDao;
import com.yiyun.dao.master.SysAdShareDao;
import com.yiyun.domain.SysAdShareDO;
import com.yiyun.web.system.service.SysAdShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SysAdShareServiceImpl implements SysAdShareService {
    @Autowired
    private SysAdShareDao sysAdShareDao;

    @Autowired
    private ClusterSysAdShareDao clusterSysAdShareDao;

    @Override
    public SysAdShareDO get(Long id) {
        return clusterSysAdShareDao.get(id);
    }

    @Override
    public List<SysAdShareDO> list(Map<String, Object> map) {
        return clusterSysAdShareDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterSysAdShareDao.count(map);
    }

    @Override
    public int save(SysAdShareDO sysAdShare) {
        return sysAdShareDao.save(sysAdShare);
    }

    @Override
    public int update(SysAdShareDO sysAdShare) {
        return sysAdShareDao.update(sysAdShare);
    }

    @Override
    public int remove(Long id) {
        return sysAdShareDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return sysAdShareDao.batchRemove(ids);
    }

}
