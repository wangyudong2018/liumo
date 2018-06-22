package com.yiyun.dao.master;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.PProduct;

/**
 * @title 产品
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface PProductDao extends MyMapper<PProduct> {
    int save(PProduct pProduct);

    int update(PProduct pProduct);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
