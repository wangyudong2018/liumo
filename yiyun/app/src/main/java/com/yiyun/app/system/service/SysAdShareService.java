package com.yiyun.app.system.service;


import com.yiyun.app.system.resp.SysAdShareResp;
import com.yiyun.domain.SysAdShareDO;

import java.lang.reflect.InvocationTargetException;
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

	List<SysAdShareResp> list(Integer category, Integer sysType) throws InvocationTargetException, IllegalAccessException;

    List<Map> getBannerList();
}
