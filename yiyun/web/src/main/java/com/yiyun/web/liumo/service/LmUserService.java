package com.yiyun.web.liumo.service;

import com.yiyun.domain.LmUser;

import java.util.List;
import java.util.Map;

/**
 * @title 六漠用户信息表
 * @author WangYuDong
 * @date Tue Jul 10 20:45:56 CST 2018
 */
public interface LmUserService {

	LmUser get(String id);

	List<LmUser> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmUser lmUser);

	int edit(LmUser lmUser);

	int remove(String id);

	int batchRemove(String[] ids);

}
