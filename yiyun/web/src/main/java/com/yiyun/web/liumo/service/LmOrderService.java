package com.yiyun.web.liumo.service;

import com.yiyun.domain.LmOrder;

import java.util.List;
import java.util.Map;

/**
 * @title 六漠订单表
 * @author wangyudong
 * @date Mon Jul 16 23:06:06 CST 2018
 */
public interface LmOrderService {

	LmOrder get(String id);

	List<LmOrder> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmOrder lmOrder);

	int edit(LmOrder lmOrder);

	int remove(String id);

	int batchRemove(String[] ids);

}
