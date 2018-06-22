package com.yiyun.web.operation.service.impl;

import com.yiyun.dao.cluster.ClusterPProductDao;
import com.yiyun.dao.master.PProductDao;
import com.yiyun.domain.PProduct;
import com.yiyun.web.operation.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PProductServiceImpl implements ProductService {
    @Autowired
    private PProductDao pProductDao;

    @Autowired
    private ClusterPProductDao clusterPProductDao;

    @Override
    public PProduct get(Long id) {
        return clusterPProductDao.get(id);
    }

    @Override
    public List<PProduct> list(Map<String, Object> map) {
        return clusterPProductDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterPProductDao.count(map);
    }

    @Override
    public int save(PProduct pProduct) {
        return pProductDao.save(pProduct);
    }

    @Override
    public int update(PProduct pProduct) {
        return pProductDao.update(pProduct);
    }

    @Override
    public int remove(Long id) {
        return pProductDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return pProductDao.batchRemove(ids);
    }

}
