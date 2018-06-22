package com.yiyun.web.oa.service;


import com.yiyun.domain.NotifyDO;
import com.yiyun.utils.PageUtil;

import java.util.List;
import java.util.Map;

/**
 * 通知通告
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-05 17:11:16
 */
public interface NotifyService {

	NotifyDO get(Long id);

	List<NotifyDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(NotifyDO notify);

	int update(NotifyDO notify);

	int remove(Long id);

	int batchRemove(Long[] ids);

	Map<String, Object> message(Long userId);

	PageUtil selfList(Map<String, Object> map);
}
