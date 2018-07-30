package com.yiyun.web.liumo.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmFileDao;
import com.yiyun.domain.LmFile;
import com.yiyun.web.common.config.SystemConfig;
import com.yiyun.web.liumo.constants.LiumoConstants;
import com.yiyun.web.liumo.service.LmFileService;

@Service
public class LmFileServiceImpl implements LmFileService {

	@Autowired
	private LmFileDao lmFileDao;
	@Autowired
	private SystemConfig systemConfig;

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
		saveFile(lmFile);
		return lmFileDao.save(lmFile);
	}

	@Override
	public int edit(LmFile lmFile) {
		saveFile(lmFile);
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

	private void saveFile(LmFile lmFile) {
		try {
			FileUtils.writeByteArrayToFile(new File(systemConfig.getUploadPath() + LiumoConstants.FILE_DIRECTORY + lmFile.getId()), lmFile.getLmFile(), false);
			lmFile.setLmFile(null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
