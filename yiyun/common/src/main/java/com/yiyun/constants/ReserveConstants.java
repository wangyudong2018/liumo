package com.yiyun.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Xingbz
 * @version 1.0
 * @title 预约订单常量类
 * @description
 * @createDate 2018/3/12
 */
public class ReserveConstants {

    /**
     * 预约类型 1 : 客户贷款预约
     */
    public static final Integer RESERVE_TYPE_BORROWER = 1;

    /**
     * 预约类型 2 : 合伙人预约
     */
    public static final Integer RESERVE_TYPE_PARTNER = 2;

    /**
     * 预约状态 -3:已过期
     */
    public static final Integer RESERVE_STATUS_YGQ = -3;
    /**
     * 预约状态 -2:后台取消
     */
    public static final Integer RESERVE_STATUS_HTQX = -2;
    /**
     * 预约状态 -1:用户取消
     */
    public static final Integer RESERVE_STATUS_YHQX = -1;
    /**
     * 预约状态 1:已提交
     */
    public static final Integer RESERVE_STATUS_YTJ = 1;

    /**
     * 预约状态 2:已受理
     */
    public static final Integer RESERVE_STATUS_YSL = 2;

    /**
     * 预约状态 3:进一步沟通
     */
    public static final Integer RESERVE_STATUS_JXGT = 3;

    /**
     * 预约状态 4:成功
     */
    public static final Integer RESERVE_STATUS_CG = 4;

    /**
     * 预约状态 5:失败
     */
    public static final Integer RESERVE_STATUS_SB = 5;

    /**
     * 预约状态 6:已评价
     */
    public static final Integer RESERVE_STATUS_YPJ = 6;

    public static final Map<Integer, String> RESERVE_STATUS_MAP = new HashMap<>(7);

    static {
        RESERVE_STATUS_MAP.put(RESERVE_STATUS_YGQ, "已过期");
        RESERVE_STATUS_MAP.put(RESERVE_STATUS_YHQX, "用户取消");
        RESERVE_STATUS_MAP.put(RESERVE_STATUS_YTJ, "已提交");
        RESERVE_STATUS_MAP.put(RESERVE_STATUS_YSL, "已受理");
        RESERVE_STATUS_MAP.put(RESERVE_STATUS_JXGT, "进一步沟通");
        RESERVE_STATUS_MAP.put(RESERVE_STATUS_CG, "成功");
        RESERVE_STATUS_MAP.put(RESERVE_STATUS_SB, "失败");
        RESERVE_STATUS_MAP.put(RESERVE_STATUS_YPJ, "已完成");
    }


    public static String getStatusRemark(Integer status) {
        return RESERVE_STATUS_MAP.get(status);
    }

    public static Integer getStatusToApp(Integer status) {
        Integer i = 0;
        switch (status) {
            case -3://已取消
                i = -1;
                break;
            case -2://已过期
                i = -2;
                break;
            case -1://已取消
                i = -1;
                break;
            case 1://已提交
                i = 1;
                break;
            case 2://已受理
                i = 2;
                break;
            case 3://已受理
                i = 2;
                break;
            case 4://处理成功
                i = 4;
                break;
            case 5://处理失败
                i = 5;
                break;
            case 6://处理失败
                i = 6;
                break;
        }
        return i;
    }
    public static String getStatusToAppRemark(Integer status) {
        String str = "";
        switch (status) {
            case -3:
                str = "已取消";
                break;
            case -2:
                str= "已过期";
                break;
            case -1:
                str = "已取消";
                break;
            case 1:
                str = "已提交";
                break;
            case 2:
                str = "已受理";
                break;
            case 3:
                str = "已受理";
                break;
            case 4:
                str = "处理成功";
                break;
            case 5:
                str = "处理失败";
                break;
            case 6:
                str = "已完成";
                break;
        }
        return str;
    }

}
