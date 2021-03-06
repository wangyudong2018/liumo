package com.yiyun.dao.cluster;


import com.yiyun.domain.SysAdShareDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 广告分享表
 *
 * @author YaN
 * @email 1992lcg@163.com
 * @date Tue Dec 19 13:50:41 CST 2017
 */
@Mapper
public interface ClusterSysAdShareDao {

    SysAdShareDO get(Long id);

    List<SysAdShareDO> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    List<SysAdShareDO> listAll(@Param(value = "category") Integer category, @Param(value = "sysType") Integer sysType);

}
