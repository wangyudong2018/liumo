package com.yiyun.dao.cluster;

import com.yiyun.domain.Idcard;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 会员身份证细腻
 * @author Xing
 * @date Fri Jun 08 16:25:09 CST 2018
 */
public interface ClusterIdcardDao extends MyMapper<Idcard>{
	Idcard get(Long id);

	List<Idcard> list(Map<String, Object> map);

	int count(Map<String, Object> map);
}
