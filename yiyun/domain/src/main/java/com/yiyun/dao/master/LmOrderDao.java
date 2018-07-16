package com.yiyun.dao.master;

import com.yiyun.domain.LmOrder;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 六漠订单表
 * @author wangyudong
 * @date Mon Jul 16 23:06:06 CST 2018
 */
public interface LmOrderDao extends MyMapper<LmOrder> {

	LmOrder get(String id);

	List<LmOrder> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmOrder lmOrder);

	int edit(LmOrder lmOrder);

	int remove(String id);

	int batchRemove(String[] ids);

}
