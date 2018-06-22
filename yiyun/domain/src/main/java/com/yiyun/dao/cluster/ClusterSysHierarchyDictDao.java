package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.SysHierarchyDict;

import java.util.List;
import java.util.Map;

/**
 * @author Xingbz
 * @title 多级字典条目信息
 * @date Tue Jan 09 18:33:26 CST 2018
 */
public interface ClusterSysHierarchyDictDao extends MyMapper<SysHierarchyDict> {

    SysHierarchyDict get(Long id);

    List<SysHierarchyDict> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    List<SysHierarchyDict> getFilterType(String rootCode);
}
