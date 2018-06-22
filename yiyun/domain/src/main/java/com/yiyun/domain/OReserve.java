package com.yiyun.domain;

import com.yiyun.constants.ReserveConstants;
import com.yiyun.utils.DateUtil;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.ParseException;

/**
 * @author Xingbz
 * @title 预约
 * @date Thu Mar 08 16:26:18 CST 2018
 */
@Data
@Table(name = "o_reserve")
public class OReserve implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    @Id
    private Long id;
    //预约类型 . 1:客户贷款预约;2合伙人预约;
    private Integer type;
    //预约状态 . -1:用户取消;1:已提交;2:已受理待沟通;3:需要进一步沟通;4:成功;5:失败
    private Integer reserveStauts;
    //区域
    private String area;
    //产品类别
    private String productType;
    //产品id , 若无则空
    private Long productId;
    //用户姓名
    private String userName;
    //联系电话
    private String userPhone;
    //预约意向
    private String intention;
    //预约时间
    private Long reserveTime;
    //预约地址(经办人接收后提供)
    private String reserveAdress;
    //业务员(经办人)ID
    private Long salesmanId;
    //受理时间
    private Long acceptTime;
    //结果
    private String result;
    //评价
    private String comment;
    //评价时间
    private Long commentTime;
    //备注
    private String remark;
    //创建时间
    private Long createTime;
    //预留字段1
    private String column1;
    //预留字段2
    private String column2;
    //预留字段3
    private String column3;

    @Transient
    private String statusRemark;

    @Transient
    private String reserveTimeRemark;



    public OReserve() {
    }

    public String getStatusRemark() {
        statusRemark = ReserveConstants.RESERVE_STATUS_MAP.get(reserveStauts);
        return statusRemark;
    }

    public OReserve(String productId, String userPhone, String reserveTime,String loginPhone) throws ParseException {
        this.type = ReserveConstants.RESERVE_TYPE_PARTNER;
        this.reserveStauts = ReserveConstants.RESERVE_STATUS_YTJ;
        this.productId = Long.parseLong(productId);
        this.userPhone = userPhone;
        this.column1=loginPhone;
        this.reserveTime = DateUtil.stringToLong(reserveTime, DateUtil.YYYY_MM_DD_HH_MM);
        this.createTime = System.currentTimeMillis();
    }

    public OReserve(Long id, Integer type, Integer reserveStauts, String area, String productType, Long productId, String userName, String userPhone, String intention, Long reserveTime, String reserveAdress, Long salesmanId, Long acceptTime, String result, String comment, Long commentTime, String remark, Long createTime, String column1, String column2, String column3, String statusRemark) {
        this.id = id;
        this.type = type;
        this.reserveStauts = reserveStauts;
        this.area = area;
        this.productType = productType;
        this.productId = productId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.intention = intention;
        this.reserveTime = reserveTime;
        this.reserveAdress = reserveAdress;
        this.salesmanId = salesmanId;
        this.acceptTime = acceptTime;
        this.result = result;
        this.comment = comment;
        this.commentTime = commentTime;
        this.remark = remark;
        this.createTime = createTime;
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
        this.statusRemark = statusRemark;
    }

    public void setStatusRemark(String statusRemark) {
        this.statusRemark = statusRemark;
    }
}
