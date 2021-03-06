package com.yiyun.dao.master;

import com.yiyun.domain.LmProduct;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 六漠产品介绍
 * @author WangYuDong
 * @date Thu Jul 05 22:19:09 CST 2018
 */
public interface LmProductDao extends MyMapper<LmProduct> {

	LmProduct get(Long id);

	List<LmProduct> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmProduct lmProduct);

	int edit(LmProduct lmProduct);

	int remove(Long id);

	int batchRemove(Long[] ids);

}
