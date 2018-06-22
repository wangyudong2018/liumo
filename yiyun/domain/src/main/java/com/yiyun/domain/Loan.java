package com.yiyun.domain;

import com.yiyun.utils.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @title 订单
 * @author Xing
 * @date Fri Jun 08 20:54:55 CST 2018
 */
@Data
public class Loan implements Serializable {
    private static final long serialVersionUID = 1L;


    //1 受理中
    public static final Integer LOAN_STATUS_SLZ = 1;
    //2 受理成功
    public static final Integer LOAN_STATUS_SLCG = 2;
    //3 待补充资料
    public static final Integer LOAN_STATUS_DBCZL = 3;
    //4 资料补充中(小程序端提交资料后更改状态)
    public static final Integer LOAN_STATUS_ZLBCZ = 4;
    //5 服务进行中
    public static final Integer LOAN_STATUS_FWJXZ = 5;
    //6 放款成功
    public static final Integer LOAN_STATUS_FKCG = 6;

    //11 受理失败
    public static final Integer LOAN_STATUS_SLSB = 11;
    //12 放款失败
    public static final Integer LOAN_STATUS_FKSB = 12;

    //1 新建 / 运营岗
    public static final Integer LOAN_STEP_YUNYING = 1;
    //2 商务
    public static final Integer LOAN_STEP_SHANGWU = 2;


    //主键
    private Long id;
    //订单编号
    private String orderSn;
    //产品ID
    private Long productId;
    //借款人id
    private Long loanMemberId;
    //合伙人联系电话(非借款人 , 而是登记该订单的合伙人)
    private String loanMemberPhone;
    //订单当前步骤 1运营 2商务
    private Integer orderStep;
    //订单状态
    private Integer orderStatus;

    //订单状态描述
    @Transient
    private String orderStatusDesc;

    //预估金额
    private BigDecimal expectAmount;
    //订单金额
    private BigDecimal loanAmount;
    //借款期限
    private Integer loanLimit;
    //借款起始时间
    private Long loanBeginTime;
    //借款到期时间
    private Long loanEndTime;

    //借款起始时间
    private String loanBeginTimeStr;
    //借款到期时间
    private String loanEndTimeStr;

    //产品类型
    private String productType;
    //城市
    private String city;
    //业务区域
    private String area;
    //订单来源
    private String source;
    //房本照片(多个用逗号隔开)
    private String photo;
    //征信报告
    private String report;
    //客户资料
    private String memberData;
    //银行流水
    private String bankWater;
    //其他补充材料
    private String otherData;
    //还款计划表
    private String loanPlan;
    //客户经理
    private String client;
    //运营受理人员
    private String operator;
    //商务受理人员
    private String customer;
    //创建时间
    private Long createTime;
    //受理时间
    private Long clientTime;
    //运营受理时间
    private Long operatorTime;
    //商务受理时间
    private Long customerTime;
    //放款时间
    private Long loanTime;
    //预计还款时间
    private Long payPlanTime;
    //还款时间
    private Long repayTime;
    //公众号注册手机号(即借款人的手机号)
    private String wechatPhone;
    //反馈消息(待补充资料描述)
    private String remark;
    //邀请码
    private String column1;
    //最近更新时间
    private String column2;
    //运营受理人员ID
    private String column3;
    //商务受理人员ID
    private String column4;
    //其他补充说明
    private String column5;
    //批复金额
    private BigDecimal realAmount;
    //借款期限单位
    private String limitUnit;

    //合伙人ID
    private Long partner;

    @Transient
    private String name;//借款人姓名

    @Transient
    private String idcard;//借款人身份证号


    public String getOrderStatusDesc() {
        this.orderStatusDesc = getDescByOrderSatatus(this.orderStatus);
        return orderStatusDesc;
    }

    public String getLoanBeginTimeStr() {
        if (StringUtils.isBlank(this.loanBeginTimeStr) && null != loanBeginTime) {
            this.loanBeginTimeStr = DateUtil.long2String(loanBeginTime, DateUtil.YYYY_MM_DD);
        }
        return loanBeginTimeStr;
    }

    public void setLoanBeginTimeStr(String loanBeginTimeStr) {
        this.loanBeginTimeStr = loanBeginTimeStr;
    }

    public String getLoanEndTimeStr() {
        if (StringUtils.isBlank(this.loanEndTimeStr) && null != loanEndTime) {
            this.loanEndTimeStr = DateUtil.long2String(loanEndTime, DateUtil.YYYY_MM_DD);
        }
        return loanEndTimeStr;
    }

    public void setLoanEndTimeStr(String loanEndTimeStr) {
        this.loanEndTimeStr = loanEndTimeStr;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public static String getDescByOrderSatatus(Integer orderStatus) {
        if (null == orderStatus) {
            return "";
        }
        switch (orderStatus) {
            case 1:
                return "受理中";
            case 2:
                return "受理成功";
            case 3:
                return "待补充资料";
            case 4:
                return "资料补充中";
            case 5:
                return "服务进行中";
            case 6:
                return "放款成功";

            case 11:
                return "受理失败";
            case 12:
                return "放款失败";
            default:
                return "";
        }
    }


    public static String getCityByPinyin(String cityPinyin) {
        if (StringUtils.isBlank(cityPinyin)) {
            return "";
        }
        cityPinyin = cityPinyin.toUpperCase();
        if ("SHANGHAI".equals(cityPinyin)) {
            return "上海";
        }
        if ("BEIJING".equals(cityPinyin)) {
            return "北京";
        }
        if ("HANGZHOU".equals(cityPinyin)) {
            return "杭州";
        }
        return "";
    }
}