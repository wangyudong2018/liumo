package com.yiyun.vo;

import lombok.Data;

@Data
public class ReserveResp {
    private String productName;
    private Long productId;
    private Long orderId;
    private String phone;
    private Integer status;
    private String statusRemark;
    private String reserveTime;

    private Integer isCancel;//是否可以取消  1 是 0否
}
