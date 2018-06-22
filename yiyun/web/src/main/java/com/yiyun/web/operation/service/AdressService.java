package com.yiyun.web.operation.service;

        import com.yiyun.domain.Adress;

import java.util.List;
import java.util.Map;

/**
 * @title 
 * @author Xing
 * @date Tue Jun 05 07:37:41 CST 2018
 */
public interface AdressService {
	
	Adress get(Long id);
	
	List<Adress> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Adress adress);
	
	int update(Adress adress);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
