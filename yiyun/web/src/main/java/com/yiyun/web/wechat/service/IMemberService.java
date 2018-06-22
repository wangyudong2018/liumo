package com.yiyun.web.wechat.service;

import com.yiyun.resp.CheckSmsResp;
import com.yiyun.resp.LoginResp;
import com.yiyun.resp.MemberResp;

public interface IMemberService {

    CheckSmsResp checkcode(String phone, String smsInputCode);

    LoginResp login(String phone, String type);

    boolean logout(String phone);

    MemberResp findMember(Long memberId, String phone);
}
