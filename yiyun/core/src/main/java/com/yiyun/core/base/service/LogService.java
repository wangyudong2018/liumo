package com.yiyun.core.base.service;

import com.yiyun.core.common.utils.Query;
import com.yiyun.domain.LogDO;
import com.yiyun.domain.PageDO;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
    PageDO<LogDO> queryList(Query query);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
