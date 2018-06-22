package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.SysHelpDocDO;

import java.util.List;
import java.util.Map;

public interface ClusterSysHelpDocDao extends MyMapper<SysHelpDocDO> {
    SysHelpDocDO get(Long id);

    List<SysHelpDocDO> list(Map<String, Object> map);

    List<SysHelpDocDO> listType();

    List<SysHelpDocDO> listByType(String helpType);

    int count(Map<String, Object> map);
}
