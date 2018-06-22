package com.yiyun.web.wechat.service;

import com.yiyun.vo.ReserveResp;

import java.text.ParseException;
import java.util.List;

/**
 * @author Xingbz
 * @title 预约
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface AppReserveService {

    List<ReserveResp> listReserve(String phone) throws ParseException;

}
