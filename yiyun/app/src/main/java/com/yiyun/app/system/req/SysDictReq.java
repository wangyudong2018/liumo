package com.yiyun.app.system.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @title 字典数据请求信息
 * @description
 * @author Xingbz
 * @createDate 2017/12/18
 * @version 1.0
 */
@Data
public class SysDictReq {

    /** 版本号 */
    @ApiModelProperty(hidden=true)
    private String dictVersion;
    /** 是否强制获取 */
    @ApiModelProperty(hidden=true)
    private String force;

}

