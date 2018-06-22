package com.yiyun.dao.cluster;

import com.yiyun.domain.Newfeedback;

import java.util.List;
import java.util.Map;

import com.yiyun.common.MyMapper;

/**
 * @title 用户反馈表
 * @author Xing
 * @date Fri Jun 08 13:13:45 CST 2018
 */
public interface ClusterNewfeedbackDao extends MyMapper<Newfeedback> {

    Newfeedback get(Long id);

    List<Newfeedback> list(Map<String, Object> map);

    int count(Map<String, Object> map);
}
