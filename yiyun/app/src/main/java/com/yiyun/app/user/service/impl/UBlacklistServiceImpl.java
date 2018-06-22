package com.yiyun.app.user.service.impl;

import com.yiyun.app.user.service.UBlacklistService;
import com.yiyun.constants.CommonConstants;
import com.yiyun.dao.cluster.ClusterUBlacklistDao;
import com.yiyun.dao.master.UBlacklistDao;
import com.yiyun.domain.UBlacklistDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UBlacklistServiceImpl implements UBlacklistService {

    @Autowired
    private UBlacklistDao uBlacklistDao;

    @Autowired
    private ClusterUBlacklistDao clusterUBlacklistDao;


    @Override
    public UBlacklistDO get(Long id) {
        return clusterUBlacklistDao.getBlack(id);
    }

    @Override
    public UBlacklistDO getBlackInfoByUserId(Long userId) {
        return clusterUBlacklistDao.getBlack(userId);
    }

    @Override
    public List<UBlacklistDO> list(Map<String, Object> map) {
        return clusterUBlacklistDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterUBlacklistDao.count(map);
    }

    @Override
    public int save(UBlacklistDO uBlacklistDO) {
        uBlacklistDO.setAddTime(System.currentTimeMillis());
        uBlacklistDO.setCreateTime(System.currentTimeMillis());
        uBlacklistDO.setBlackState(Long.valueOf(CommonConstants.YES));
        return uBlacklistDao.save(uBlacklistDO);
    }

    @Override
    public int update(UBlacklistDO UBlacklistDO) {
        UBlacklistDO.setAddTime(System.currentTimeMillis());
        return uBlacklistDao.update(UBlacklistDO);
    }

    @Override
    public int remove(Long id) {
        return uBlacklistDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return uBlacklistDao.batchRemove(ids);
    }

    @Override
    public boolean saveUpdate(UBlacklistDO UBlacklistDO) {
        boolean boo = false;
        if (clusterUBlacklistDao.getBlack(UBlacklistDO.getUserId()) != null) {
            //更新
            boo = true;
            return boo;
        } else {
            //保存
            return boo;
        }
    }

}
