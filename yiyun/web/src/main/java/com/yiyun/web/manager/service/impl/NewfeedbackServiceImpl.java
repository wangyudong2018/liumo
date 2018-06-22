package com.yiyun.web.manager.service.impl;

import com.yiyun.dao.cluster.ClusterNewfeedbackDao;
import com.yiyun.dao.master.NewfeedbackDao;
import com.yiyun.domain.Newfeedback;
import com.yiyun.web.manager.service.NewfeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NewfeedbackServiceImpl implements NewfeedbackService {
    @Autowired
    private NewfeedbackDao newfeedbackDao;
    @Autowired
    private ClusterNewfeedbackDao clusterNewfeedbackDao;

    @Override
    public Newfeedback get(Long id) {
        return clusterNewfeedbackDao.get(id);
    }

    @Override
    public List<Newfeedback> list(Map<String, Object> map) {
        return clusterNewfeedbackDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterNewfeedbackDao.count(map);
    }

    @Override
    public int save(Newfeedback newfeedback) {
        return newfeedbackDao.save(newfeedback);
    }

    @Override
    public int update(Newfeedback newfeedback) {
        return newfeedbackDao.update(newfeedback);
    }

    @Override
    public int remove(Long id) {
        return newfeedbackDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return newfeedbackDao.batchRemove(ids);
    }

}
