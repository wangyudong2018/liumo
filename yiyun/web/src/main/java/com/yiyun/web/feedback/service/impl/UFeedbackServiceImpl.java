package com.yiyun.web.feedback.service.impl;

import com.yiyun.dao.cluster.ClusterUFeedbackDao;
import com.yiyun.dao.master.UFeedbackDao;
import com.yiyun.domain.UFeedbackDO;
import com.yiyun.web.feedback.service.UFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class UFeedbackServiceImpl implements UFeedbackService {

	@Autowired
	private ClusterUFeedbackDao clusterUFeedbackDao;

	@Autowired
	private UFeedbackDao feedbackDao;

	@Override
	public UFeedbackDO get(Long id){
		return clusterUFeedbackDao.get(id);
	}
	
	@Override
	public List<UFeedbackDO> list(Map<String, Object> map){
		return clusterUFeedbackDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return clusterUFeedbackDao.count(map);
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
