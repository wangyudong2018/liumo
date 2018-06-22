package com.yiyun.dao.master;

import com.yiyun.domain.UBlacklistDO;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 黑名单列表
 * @author liyinq
 * @date Tue Jan 09 14:13:53 CST 2018
 */
public interface UBlacklistDao extends MyMapper<UBlacklistDO>{

	int save(UBlacklistDO UBlacklistDO);
	
	int update(UBlacklistDO UBlacklistDO);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

}
