package com.yiyun.web.system.service;


import com.yiyun.domain.SysAdShareDO;

import java.util.List;
import java.util.Map;

/**
 * 广告分享表
 * 
 * @author YaN
 * @email 1992lcg@163.com
 * @date Tue Dec 19 13:50:41 CST 2017
 */
public interface SysAdShareService {
	
	SysAdShareDO get(Long id);
	
	List<SysAdShareDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SysAdShareDO sysAdShare);
	
	int update(SysAdShareDO sysAdShare);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
