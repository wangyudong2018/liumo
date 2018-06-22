package com.yiyun.web.base.service;


import com.yiyun.domain.SysHelpDocDO;

import java.util.List;
import java.util.Map;

/**
 * 帮助中心
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date Sat Dec 16 09:53:49 CST 2017
 */
public interface SysHelpDocService {
	
	SysHelpDocDO get(Long id);
	
	List<SysHelpDocDO> list(Map<String, Object> map);

	List<SysHelpDocDO> listType();
	
	int count(Map<String, Object> map);
	
	int save(SysHelpDocDO sysHelpDoc);
	
	int update(SysHelpDocDO sysHelpDoc);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
