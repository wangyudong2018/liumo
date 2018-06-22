package com.yiyun.app.system.req;

import io.swagger.annotations.ApiModelProperty;
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
public class FeedbackReq {

    //用户id
    @ApiModelProperty(value = "用户memberId", required = false, hidden = true)
    private String memberId;
    //手机号码
    @ApiModelProperty(value = "用户手机号", required = false, hidden = true)
    private String phone;
    //姓名
    @ApiModelProperty(value = "用户姓名", required = false, hidden = true)
    private String name;
    //反馈类别
    @ApiModelProperty(value = "用户反馈类别", required = false, hidden = true)
    private String type;
    //内容
    @ApiModelProperty(value = "用户反馈信息", required = false, hidden = true)
    private String message;
    //手机型号(品牌+型号例:iPhone5s)
    @ApiModelProperty(value = "手机型号", required = false, hidden = true)
    private String phoneModel;
    //操作系统(操作系统+系统版本号 例:ios9.3.1)
    @ApiModelProperty(value = "操作系统", required = false, hidden = true)
    private String operatingSystem;
    //手机串码
    @ApiModelProperty(value = "手机串码", required = false, hidden = true)
    private String phoneImei;

    @NotBlank(message = "内容不能为空")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @NotBlank(message = "手机型号不能为空")
    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    @NotBlank(message = "操作系统不能为空")
    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getPhoneImei() {
        return phoneImei;
    }

    public void setPhoneImei(String phoneImei) {
        this.phoneImei = phoneImei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank(message = "反馈类别不能为空")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @NotBlank(message = "用户ID不能为空")
    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
