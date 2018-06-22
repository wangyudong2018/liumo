package com.yiyun.dao.cluster;


import com.yiyun.common.MyMapper;
import com.yiyun.domain.SysCommonDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统通用参数
 * @author xx
 * @email xxx@163.com
 * @date Fri Dec 15 12:47:48 CST 2017
 */
@Mapper
public interface ClusterSysCommonDao extends MyMapper<SysCommonDO> {

	SysCommonDO get(Long id);
	
	List<SysCommonDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	

}
