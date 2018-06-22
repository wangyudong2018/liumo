package com.yiyun.dao.master;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.OOrder;

/**
 * @title 订单
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface OOrderDao extends MyMapper<OOrder> {
    int save(OOrder oOrder);

    int update(OOrder oOrder);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
