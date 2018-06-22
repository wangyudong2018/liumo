package com.yiyun.web.common.utils;

import com.yiyun.web.common.config.SmsConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsUtil {

    private static SmsConfigurationProperties smsConfigurationProperties;

    public static SmsConfigurationProperties getSmsConfigurationProperties() {
        return smsConfigurationProperties;
    }

    @Autowired(required = true)
    public void setSmsConfigurationProperties(SmsConfigurationProperties smsConfigurationProperties) {
        SmsUtil.smsConfigurationProperties = smsConfigurationProperties;
    }

}
