package com.yiyun.web.order.service;

import com.yiyun.domain.OReserve;
import com.yiyun.resp.AddReserveResp;
import com.yiyun.resp.CheckSmsResp;

import java.util.List;
import java.util.Map;

/**
 * @title 预约
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface OReserveService {

    OReserve get(Long id);

    List<OReserve> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(OReserve oReserve);

    int update(OReserve oReserve);

    int remove(Long id);

    int batchRemove(Long[] ids);

    CheckSmsResp checkcode(String phone, String smsInputCode);

    AddReserveResp receive(Long userId);

    int cancel(Long id);

    void check(OReserve oReserve);

}
