package com.yiyun.web.base.service.impl;

import com.yiyun.constants.RedisConstants;
import com.yiyun.dao.cluster.ClusterSysHelpDocDao;
import com.yiyun.dao.master.SysHelpDocDao;
import com.yiyun.domain.SysHelpDocDO;
import com.yiyun.web.base.service.SysHelpDocService;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class SysHelpDocServiceImpl implements SysHelpDocService {
	@Autowired
	private SysHelpDocDao sysHelpDocDao;
	@Autowired
	private ClusterSysHelpDocDao clusterSysHelpDocDao;
	@Autowired
	private RedisTemplateDAO redisTemplateDAO;
	@Override
	public SysHelpDocDO get(Long id){
		return clusterSysHelpDocDao.get(id);
	}
	
	@Override
	public List<SysHelpDocDO> list(Map<String, Object> map){
		return clusterSysHelpDocDao.list(map);
	}
	@Override
	public List<SysHelpDocDO> listType(){
		return clusterSysHelpDocDao.listType();
	}
	
	@Override
	public int count(Map<String, Object> map){
		return clusterSysHelpDocDao.count(map);
	}
	
	@Override
	public int save(SysHelpDocDO sysHelpDoc){
        updateIcon(sysHelpDoc);//同类别保持图标一致
        int result = sysHelpDocDao.save(sysHelpDoc);
		redisTemplateDAO.delete(RedisConstants.CACHE_KEY_APP_HELPS);
		return result;
	}
	
	@Override
	public int update(SysHelpDocDO sysHelpDoc){
		updateIcon(sysHelpDoc);//同类别保持图标一致
		int result = sysHelpDocDao.update(sysHelpDoc);
		redisTemplateDAO.delete(RedisConstants.CACHE_KEY_APP_HELPS);
		return result;
	}
	
	@Override
	public int remove(Long id){
		int result = sysHelpDocDao.remove(id);
		redisTemplateDAO.delete(RedisConstants.CACHE_KEY_APP_HELPS);
		return result;
	}
	
	@Override
	public int batchRemove(Long[] ids){
		int result = sysHelpDocDao.batchRemove(ids);
		redisTemplateDAO.delete(RedisConstants.CACHE_KEY_APP_HELPS);
		return result;
	}

	//批量修改同类型图标
	private void updateIcon(SysHelpDocDO sysHelpDoc){
        String helpType = sysHelpDoc.getType();
        List<SysHelpDocDO>  helpList = clusterSysHelpDocDao.listByType(helpType);
        if(helpList.size()>0){
            String icon = sysHelpDoc.getIcon();
            if(icon==null){
                icon="";
            }
            if(!icon.equals(helpList.get(0).getIcon())){//判断同类别图标是否修改
                for(SysHelpDocDO shd: helpList){
                    shd.setIcon(icon);
                }
                sysHelpDocDao.updateIconBatch(helpList);//更新同类别的图标值
            }
        }
    }
	
}
