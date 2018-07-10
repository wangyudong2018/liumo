package com.yiyun.web.liumo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmUserDao;
import com.yiyun.domain.LmUser;
import com.yiyun.web.liumo.service.LmUserService;

@Service
public class LmUserServiceImpl implements LmUserService {

	@Autowired
	private LmUserDao lmUserDao;

	@Override
	public LmUser get(String id) {
		return lmUserDao.get(id);
	}

	@Override
	public List<LmUser> list(Map<String, Object> map) {
		return lmUserDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmUserDao.count(map);
	}

	@Override
	public int save(LmUser lmUser) {
		return lmUserDao.save(lmUser);
	}

	@Override
	public int edit(LmUser lmUser) {
		return lmUserDao.edit(lmUser);
	}

	@Override
	public int remove(String id) {
		return lmUserDao.remove(id);
	}

	@Override
	public int batchRemove(String[] ids) {
		return lmUserDao.batchRemove(ids);
	}

}
