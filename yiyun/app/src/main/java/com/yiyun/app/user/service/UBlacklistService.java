package com.yiyun.app.user.service;

        import com.yiyun.domain.UBlacklistDO;

import java.util.List;
import java.util.Map;

/**
 * @title 黑名单列表
 * @author liyinq
 * @date Tue Jan 09 14:13:53 CST 2018
 */
public interface UBlacklistService {
	
	UBlacklistDO get(Long id);

	UBlacklistDO getBlackInfoByUserId(Long userId);

	List<UBlacklistDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UBlacklistDO UBlacklistDO);
	
	int update(UBlacklistDO UBlacklistDO);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	boolean saveUpdate(UBlacklistDO UBlacklistDO);
}
