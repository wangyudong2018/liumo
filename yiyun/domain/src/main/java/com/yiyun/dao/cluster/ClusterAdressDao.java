package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.Adress;

import java.util.List;
import java.util.Map;

/**
 * @title 
 * @author Xing
 * @date Tue Jun 05 07:37:41 CST 2018
 */
public interface ClusterAdressDao extends MyMapper<Adress>{

	Adress get(Long id);

    List<Adress> list(Map<String, Object> map);

    int count(Map<String, Object> map);
}
