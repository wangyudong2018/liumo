package com.yiyun.web.base.service;


import com.yiyun.domain.DictDO;
import com.yiyun.domain.SysHierarchyDict;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-29 18:28:07
 */
public interface DictService {

    DictDO get(Long id);

    List<DictDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(DictDO sysDict);

    int update(DictDO sysDict);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<DictDO> listType();

    String getName(String type, String value);

    String updateDictVersion(int result);

    List<DictDO> listFlag();

    List<SysHierarchyDict> listHierarchyDict(Map<String, Object> map);
}
