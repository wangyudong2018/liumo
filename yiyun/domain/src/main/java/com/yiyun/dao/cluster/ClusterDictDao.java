package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.DictDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 字典表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 15:45:42
 */
@Mapper
public interface ClusterDictDao extends MyMapper<DictDO> {

	DictDO get(Long id);

	List<DictDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	List<DictDO> listType();

    List<DictDO> listFlag();
}