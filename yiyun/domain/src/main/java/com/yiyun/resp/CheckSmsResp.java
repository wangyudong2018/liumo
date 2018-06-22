package com.yiyun.resp;

import lombok.Data;

/**
 * @title 校验短信验证码响应结果
 * @description
 * @author Xingbz
 * @createDate 2018/3/12
 * @version 1.0
 */
@Data
public class CheckSmsResp {

    private boolean flag;

    private String message;
}
