package com.yiyun.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @title 会员/用户
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
@Data
@NoArgsConstructor
public class MMember implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long id;
    //用户类型 1:合伙人;2:客户
    private Integer type;
    //用户状态 1:正常2:黑名单
    private Integer status;
    //昵称
    private String nickName;
    //手机号
    private String phone;
    //密码
    private String password;
    //头像
    private String headImg;
    //微信open_id
    private String openId;
    //来源
    private String source;
    //创建时间
    private Long createTime;
    //该会员邀请码
    private String memberInvestCode;
    //该会员邀请成功次数
    private Integer investCount;
    //该会员成功邀请下单次数
    private Integer investLoanCount;

    //邀请该用户的用户ID
    private Long investerMemberId;

    //邀请该会员的邀请码
    private String investerCode;

    @Transient
    private String name;

    @Transient
    private String idcard;

    @Transient
    private Integer gender;

    @Transient
    private Integer age;

    @Transient
    private String adress;

    public MMember(String phone) {
        this.phone = phone;
    }
}
