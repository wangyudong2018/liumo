package com.yiyun.web.liumo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yiyun.dao.master.LmProductDao;
import com.yiyun.domain.LmProduct;
import com.yiyun.web.liumo.service.LmProductService;

@Service
public class LmProductServiceImpl implements LmProductService {
	@Autowired
	private LmProductDao lmProductDao;

	@Override
	public LmProduct get(Long id) {
		return lmProductDao.get(id);
	}

	@Override
	public List<LmProduct> list(Map<String, Object> map) {
		return lmProductDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmProductDao.count(map);
	}

	@Override
	public int save(LmProduct lmProduct) {
		return lmProductDao.save(lmProduct);
	}

	@Override
	public int edit(LmProduct lmProduct) {
		return lmProductDao.edit(lmProduct);
	}

	@Override
	public int remove(Long id) {
		return lmProductDao.remove(id);
	}

	@Override
	public int batchRemove(Long[] ids) {
		return lmProductDao.batchRemove(ids);
	}

}
