package com.yiyun.web.base.service;

import com.yiyun.domain.LogDO;
import com.yiyun.domain.PageDO;
import com.yiyun.web.common.utils.Query;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
