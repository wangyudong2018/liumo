package com.yiyun.dao.master;


import com.yiyun.common.MyMapper;
import com.yiyun.domain.SysCommonDO;

/**
 * 系统通用参数
 * @author xx
 * @email xxx@163.com
 * @date Fri Dec 15 12:47:48 CST 2017
 */
public interface SysCommonDao extends MyMapper<SysCommonDO> {

	int save(SysCommonDO sysCommon);
	
	int update(SysCommonDO sysCommon);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
