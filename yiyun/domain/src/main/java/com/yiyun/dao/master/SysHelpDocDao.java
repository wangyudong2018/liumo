package com.yiyun.dao.master;


import com.yiyun.common.MyMapper;
import com.yiyun.domain.SysHelpDocDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 帮助中心
 * @author chglee
 * @email 1992lcg@163.com
 * @date Sat Dec 16 09:53:49 CST 2017
 */
@Mapper
public interface SysHelpDocDao extends MyMapper<SysHelpDocDO> {

	int save(SysHelpDocDO sysHelpDoc);

	int update(SysHelpDocDO sysHelpDoc);

	int remove(Long id);

	int batchRemove(Long[] ids);

	void updateIconBatch(List<SysHelpDocDO> list);
}
