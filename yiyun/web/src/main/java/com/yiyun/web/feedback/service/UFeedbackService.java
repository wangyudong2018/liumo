package com.yiyun.web.feedback.service;


import com.yiyun.domain.UFeedbackDO;

import java.util.List;
import java.util.Map;

/**
 * 用户反馈表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-12-13 16:02:47
 */
public interface UFeedbackService {
	
	UFeedbackDO get(Long id);
	
	List<UFeedbackDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UFeedbackDO feedback);
	
	int update(UFeedbackDO feedback);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
