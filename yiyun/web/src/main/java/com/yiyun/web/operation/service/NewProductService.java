package com.yiyun.web.operation.service;

import com.yiyun.domain.NewProduct;

import java.util.List;
import java.util.Map;

/**
 * @title
 * @author Xing
 * @date Fri Jun 08 10:55:33 CST 2018
 */
public interface NewProductService {

    NewProduct get(Long id);

    List<NewProduct> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(NewProduct newProduct);

    int update(NewProduct newProduct);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
