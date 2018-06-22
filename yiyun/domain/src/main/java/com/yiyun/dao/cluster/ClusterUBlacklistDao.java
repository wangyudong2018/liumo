package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.UBlacklistDO;

import java.util.List;
import java.util.Map;

/**
 * @title 黑名单列表
 * @author liyinq
 * @date Tue Jan 09 14:13:53 CST 2018
 */
public interface ClusterUBlacklistDao extends MyMapper<UBlacklistDO>{

	UBlacklistDO getBlack(Long userId);

    List<UBlacklistDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

}
