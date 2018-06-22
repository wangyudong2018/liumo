package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.MMember;

import java.util.List;
import java.util.Map;

/**
 * @title 会员/用户
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface ClusterMMemberDao extends MyMapper<MMember> {

    MMember get(Long id);

    MMember getByPhone(String phone);

    List<MMember> getListByPhone(String phone);

    List<MMember> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    List<MMember> blackList(Map<String, Object> map);

    List<MMember> listClient(Map<String,Object> map);

    int countClient(Map<String, Object> map);
}
