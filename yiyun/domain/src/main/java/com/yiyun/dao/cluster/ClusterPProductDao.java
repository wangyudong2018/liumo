package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.PProduct;

import java.util.List;
import java.util.Map;

/**
 * @title 产品
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface ClusterPProductDao extends MyMapper<PProduct> {

    PProduct get(Long id);

    List<PProduct> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    List<PProduct> getProductList();
}
