package com.yiyun.web.query.service;

        import com.yiyun.domain.Idcard;

import java.util.List;
import java.util.Map;

/**
 * @title 会员身份证细腻
 * @author Xing
 * @date Fri Jun 08 16:25:09 CST 2018
 */
public interface IdcardService {
	
	Idcard get(Long id);
	
	List<Idcard> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(Idcard idcard);
	
	int update(Idcard idcard);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
