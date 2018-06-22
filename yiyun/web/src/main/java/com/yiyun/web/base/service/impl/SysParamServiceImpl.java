package com.yiyun.web.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.yiyun.constants.RedisConstants;
import com.yiyun.dao.cluster.ClusterSysParamDao;
import com.yiyun.dao.master.SysParamDao;
import com.yiyun.domain.SysParam;
import com.yiyun.web.base.service.SysParamService;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@CacheConfig(cacheNames = RedisConstants.CACHE_NAME_COMMON)
public class SysParamServiceImpl implements SysParamService {
    @Autowired
    private SysParamDao sysParamDao;

    @Autowired
    private ClusterSysParamDao clusterSysParamDao;

    @Autowired
    private RedisTemplateDAO redisDao;

    @Override
    public SysParam get(Long id) {
        return clusterSysParamDao.get(id);
    }

    /**
     * 根据code获取系统参数
     *
     * @param code
     * @return
     * @description
     */
    @Override
    public SysParam getByCode(String code) {
        String cacheParamJson = redisDao.get(RedisConstants.CACHE_KEY_COMMON_PARAM + code);
        if (StringUtils.isNotBlank(cacheParamJson)) {
            return JSON.parseObject(cacheParamJson, SysParam.class);
        }

        SysParam queryParam = new SysParam();
        queryParam.setCode(code);
        SysParam sysParam = clusterSysParamDao.selectOne(queryParam);
        if (sysParam != null) {
            redisDao.put(RedisConstants.CACHE_KEY_COMMON_PARAM + sysParam.getCode(), JSON.toJSONString(sysParam), RedisConstants.getCacheOutTime(RedisConstants.OUT_TIME_ONE_MONTH));
            return sysParam;
        }
        return null;
    }

    @Override
    public List<SysParam> list(Map<String, Object> map) {
        return clusterSysParamDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterSysParamDao.count(map);
    }

    @Override
    public int save(SysParam param) {
        return sysParamDao.save(param);
    }

    @Override
    public int update(SysParam param) {
        int result = sysParamDao.update(param);
        if (result > 0) {
            redisDao.put(RedisConstants.CACHE_KEY_COMMON_PARAM + param.getCode(), JSON.toJSONString(param), RedisConstants.getCacheOutTime(RedisConstants.OUT_TIME_ONE_MONTH));
        }
        return result;
    }

    @Override
    public int remove(Long id) {
        SysParam oldParam = clusterSysParamDao.get(id);
        if (oldParam != null) {
            redisDao.delete(RedisConstants.CACHE_KEY_COMMON_PARAM + oldParam.getCode());
        }
        return sysParamDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        for (Long id : ids) {
            SysParam oldParam = clusterSysParamDao.get(id);
            if (oldParam != null) {
                redisDao.delete(RedisConstants.CACHE_KEY_COMMON_PARAM + oldParam.getCode());
            }
        }
        return sysParamDao.batchRemove(ids); //删除表中的批量记录
    }

    @Override
    public List<SysParam> listType() {
        return clusterSysParamDao.listType();
    }

    @Override
    public String getName(String type, String value) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", type);
        param.put("value", value);
        return clusterSysParamDao.list(param).get(0).getName();
    }


    /**
     * 根据类型查询所有的系统参数
     */
    @Override
    public List<SysParam> listByType(String type) {
        Map<String, Object> param = new HashMap<>(16);
        param.put("type", type);
        List<SysParam> result = clusterSysParamDao.list(param);
        if(result != null && !result.isEmpty()){
            result.forEach( sysParam ->
                    redisDao.put(RedisConstants.CACHE_KEY_COMMON_PARAM + sysParam.getCode(), JSON.toJSONString(param), RedisConstants.getCacheOutTime(RedisConstants.OUT_TIME_ONE_MONTH))
            );
        }
        return result;
    }

    /**
     * 根据code集合获取所有的参数 , 返回值为list
     * @param codes
     * @return
     */
    @Override
    public List<SysParam> listByCodes(String... codes) {
        List<SysParam> list = new ArrayList<>();
        Optional<String[]> optional = Optional.ofNullable(codes);
        optional.ifPresent(codeArr ->{
            for (String code : codeArr) {
                SysParam param = getByCode(code);
                if(param != null){
                    list.add(param);
                }
            }
        });
        return list;
    }

    /**
     * 根据code集合获取所有的参数 , 返回值为map , 其中参数code为key , 参数值为value
     * @param codes
     * @return
     */
    @Override
    public Map<String, String> mapByCodes(String... codes) {
        return list2map(listByCodes(codes));
    }
}
