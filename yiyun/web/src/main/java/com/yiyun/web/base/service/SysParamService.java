package com.yiyun.web.base.service;

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

	List<SysParam> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SysParam param);
	
	int update(SysParam param);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	List<SysParam> listType();

	String getName(String type, String value);

    /**
     * 根据code查询参数
     */
    SysParam getByCode(String code);

    /**
     * 根据类型编码查询其下所有的参数
     * @param type
     * @return
     */
    List<SysParam> listByType(String type);

    List<SysParam> listByCodes(String... codes);

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
