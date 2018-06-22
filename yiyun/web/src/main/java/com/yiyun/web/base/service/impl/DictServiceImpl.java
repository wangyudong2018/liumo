package com.yiyun.web.base.service.impl;

import com.yiyun.constants.RedisConstants;
import com.yiyun.constants.SystemConstants;
import com.yiyun.dao.cluster.ClusterDictDao;
import com.yiyun.dao.cluster.ClusterSysHierarchyDictDao;
import com.yiyun.dao.master.DictDao;
import com.yiyun.domain.DictDO;
import com.yiyun.domain.SysHierarchyDict;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = RedisConstants.CACHE_NAME_COMMON)
public class DictServiceImpl implements DictService {
	@Autowired
	private DictDao dictDao;

    @Autowired
    private ClusterDictDao clusterDictDao;

    @Autowired
    private RedisTemplateDAO redisDao;

    @Autowired
    private ClusterSysHierarchyDictDao clusterSysHierarchyDictDao;

	@Override
	public DictDO get(Long id){
		return clusterDictDao.get(id);
	}
	
	@Override
	public List<DictDO> list(Map<String, Object> map){
		return clusterDictDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return clusterDictDao.count(map);
	}
	
	@Override
    @CacheEvict(key="'" + RedisConstants.CACHE_KEY_COMMON_DICT_ALL + "'")
    public int save(DictDO sysDict){
        int result = dictDao.save(sysDict);
	    //新增字典 , 版本号增加
        updateDictVersion(result);
		return result;
	}
	
	@Override
    @CacheEvict(key="'" + RedisConstants.CACHE_KEY_COMMON_DICT_ALL + "'")
	public int update(DictDO sysDict){
        int result = dictDao.update(sysDict);
        //修改字典 , 版本号增加
        updateDictVersion(result);
        return result;
	}
	
	@Override
    @CacheEvict(key="'" + RedisConstants.CACHE_KEY_COMMON_DICT_ALL + "'")
	public int remove(Long id){
        int result = dictDao.remove(id);
        //删除字典 , 版本号增加
        updateDictVersion(result);
        return result;
	}

    /**
     * @title 更新版本号
     * @description
     * @param result
     * @return void
     */
//    @CachePut(key = "'" + RedisConstants.CACHE_KEY_COMMON_DICT_VERSION + "'" , condition = "#p0 > 0")
    public String updateDictVersion(int result) {
        String dictVersion = SystemConstants.DICT_DEFAULT_VERSION;
        if(result <= 0){
            return dictVersion;
        }

        DictDO versionDict = clusterDictDao.get(SystemConstants.DICT_VERSION_ID);
        if (versionDict == null) {//如果第一次使用 , 则增加
            versionDict = new DictDO();
            versionDict.setId(SystemConstants.DICT_VERSION_ID);
            versionDict.setName("version");
            versionDict.setValue(dictVersion);
            versionDict.setType("VERSION");
            versionDict.setDescription("版本控制(不可人为改动)");
            versionDict.setSort(new BigDecimal("0"));
            versionDict.setRemarks("该信息为全部字典的版本控制信息,每次增加,修改或删除时,均需要将该交记录的值+1");
            versionDict.setDelFlag("0");
            dictDao.insertSelective(versionDict);
        }else if(StringUtils.isNotBlank(versionDict.getValue())){
            Integer version = Integer.valueOf(versionDict.getValue());
            dictVersion = String.valueOf(version + 1);
            versionDict.setValue(dictVersion);
            dictDao.update(versionDict);
        }
        redisDao.put(RedisConstants.CACHE_KEY_COMMON_DICT_VERSION , dictVersion);
        return dictVersion;
    }

    @Override
    public List<DictDO> listFlag() {
        return clusterDictDao.listFlag();
    }

    @Override
//    @Cacheable(key="'" + RedisConstants.CACHE_KEY_COMMON_DICT_ALL + "'")
    public List<SysHierarchyDict> listHierarchyDict(Map<String, Object> map) {
        if (map != null) {
            map.put("sortBy", "sort");
            map.put("order", "asc");
        }
        return clusterSysHierarchyDictDao.list(map);
    }

    @Override
    @CacheEvict(key="'" + RedisConstants.CACHE_KEY_COMMON_DICT_ALL + "'")
	public int batchRemove(Long[] ids){
        int result = dictDao.batchRemove(ids);
        //删除字典 , 版本号增加
        updateDictVersion(result);
        return result;
    }
	
	@Override
	public List<DictDO> listType(){
		return clusterDictDao.listType();
	}

	@Override
    @Cacheable
	public String getName(String type, String value) {
		Map<String, Object> param = new HashMap<String, Object>(16);
		param.put("type", type);
		param.put("value", value);
		return clusterDictDao.list(param).get(0).getName();
	}

}
