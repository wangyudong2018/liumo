package com.yiyun.dao.cluster;

        import com.yiyun.domain.NewProduct;

import java.util.List;
import java.util.Map;
        import com.yiyun.common.MyMapper;

/**
 * @title 
 * @author Xing
 * @date Fri Jun 08 10:55:33 CST 2018
 */
public interface ClusterNewProductDao extends MyMapper<NewProduct>{

	NewProduct get(Long id);

    List<NewProduct> list(Map<String, Object> map);

    int count(Map<String, Object> map);
}
