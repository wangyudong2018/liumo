package com.yiyun.app.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yiyun.app.common.config.redis.RedisTemplateDAO;
import com.yiyun.app.system.resp.HelpDoc;
import com.yiyun.app.system.resp.SysHelpDocResp;
import com.yiyun.app.system.service.SysHelpDocService;
import com.yiyun.constants.RedisConstants;
import com.yiyun.dao.cluster.ClusterSysHelpDocDao;
import com.yiyun.domain.SysHelpDocDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SysHelpDocServiceImpl implements SysHelpDocService {

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
	public JSONArray getAllHelpDocByType(){
        String redisHelp =  redisTemplateDAO.get(RedisConstants.CACHE_KEY_APP_HELPS);
        if(StringUtils.isNotBlank(redisHelp)){//有缓存, 直接返回缓存数据
            return JSONObject.parseArray(redisHelp);
        }
		List<SysHelpDocDO> sysHelpDoc = clusterSysHelpDocDao.list(null);
        Map<String,String> tempMap1 = new HashMap<>();
        for(SysHelpDocDO sdo:sysHelpDoc){   //获取所有类别，类别名称，图标地址
            String description = sdo.getDescription();
            String type1 = sdo.getType();
            String icon = sdo.getIcon();
            tempMap1.put(type1,description);
            tempMap1.put(type1+"_icon",icon);
        }
		Map<String,List<HelpDoc>> tempMap2 = new LinkedHashMap<>();//临时map
		List<SysHelpDocResp> result = new ArrayList<>();
		for(SysHelpDocDO doc : sysHelpDoc){
			HelpDoc helpDoc = new HelpDoc();
			helpDoc.setQuestion(doc.getTitle());      //问题
			helpDoc.setAnswer(doc.getBody());         //回答
			String type2 = doc.getType();             //类别
			if(tempMap2.containsKey(type2)){
				tempMap2.get(type2).add(helpDoc);
			}else{
				List<HelpDoc> resps = new ArrayList<>();
				resps.add(helpDoc);
				tempMap2.put(type2, resps);
			}
		}

		tempMap2.forEach((key, value) -> {
			SysHelpDocResp sysHelpDocResp = new SysHelpDocResp();
			String description = tempMap1.get(key);
			String icon = tempMap1.get(key + "_icon");
			if (StringUtils.isBlank(icon)) {
				icon = "";
			}
			sysHelpDocResp.setHelpDocType(description);
			sysHelpDocResp.setIcon(icon);
			sysHelpDocResp.setContent(tempMap2.get(key));
			result.add(sysHelpDocResp);
		});

        String jsonResult = JSON.toJSONString(result);
        redisTemplateDAO.put(RedisConstants.CACHE_KEY_APP_HELPS,jsonResult);
        return JSONObject.parseArray(jsonResult);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return clusterSysHelpDocDao.count(map);
	}

	
}
