package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.OOrder;

import java.util.List;
import java.util.Map;

/**
 * @title 订单
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface ClusterOOrderDao extends MyMapper<OOrder> {

    OOrder get(Long id);

    List<OOrder> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int countResp(Map<String, Object> map);

    List<OOrder> listResp(Map<String, Object> map);
}
