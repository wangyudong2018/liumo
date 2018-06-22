package com.yiyun.dao.master;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.DictDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 15:45:42
 */
@Mapper
public interface DictDao extends MyMapper<DictDO> {

	int save(DictDO dict);

	int update(DictDO dict);

	int remove(Long id);

	int batchRemove(Long[] ids);

}
