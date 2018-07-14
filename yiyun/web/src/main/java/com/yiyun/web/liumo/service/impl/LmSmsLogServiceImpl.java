package com.yiyun.web.liumo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmSmsLogDao;
import com.yiyun.domain.LmSmsLog;
import com.yiyun.domain.LmUser;
import com.yiyun.web.liumo.service.LmSmsLogService;
import com.yiyun.web.liumo.util.SessionUtil;

@Service
public class LmSmsLogServiceImpl implements LmSmsLogService {

	@Autowired
	private LmSmsLogDao lmSmsLogDao;

	@Override
	public LmSmsLog get(Long id) {
		return lmSmsLogDao.get(id);
	}

	@Override
	public List<LmSmsLog> list(Map<String, Object> map) {
		return lmSmsLogDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmSmsLogDao.count(map);
	}

	@Override
	public int save(LmSmsLog lmSmsLog) {
		return lmSmsLogDao.save(lmSmsLog);
	}

	@Override
	public int save(String mobile, String type, String content) {
		LmUser lmUser = SessionUtil.getLmUser();

		LmSmsLog lmSmsLog = new LmSmsLog();
		lmSmsLog.setUserId(lmUser == null ? null : lmUser.getId());
		lmSmsLog.setMobile(mobile);
		lmSmsLog.setSmsType(type);
		lmSmsLog.setContent(content);
		lmSmsLog.setSendDate(new Date());
		lmSmsLog.setCreateTime(new Date());
		return lmSmsLogDao.save(lmSmsLog);
	}

	@Override
	public int edit(LmSmsLog lmSmsLog) {
		return lmSmsLogDao.edit(lmSmsLog);
	}

	@Override
	public int remove(Long id) {
		return lmSmsLogDao.remove(id);
	}

	@Override
	public int batchRemove(Long[] ids) {
		return lmSmsLogDao.batchRemove(ids);
	}

}
