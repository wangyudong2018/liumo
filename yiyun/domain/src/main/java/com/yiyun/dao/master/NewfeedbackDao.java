package com.yiyun.dao.master;

import com.yiyun.domain.Newfeedback;

import java.util.List;
import java.util.Map;

import com.yiyun.common.MyMapper;

/**
 * @title 用户反馈表
 * @author Xing
 * @date Fri Jun 08 13:13:45 CST 2018
 */
public interface NewfeedbackDao extends MyMapper<Newfeedback> {
    int save(Newfeedback newfeedback);

    int update(Newfeedback newfeedback);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
