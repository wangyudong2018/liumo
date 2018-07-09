package com.yiyun.web.liumo.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmFileDao;
import com.yiyun.domain.LmFile;
import com.yiyun.web.liumo.service.LmFileService;

@Service
public class LmFileServiceImpl implements LmFileService {

	@Autowired
	private LmFileDao lmFileDao;

	@Override
	public LmFile get(String id) {
		return lmFileDao.get(id);
	}

	@Override
	public List<LmFile> list(Map<String, Object> map) {
		return lmFileDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmFileDao.count(map);
	}

	@Override
	public int save(LmFile lmFile) {
		return lmFileDao.save(lmFile);
	}

	@Override
	public int edit(LmFile lmFile) {
		return lmFileDao.edit(lmFile);
	}

	@Override
	public int remove(String id) {
		return lmFileDao.remove(id);
	}

	@Override
	public int batchRemove(String[] ids) {
		return lmFileDao.batchRemove(ids);
	}

}
