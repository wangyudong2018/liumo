package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.OReserve;
import com.yiyun.vo.ReserveDetailResp;
import com.yiyun.vo.ReserveResp;

import java.util.List;
import java.util.Map;

/**
 * @author Xingbz
 * @title 预约
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface ClusterOReserveDao extends MyMapper<OReserve> {

    OReserve get(Long id);

    ReserveDetailResp getById(Long id);

    List<ReserveResp> getByPhone(String phone);

    List<OReserve> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    Integer isExistCommitOrder(String loginPhone);

    List<ReserveResp> getByCommitPhone(String phone);
}
