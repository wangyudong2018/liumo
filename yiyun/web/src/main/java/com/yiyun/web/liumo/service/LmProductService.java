package com.yiyun.web.liumo.service;

import com.yiyun.domain.LmProduct;

import java.util.List;
import java.util.Map;

/**
 * @title 六漠产品介绍
 * @author wangyudong
 * @date Fri Jun 22 23:28:52 CST 2018
 */
public interface LmProductService {

	LmProduct get(Long id);

	List<LmProduct> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmProduct lmProduct);

	int edit(LmProduct lmProduct);

	int remove(Long id);

	int batchRemove(Long[] ids);
}
