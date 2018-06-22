package com.yiyun.domain;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @title 订单
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
@Data
public class OOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long id;
    //订单号
    private String orderSn;
    //产品ID
    private Long productId;
    //客户id
    private Long loanMemberId;
    //订单状态
    private Integer orderStatus;
    //借款金额
    private BigDecimal loanAmount;
    //已还金额
    private BigDecimal repayAmount;
    //发生逾期金额
    private BigDecimal factOverdueFine;
    //借款银行ID
    private Long loanBankId;
    //还款银行ID
    private Long repayBankId;
    //费率
    private BigDecimal rate;
    //手续费
    private BigDecimal proceCost;
    //利息费
    private BigDecimal accrualCost;
    //借款期次
    private Integer loanLimit;
    //最新期次
    private Integer nextLimit;
    //下次还款时间
    private Long nextTime;
    //申请时间
    private Long applyTime;
    //确认时间
    private Long confirmTime;
    //放款时间
    private Long loanTime;
    //借款结清时间
    private Long finishTime;
    //当前申请次数
    private Integer applyCount;
    //业务员(经办人)ID
    private Long salesmanId;
    //来源
    private String source;
    //创建时间
    private Long createTime;
    //预留字段1
    private String column1;
    //预留字段2
    private String column2;
    //预留字段3
    private String column3;

    /** 借款人姓名 */
    @Transient
    private String loanMemberName;
    /** 借款人手机号 */
    @Transient
    private String loanMemberPhone;
}
