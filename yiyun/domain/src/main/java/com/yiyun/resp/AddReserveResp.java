package com.yiyun.resp;

import lombok.Data;

/**
 * @title 预约订单响应
 * @description
 * @author Xingbz
 * @createDate 2018/3/21
 * @version 1.0
 */
@Data
public class AddReserveResp {

    private boolean flag;

    private String message;

    private Long reserveId;

    private String reserveTime;
}
