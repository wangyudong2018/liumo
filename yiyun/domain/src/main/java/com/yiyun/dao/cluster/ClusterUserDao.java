package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.UserDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Xingbz
 * 系统用户
 * @version 1.0
 * @title
 * @description
 * @createDate 2017/12/8
 */
@Mapper
public interface ClusterUserDao extends MyMapper<UserDO> {

    UserDO get(Long userId);

    List<UserDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    List<Long> listAllDept();

    /**
     * 根据角色code获取用户
     * @param
     * @return
     */
    List<UserDO> getUserByRoleCode(Map<String, Object> map);
}
