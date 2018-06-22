package com.yiyun.req;

import lombok.Data;

/**
 * @title 短信参数接收类
 *
 */
@Data
public class MessageReq {

    //手机号
    private String phone;
    // 类型
    private String type;

}