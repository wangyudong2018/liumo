package com.yiyun.app.system.service;


import com.yiyun.domain.SysCommonDO;

import java.util.List;
import java.util.Map;

/**
 * 系统通用参数
 * 
 * @author xx
 * @email xxx@163.com
 * @date Fri Dec 15 12:47:48 CST 2017
 */

public interface SysCommonService {
	
	SysCommonDO get(Long id);
	
	List<SysCommonDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SysCommonDO sysCommon);
	
	int update(SysCommonDO sysCommon);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
