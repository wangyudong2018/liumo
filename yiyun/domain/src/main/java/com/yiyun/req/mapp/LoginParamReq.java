package com.yiyun.req.mapp;

import com.yiyun.req.mapp.base.BaseParamReq;
import lombok.Data;

/**
 * @title
 * @author Xingbz
 * @createDate 2018-6-10
 */
@Data
public class LoginParamReq extends BaseParamReq {
    /**  */
    private String mobile;

    private String sms;
}