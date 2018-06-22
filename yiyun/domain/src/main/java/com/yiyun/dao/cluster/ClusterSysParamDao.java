package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.SysParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author xbz
 * @email 1992lcg@163.com
 * @date Thu Dec 14 18:00:10 CST 2017
 */
@Mapper
public interface ClusterSysParamDao extends MyMapper<SysParam> {

	SysParam get(Long id);
	
	List<SysParam> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);

    List<SysParam> listType();
}
