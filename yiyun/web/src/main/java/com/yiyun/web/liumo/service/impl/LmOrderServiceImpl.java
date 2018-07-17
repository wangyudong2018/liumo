package com.yiyun.web.liumo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmOrderDao;
import com.yiyun.domain.LmOrder;
import com.yiyun.web.liumo.service.LmOrderService;

@Service
public class LmOrderServiceImpl implements LmOrderService {

	@Autowired
	private LmOrderDao lmOrderDao;

	@Override
	public LmOrder get(String id) {
		return lmOrderDao.get(id);
	}

	@Override
	public List<LmOrder> list(Map<String, Object> map) {
		return lmOrderDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmOrderDao.count(map);
	}

	@Override
	public int save(LmOrder lmOrder) {
		return lmOrderDao.save(lmOrder);
	}

	@Override
	public int edit(LmOrder lmOrder) {
		return lmOrderDao.edit(lmOrder);
	}

	@Override
	public int remove(String id) {
		return lmOrderDao.remove(id);
	}

	@Override
	public int batchRemove(String[] ids) {
		return lmOrderDao.batchRemove(ids);
	}

}
