package com.yiyun.web.operation.service;

import com.yiyun.domain.PProduct;

import java.util.List;
import java.util.Map;

/**
 * @title 产品
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface ProductService {

    PProduct get(Long id);

    List<PProduct> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(PProduct pProduct);

    int update(PProduct pProduct);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
