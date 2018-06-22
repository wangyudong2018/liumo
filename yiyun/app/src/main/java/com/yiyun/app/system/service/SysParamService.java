package com.yiyun.app.system.service;

import com.yiyun.domain.SysParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * 
 * @author xbz
 * @email 1992lcg@163.com
 * @date Thu Dec 14 18:00:10 CST 2017
 */
public interface SysParamService {
	
	SysParam get(Long id);

	SysParam getByCode(String code);
	
	List<SysParam> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SysParam param);
	
	int update(SysParam param);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<SysParam> listType();

	String getName(String type, String value);

    /**
     * 根据类型编码查询该分类下所有的参数
     * @param type
     * @return
     */
    List<SysParam> listByType(String type);

    /**
     * 根据code集合查询多参数
     */
    List<SysParam> listByCodes(String... codes);

    /** 根据code集合查询多个参数 , 同时封装为map的形式 , 其中 , key为code,value为参数的value值 */
    Map<String , String> mapByCodes(String... codes);

    /**
     * 提供接口默认方法 , 可将参数集合转换为map的形式 , 其中 , 参数代码为key,参数值为value
     * @param list 需要转换的集合
     * @return
     */
    default Map<String , String> list2map(List<SysParam> list){
        Map<String, String> map = new HashMap<>();

        Optional<List<SysParam>> optional = Optional.ofNullable(list);
        // 如果不是null,调用Consumer
        optional.ifPresent(sysParams ->
            sysParams.forEach(sysParam -> map.put(sysParam.getCode() , sysParam.getValue()))
        );
        return map;
    }
}
