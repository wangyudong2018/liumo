package com.yiyun.app.order.service;

import com.yiyun.domain.OReserve;
import com.yiyun.req.ReserveReq;
import com.yiyun.vo.ReserveDetailResp;
import com.yiyun.vo.ReserveResp;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @author Xingbz
 * @title 预约
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface OReserveService {

    OReserve get(Long id);

    ReserveDetailResp getById(Long id);

    List<OReserve> list(Map<String, Object> map);

    void commitReserve(ReserveReq req) throws ParseException;

    List<ReserveResp> listReserve(String phone) throws ParseException;

    void cancelOrUpdateById(ReserveReq req) throws ParseException;

    Boolean isExistCommitOrder(String loginPhone);

    void commentOrder(ReserveReq req);
}
