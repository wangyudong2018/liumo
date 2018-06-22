package com.yiyun.web.order.service;

import com.yiyun.domain.OOrder;

import java.util.List;
import java.util.Map;

/**
 * @title 订单
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface OOrderService {

    OOrder get(Long id);

    List<OOrder> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OOrder oOrder);

    int update(OOrder oOrder);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
