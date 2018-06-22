package com.yiyun.resp;

import lombok.Data;

/**
 * @title
 * @description
 * @author Xingbz
 * @createDate 2018/3/21
 * @version 1.0
 */
@Data
public class MemberResp {
    private Long memberId;

    private String nickName;

    private String phone;

    private String headImg;

}
