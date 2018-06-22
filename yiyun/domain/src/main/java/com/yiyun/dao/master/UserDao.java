package com.yiyun.dao.master;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 09:45:11
 */
@Mapper
public interface UserDao extends MyMapper<UserDO> {
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Long userId);
	
	int batchRemove(Long[] userIds);
}
