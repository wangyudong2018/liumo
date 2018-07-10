package com.yiyun.web.liumo.service;

import com.yiyun.domain.LmUserHis;

import java.util.List;
import java.util.Map;

/**
 * @title 六漠用户信息历史表
 * @author WangYuDong
 * @date Tue Jul 10 20:45:56 CST 2018
 */
public interface LmUserHisService {

	LmUserHis get(String id);

	List<LmUserHis> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmUserHis lmUserHis);

	int edit(LmUserHis lmUserHis);

	int remove(String id);

	int batchRemove(String[] ids);
	
}
