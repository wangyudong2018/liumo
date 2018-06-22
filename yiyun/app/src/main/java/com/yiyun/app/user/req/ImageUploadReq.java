package com.yiyun.app.user.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.beans.Transient;

/**
 * @author XieLinGe
 * @version 1.0
 * @Title 上传身份信息图片
 * @Description
 * @createDate 2017年7月28日
 * @modifier
 * @modifyDate
 */
@Data
@ToString
public class ImageUploadReq {

    @ApiModelProperty(hidden = true)
    @NotBlank(message = "图片流不能为空")
    private String image;

    @ApiModelProperty(hidden = true)
    @NotBlank(message = "类别不能为空")
    private String type;// 类型 1 其他 2身份证


}
