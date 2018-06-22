package com.yiyun.dao.master;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.SysHierarchyDict;

/**
 * @author Xingbz
 * @title 多级字典条目信息
 * @date Tue Jan 09 18:33:26 CST 2018
 */
public interface SysHierarchyDictDao extends MyMapper<SysHierarchyDict> {
    int save(SysHierarchyDict hierarchyDict);

    int update(SysHierarchyDict hierarchyDict);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
