package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.Adress;
import com.yiyun.domain.SmsSendLog;

import java.util.List;
import java.util.Map;

/**
 * @title
 * @author Xingbz
 * @createDate 2018-6-11
 */
public interface ClusterSmsSendLogDao extends MyMapper<SmsSendLog> {
    Adress get(Long id);

    List<SmsSendLog> list(Map<String, Object> map);

    int count(Map<String, Object> map);
}