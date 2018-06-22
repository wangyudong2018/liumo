package com.yiyun.app.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author XieLinGe
 * @version 1.0
 * @Title 意见反馈请求参数
 * @Description
 * @createDate 2017年7月28日
 * @modifier
 * @modifyDate
 */
@Data
@ApiModel(value="广告",description="广告对象")
public class SysAdShareReq {

    //类别
    @NotBlank(message = "类别不能为空")
    @ApiModelProperty(value = "类别;系统参数type:APP_AD", required = true, hidden = true)
    private String category;
    //系统类别
    @ApiModelProperty(value = "系统类别;系统参数type:SYSTEM_TYPE", hidden = true)
    private String sysType;

}
