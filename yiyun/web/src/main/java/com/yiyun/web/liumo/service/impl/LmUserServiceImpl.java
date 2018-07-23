package com.yiyun.web.liumo.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yiyun.dao.master.LmUserDao;
import com.yiyun.dao.master.LmUserHisDao;
import com.yiyun.domain.LmUser;
import com.yiyun.domain.LmUserHis;
import com.yiyun.web.liumo.service.LmUserService;
import com.yiyun.web.liumo.util.UUIDGenerator;

@Service
public class LmUserServiceImpl implements LmUserService {

	@Autowired
	private LmUserDao lmUserDao;
	@Autowired
	private LmUserHisDao lmUserHisDao;

	@Override
	public LmUser get(String id) {
		return lmUserDao.get(id);
	}

	@Override
	public List<LmUser> list(Map<String, Object> map) {
		return lmUserDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return lmUserDao.count(map);
	}

	@Override
	public int save(LmUser lmUser) {

		int reuslt = lmUserDao.save(lmUser);
		insertHis(lmUserDao.get(lmUser.getId()), "00");

		return reuslt;
	}

	@Override
	public int edit(LmUser lmUser) {
		return lmUserDao.edit(lmUser);
	}

	@Override
	public int remove(String id) {
		return lmUserDao.remove(id);
	}

	@Override
	public int batchRemove(String[] ids) {
		return lmUserDao.batchRemove(ids);
	}

	@Override
	public int edit(LmUser lmUser, String type) {
		lmUser.setLastUpdateTime(new Date());

		// type（00注册01认证历史02手机号历史03设置密码）
		if (StringUtils.equals("01", type)) {
			lmUser.setOprUpdateTime(new Date());
			// 实名认证标志（1是0否）
			if (StringUtils.equals("1", lmUser.getCertSign())) {
				lmUser.setCertDate(new Date());
			}
		} else if (StringUtils.equals("02", type)) {
			lmUser.setUsrUpdateTime(new Date());
		} else if (StringUtils.equals("03", type)) {
			lmUser.setUsrUpdateTime(new Date());
		}

		int reuslt = lmUserDao.edit(lmUser);

		if (StringUtils.equals("00", type)) { // 注册存历史
			insertHis(lmUserDao.get(lmUser.getId()), type);
		} else if (StringUtils.equals("02", type)) { // 手机号变更需要存历史
			insertHis(lmUserDao.get(lmUser.getId()), type);
		}

		return reuslt;
	}

	private int insertHis(LmUser lmUser, String hisType) {
		LmUserHis lmUserHis = new LmUserHis();
		BeanUtils.copyProperties(lmUser, lmUserHis);
		lmUserHis.setId(UUIDGenerator.generate());
		lmUserHis.setUserId(lmUser.getId());
		lmUserHis.setHisType(hisType);
		return lmUserHisDao.insert(lmUserHis);
	}

}
