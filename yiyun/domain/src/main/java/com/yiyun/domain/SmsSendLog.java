package com.yiyun.domain;

import com.yiyun.utils.DateUtil;
import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @title
 * @author Xingbz
 * @createDate 2018-6-11
 */
@Data
public class SmsSendLog implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private Long id;

    //订单ID
    private Long loanId;

    //目标手机号
    private String toPhone;

    //短信内容
    private String content;

    //发送状态 1成功 2失败 3发送中 4其他
    private Integer status;

    @Transient
    private String statusDesc;

    //发送人ID
    private Long senderId;

    //发送人名称
    private String senderName;

    //响应消息
    private String retMsg;

    //创建时间
    private Long createTime;

    @Transient
    private String createTimeStr;

    //是否展示 1 展示 0不展示
    private Integer isShow;

    public String getCreateTimeStr() {
        this.createTimeStr = DateUtil.long2String(this.createTime, DateUtil.YYYY_MM_DD_HH_MM_SS);
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}