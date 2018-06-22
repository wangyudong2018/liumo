package com.yiyun.app.system.service.impl;

import com.yiyun.app.system.service.SysCommonService;
import com.yiyun.dao.cluster.ClusterSysCommonDao;
import com.yiyun.dao.master.SysCommonDao;
import com.yiyun.domain.SysCommonDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class SysCommonServiceImpl implements SysCommonService {

    @Autowired
    private SysCommonDao sysCommonDao;
    @Autowired
    private ClusterSysCommonDao clusterSysCommonDao;
	
	@Override
	public SysCommonDO get(Long id){
        return clusterSysCommonDao.get(id);
    }

    @Override
    public List<SysCommonDO> list(Map<String, Object> map){
		return clusterSysCommonDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return clusterSysCommonDao.count(map);
	}
	
	@Override
	public int save(SysCommonDO sysCommon){
		return sysCommonDao.save(sysCommon);
	}
	
	@Override
	public int update(SysCommonDO sysCommon){
		return sysCommonDao.update(sysCommon);
	}
	
	@Override
	public int remove(Long id){
		return sysCommonDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return sysCommonDao.batchRemove(ids);
	}
	
}
