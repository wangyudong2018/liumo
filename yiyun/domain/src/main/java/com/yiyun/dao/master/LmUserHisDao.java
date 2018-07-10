package com.yiyun.dao.master;

import com.yiyun.domain.LmUserHis;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 六漠用户信息历史表
 * @author WangYuDong
 * @date Tue Jul 10 20:45:56 CST 2018
 */
public interface LmUserHisDao extends MyMapper<LmUserHis> {

	LmUserHis get(String id);

	List<LmUserHis> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmUserHis lmUserHis);

	int edit(LmUserHis lmUserHis);

	int remove(String id);

	int batchRemove(String[] ids);

}
