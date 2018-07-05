package com.yiyun.web.liumo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmBannerDao;
import com.yiyun.domain.LmBanner;
import com.yiyun.web.liumo.service.LmBannerService;

@Service
public class LmBannerServiceImpl implements LmBannerService {

	@Autowired
	private LmBannerDao lmBannerDao;

	@Override
	public LmBanner get(Long id) {
		return lmBannerDao.get(id);
	}

	@Override
	public List<LmBanner> list(Map<String, Object> map) {
		return lmBannerDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmBannerDao.count(map);
	}

	@Override
	public int save(LmBanner lmBanner) {
		return lmBannerDao.save(lmBanner);
	}

	@Override
	public int edit(LmBanner lmBanner) {
		return lmBannerDao.edit(lmBanner);
	}

	@Override
	public int remove(Long id) {
		return lmBannerDao.remove(id);
	}

	@Override
	public int batchRemove(Long[] ids) {
		return lmBannerDao.batchRemove(ids);
	}

}
