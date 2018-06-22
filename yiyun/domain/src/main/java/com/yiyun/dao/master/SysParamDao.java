package com.yiyun.dao.master;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.SysParam;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author xbz
 * @email 1992lcg@163.com
 * @date Thu Dec 14 18:00:10 CST 2017
 */
@Mapper
public interface SysParamDao extends MyMapper<SysParam> {
	int save(SysParam param);
	
	int update(SysParam param);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
