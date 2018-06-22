package com.yiyun.dao.master;

        import com.yiyun.domain.NewProduct;

import java.util.List;
import java.util.Map;
        import com.yiyun.common.MyMapper;

/**
 * @title 
 * @author Xing
 * @date Fri Jun 08 10:55:33 CST 2018
 */
public interface NewProductDao extends MyMapper<NewProduct>{
	int save(NewProduct newProduct);

	int update(NewProduct newProduct);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
