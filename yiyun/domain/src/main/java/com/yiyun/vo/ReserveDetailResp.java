package com.yiyun.vo;

import lombok.Data;

@Data
public class ReserveDetailResp {
    private String productName;
    private Long productId;
    private Long orderId;
    private String phone;
    private String reserveTime;
    private String salesman;
    private Integer status;
    private String area;

}
