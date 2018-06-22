package com.yiyun.web.operation.service.impl;

import com.yiyun.dao.cluster.ClusterNewProductDao;
import com.yiyun.dao.master.NewProductDao;
import com.yiyun.domain.NewProduct;
import com.yiyun.web.operation.service.NewProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NewProductServiceImpl implements NewProductService {
    @Autowired
    private NewProductDao newProductDao;
    @Autowired
    private ClusterNewProductDao clusterNewProductDao;

    @Override
    public NewProduct get(Long id) {
        return clusterNewProductDao.get(id);
    }

    @Override
    public List<NewProduct> list(Map<String, Object> map) {
        return clusterNewProductDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterNewProductDao.count(map);
    }

    @Override
    public int save(NewProduct newProduct) {
        return newProductDao.save(newProduct);
    }

    @Override
    public int update(NewProduct newProduct) {
        return newProductDao.update(newProduct);
    }

    @Override
    public int remove(Long id) {
        return newProductDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return newProductDao.batchRemove(ids);
    }

}
