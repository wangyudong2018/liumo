package com.yiyun.app.system.resp;

import lombok.Data;

import java.util.List;

/**
 * @author YanXiaoGuang
 * @version 1.0
 * @title 广告分享返回信息
 * @description
 * @createDate 2017/12/20
 */
@Data
public class SysAdShareResp {

    private Integer category;

    private List<SysAdShareVO> content;
}

