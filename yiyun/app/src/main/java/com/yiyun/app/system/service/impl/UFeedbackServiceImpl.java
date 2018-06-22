package com.yiyun.app.system.service.impl;

import com.yiyun.app.system.service.UFeedbackService;
import com.yiyun.dao.master.UFeedbackDao;
import com.yiyun.domain.UFeedbackDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class UFeedbackServiceImpl implements UFeedbackService {
	@Autowired
	private UFeedbackDao feedbackDao;
	
	@Override
	public UFeedbackDO get(Long id){
		return feedbackDao.get(id);
	}
	
	@Override
	public List<UFeedbackDO> list(Map<String, Object> map){
		return feedbackDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return feedbackDao.count(map);
	}
	
	@Override
	public int save(UFeedbackDO feedback){
		return feedbackDao.save(feedback);
	}
	
	@Override
	public int update(UFeedbackDO feedback){
		return feedbackDao.update(feedback);
	}
	
	@Override
	public int remove(Long id){
		return feedbackDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return feedbackDao.batchRemove(ids);
	}
	
}
