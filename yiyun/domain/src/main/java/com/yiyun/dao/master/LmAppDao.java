package com.yiyun.dao.master;

import com.yiyun.domain.LmApp;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 六漠APP表
 * @author wangyudong
 * @date Fri Jul 20 21:51:20 CST 2018
 */
public interface LmAppDao extends MyMapper<LmApp> {

	LmApp get(String id);

	List<LmApp> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmApp lmApp);

	int edit(LmApp lmApp);

	int remove(String id);

	int batchRemove(String[] ids);

}
