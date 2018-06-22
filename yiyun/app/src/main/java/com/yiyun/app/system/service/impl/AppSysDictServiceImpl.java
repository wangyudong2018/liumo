package com.yiyun.app.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.yiyun.app.common.config.redis.RedisTemplateDAO;
import com.yiyun.app.system.req.SysDictReq;
import com.yiyun.app.system.resp.DictVO;
import com.yiyun.app.system.resp.SysDictResp;
import com.yiyun.app.system.service.IAppSysDictService;
import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.RedisConstants;
import com.yiyun.constants.SystemConstants;
import com.yiyun.dao.cluster.ClusterDictDao;
import com.yiyun.domain.DictDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @title 字典数据业务层
 * @description 字典数据业务层
 * @author Xingbz
 * @createDate 2017/12/18
 * @version 1.0
 */
@Service
public class AppSysDictServiceImpl implements IAppSysDictService {

	@Autowired
	private ClusterDictDao clusterDictDao;

	@Autowired
    private RedisTemplateDAO redisDao;


    /**
     * @title 获取所有数据字典
     * @description
     * @param req
     * @return java.util.List<DictDO>
     */
    @Override
    public SysDictResp getAllDictsByVersion(SysDictReq req) {
        SysDictResp resp = new SysDictResp();

        String appVersion = req.getDictVersion();
        String currVersion = getCurrDictVersion();
        if(Objects.equals(appVersion , currVersion)){
            String isForce = req.getForce();
            if(!(CommonConstants.YES + "").equals(isForce)){//不要求强制更新
                resp.setIsUpdate(CommonConstants.NO + "");
                resp.setDictVersion(currVersion);
                return resp;
            }
        }

        /* 首先从缓存中尝试获取数据 , 若无则从数据库获取 */
        String cacheDictStr = redisDao.get(RedisConstants.CACHE_KEY_COMMON_DICT_ALL);
        List<DictVO> dicts;
        if(StringUtils.isNotBlank(cacheDictStr)){
            dicts = JSON.parseArray(cacheDictStr , DictVO.class);
        }else{
            dicts = new ArrayList<>();
            Map<String , Object> param = new HashMap<>();
            param.put("flags" , Arrays.asList(SystemConstants.DICT_FLAG_APP , SystemConstants.DICT_FLAG_COMMON));//仅查询app端和通用的数据字典
            List<DictDO> dictList = clusterDictDao.list(param);//查询所有的数据字典

            /*
             *  jdk8方式转化为前端要求格式 , 留以备注
             *  转换要求 : 一个集合(内含一个类别和该类别下的一组字符串)
             *  step1 . 根据type分成map , 其中type值为key , 所有type下的对象集合为value
             *  step2 . 将DictDO集合只提取value值转为List<String>
             */
            Map<String , List<DictDO>> doMap = dictList.stream().collect(Collectors.groupingBy(DictDO::getType));
            doMap.forEach((type , doList) -> dicts.add(new DictVO(type , doList.stream().map(DictDO::getValue).collect(Collectors.toList()))));

            redisDao.put(RedisConstants.CACHE_KEY_COMMON_DICT_ALL , JSON.toJSONString(dicts));//存入缓存
        }

        resp.setIsUpdate(CommonConstants.YES + "");
        resp.setDictVersion(currVersion);
        resp.setDicts(dicts);
        return resp;
    }

    @Override
    public List<DictDO> list(Map<String, Object> map) {
        return clusterDictDao.list(map);
    }

//    private static DictVO getVoByType(String type, List<DictVO> dicts) {
//        if(StringUtils.isBlank(type) || dicts == null || dicts.isEmpty()){
//            return null;
//        }
//
//
//
//        for (DictVO vo : dicts) {
//            if(type.equals(vo.getCode())){
//                return vo;
//            }
//        }
//        return null;
//    }

    /**
     * @title 获取当前的字典版本号
     * @description
     * @return
     */
    private String getCurrDictVersion() {
         String cacheDictVersion = redisDao.get(RedisConstants.CACHE_KEY_COMMON_DICT_VERSION);
         if(StringUtils.isNotBlank(cacheDictVersion)){
             return cacheDictVersion;
         }

         String newDictVersion =SystemConstants.DICT_DEFAULT_VERSION;
         DictDO versionDict = clusterDictDao.get(SystemConstants.DICT_VERSION_ID);
         if(versionDict != null && StringUtils.isNotBlank(versionDict.getValue())){
             newDictVersion = versionDict.getValue();
            redisDao.put(RedisConstants.CACHE_KEY_COMMON_DICT_VERSION, newDictVersion);
        }
         return newDictVersion;
    }

}
