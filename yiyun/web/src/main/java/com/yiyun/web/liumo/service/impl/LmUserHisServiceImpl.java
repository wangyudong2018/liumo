package com.yiyun.web.liumo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmUserHisDao;
import com.yiyun.domain.LmUserHis;
import com.yiyun.web.liumo.service.LmUserHisService;

@Service
public class LmUserHisServiceImpl implements LmUserHisService {

	@Autowired
	private LmUserHisDao lmUserHisDao;

	@Override
	public LmUserHis get(String id) {
		return lmUserHisDao.get(id);
	}

	@Override
	public List<LmUserHis> list(Map<String, Object> map) {
		return lmUserHisDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmUserHisDao.count(map);
	}

	@Override
	public int save(LmUserHis lmUserHis) {
		return lmUserHisDao.save(lmUserHis);
	}

	@Override
	public int edit(LmUserHis lmUserHis) {
		return lmUserHisDao.edit(lmUserHis);
	}

	@Override
	public int remove(String id) {
		return lmUserHisDao.remove(id);
	}

	@Override
	public int batchRemove(String[] ids) {
		return lmUserHisDao.batchRemove(ids);
	}

}
