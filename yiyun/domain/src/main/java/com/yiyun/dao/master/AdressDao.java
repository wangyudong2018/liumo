package com.yiyun.dao.master;

        import com.yiyun.common.MyMapper;
import com.yiyun.domain.Adress;

/**
 * @title 
 * @author Xing
 * @date Tue Jun 05 07:37:41 CST 2018
 */
public interface AdressDao extends MyMapper<Adress>{
	int save(Adress adress);
	
	int update(Adress adress);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
