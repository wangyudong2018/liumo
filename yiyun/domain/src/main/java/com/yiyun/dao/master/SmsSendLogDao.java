package com.yiyun.dao.master;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.SmsSendLog;

/**
 * @title
 * @author Xingbz
 * @createDate 2018-6-11
 */
public interface SmsSendLogDao extends MyMapper<SmsSendLog> {
    int save(SmsSendLog smsSendLog);

    int update(SmsSendLog smsSendLog);

    int remove(Long id);

    int batchRemove(Long[] ids);
}