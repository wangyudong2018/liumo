package com.yiyun.web.liumo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmReleaseDao;
import com.yiyun.domain.LmRelease;
import com.yiyun.web.liumo.service.LmReleaseService;

@Service
public class LmReleaseServiceImpl implements LmReleaseService {

	@Autowired
	private LmReleaseDao lmReleaseDao;

	@Override
	public LmRelease get(Long id) {
		return lmReleaseDao.get(id);
	}

	@Override
	public List<LmRelease> list(Map<String, Object> map) {
		return lmReleaseDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmReleaseDao.count(map);
	}

	@Override
	public int save(LmRelease lmRelease) {
		return lmReleaseDao.save(lmRelease);
	}

	@Override
	public int edit(LmRelease lmRelease) {
		return lmReleaseDao.edit(lmRelease);
	}

	@Override
	public int remove(Long id) {
		return lmReleaseDao.remove(id);
	}

	@Override
	public int batchRemove(Long[] ids) {
		return lmReleaseDao.batchRemove(ids);
	}

}
