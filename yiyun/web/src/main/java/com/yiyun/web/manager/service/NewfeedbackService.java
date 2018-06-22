package com.yiyun.web.manager.service;

import com.yiyun.domain.Newfeedback;

import java.util.List;
import java.util.Map;

/**
 * @title 用户反馈表
 * @author Xing
 * @date Fri Jun 08 13:13:45 CST 2018
 */
public interface NewfeedbackService {

    Newfeedback get(Long id);

    List<Newfeedback> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(Newfeedback newfeedback);

    int update(Newfeedback newfeedback);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
