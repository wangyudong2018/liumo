package com.yiyun.constants;

import org.apache.commons.lang3.StringUtils;

/**
 * @title
 * @author Xingbz
 * @createDate 2018-6-10
 */
public enum MessageEnums {
    A("A", "您好！客户{s}进抵预约成功，办理时间：{s}，地址：{s}，联系人：{s}，联系电话：{s}，需携带资料：{s}，如有疑问请拨打400-9999-715，感谢！", NewMessageConstatns.MESSAGE_TEMPLATE_1),
    B("B", "您好！客户{s}下户预约成功，下户时间：{s}，联系人：{s}，联系电话：{s}，需准备资料：{s}，如有疑问请拨打400-9999-715，感谢！", NewMessageConstatns.MESSAGE_TEMPLATE_2),
    C("C", "您好！客户{s}公证预约成功，办理时间：{s}，地址：{s}，联系人：{s}，联系电话：{s}，需携带资料：{s}，如有疑问请拨打400-9999-715，感谢！", NewMessageConstatns.MESSAGE_TEMPLATE_3),
    D("D", "您好！客户{s}面签预约成功，面签时间：{s}，地址：{s}，联系人：{s}，联系电话：{s}，需携带资料：{s}，如有疑问请拨打400-9999-715，感谢！", NewMessageConstatns.MESSAGE_TEMPLATE_4);

    private String code;

    private String showMsg;

    private String sourceMsg;

    MessageEnums(String code, String showMsg, String sourceMsg) {
        this.code = code;
        this.showMsg = showMsg;
        this.sourceMsg = sourceMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShowMsg() {
        return showMsg;
    }

    public void setShowMsg(String showMsg) {
        this.showMsg = showMsg;
    }

    public String getSourceMsg() {
        return sourceMsg;
    }

    public void setSourceMsg(String sourceMsg) {
        this.sourceMsg = sourceMsg;
    }

    public static String getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            MessageEnums[] enums = MessageEnums.values();
            for (MessageEnums en : enums) {
                if (code.equals(en.getCode())) {
                    return en.getSourceMsg();
                }
            }
        }
        return null;
    }
}