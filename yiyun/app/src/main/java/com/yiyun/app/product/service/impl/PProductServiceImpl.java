package com.yiyun.app.product.service.impl;

import com.yiyun.app.product.service.ProductService;
import com.yiyun.dao.cluster.ClusterPProductDao;
import com.yiyun.dao.master.PProductDao;
import com.yiyun.domain.PProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PProductServiceImpl implements ProductService {
    @Autowired
    private PProductDao pProductDao;

    @Autowired
    private ClusterPProductDao clusterPProductDao;


    @Override
    public List<PProduct> getProductList() {
        return clusterPProductDao.getProductList();
    }
}
