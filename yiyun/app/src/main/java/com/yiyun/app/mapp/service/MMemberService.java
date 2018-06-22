package com.yiyun.app.mapp.service;

import com.yiyun.resp.CheckSmsResp;
import com.yiyun.resp.LoginResp;

import javax.servlet.http.HttpSession;

/**
 * @title
 * @author Xingbz
 * @createDate 2018-6-10
 */
public interface MMemberService {

    CheckSmsResp checkcode(String phone, String smsInputCode , HttpSession session);

    LoginResp login(String mobile, String investCode, HttpSession session);
}