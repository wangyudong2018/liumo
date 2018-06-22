package com.yiyun.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReserveReq {
    @ApiModelProperty(hidden = true)
    private String reserveTime;//预约时间

    @ApiModelProperty(hidden = true)
    private String orderId;//订单ID

    @ApiModelProperty(hidden = true)
    private String productId;//产品ID

    @ApiModelProperty(hidden = true)
    private String userPhone;//手机号

    @ApiModelProperty(hidden = true)
    private String loginPhone;//登录手机号

    @ApiModelProperty(hidden = true)
    private String comment;//评价

    @ApiModelProperty(hidden = true)
    private String type;//类别 1 取消  2修改
}

