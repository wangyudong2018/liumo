package com.yiyun.domain;

import com.yiyun.constants.CommonConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liyinq
 * @title 黑名单列表
 * @date Tue Jan 09 14:13:53 CST 2018
 */
@Data
public class UBlacklistDO implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long id;
    //用户id
    private Long userId;
    //用户姓名
    private String userName;
    //用户电话
    private String userPhone;
    //用户身份证
    private String userIdcard;
    //拉入黑名单时间
    private Long addTime;
    //描述拉入黑名单原因
    private String remark;
    //来源(来自第三方时)
    private String source;
    //操作人
    private String opeator;
    //有效状态
    private Long blackState;
    //创建时间
    private Long createTime;
    //
    private String column1;
    //
    private String column2;
    //
    private String column3;

    public UBlacklistDO() {
    }

    public UBlacklistDO(Long userId, String userPhone, String opeator) {
        this.userId = userId;
        this.userPhone = userPhone;
        this.opeator = opeator;
        this.blackState = CommonConstants.YES.longValue();
        this.createTime = System.currentTimeMillis();
    }
}
