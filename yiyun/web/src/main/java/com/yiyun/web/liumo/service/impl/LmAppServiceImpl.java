package com.yiyun.web.liumo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmAppDao;
import com.yiyun.domain.LmApp;
import com.yiyun.web.liumo.service.LmAppService;

@Service
public class LmAppServiceImpl implements LmAppService {

	@Autowired
	private LmAppDao lmAppDao;

	@Override
	public LmApp get(String id) {
		return lmAppDao.get(id);
	}

	@Override
	public List<LmApp> list(Map<String, Object> map) {
		return lmAppDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmAppDao.count(map);
	}

	@Override
	public int save(LmApp lmApp) {
		return lmAppDao.save(lmApp);
	}

	@Override
	public int edit(LmApp lmApp) {
		return lmAppDao.edit(lmApp);
	}

	@Override
	public int remove(String id) {
		return lmAppDao.remove(id);
	}

	@Override
	public int batchRemove(String[] ids) {
		return lmAppDao.batchRemove(ids);
	}

}
