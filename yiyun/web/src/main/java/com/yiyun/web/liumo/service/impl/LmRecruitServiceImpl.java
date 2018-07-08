package com.yiyun.web.liumo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmRecruitDao;
import com.yiyun.domain.LmRecruit;
import com.yiyun.web.liumo.service.LmRecruitService;

@Service
public class LmRecruitServiceImpl implements LmRecruitService {

	@Autowired
	private LmRecruitDao lmRecruitDao;

	@Override
	public LmRecruit get(Long id) {
		return lmRecruitDao.get(id);
	}

	@Override
	public List<LmRecruit> list(Map<String, Object> map) {
		return lmRecruitDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmRecruitDao.count(map);
	}

	@Override
	public int save(LmRecruit lmRecruit) {
		return lmRecruitDao.save(lmRecruit);
	}

	@Override
	public int edit(LmRecruit lmRecruit) {
		return lmRecruitDao.edit(lmRecruit);
	}

	@Override
	public int remove(Long id) {
		return lmRecruitDao.remove(id);
	}

	@Override
	public int batchRemove(Long[] ids) {
		return lmRecruitDao.batchRemove(ids);
	}

}
