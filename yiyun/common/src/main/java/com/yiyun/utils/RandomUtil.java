package com.yiyun.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Random;

/**
 * @title
 * @description
 * @author Xingbz
 * @createDate 2018/1/16
 * @version 1.0
 */
public class RandomUtil {
    private static Random ran = new Random();

    public static int randomInt() {
        return ran.nextInt(10);
    }

    public static String randomInvestCode() {
        return RandomUtils.nextInt(100000, 999999) + "";
    }

    /**
     * 生成放款批次号  , =订单号 , 若订单号长度 < 15 , 则补充随机数 , 若大于15 取前15个数字
     * @param orderSn
     * @return
     */
    public static String randomserinlNum(String orderSn) {

        if (StringUtils.isBlank(orderSn)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        orderSn = orderSn.toUpperCase().replaceFirst("O", "");
        int length = orderSn.length();

        if (length >= 15) {
            sb.append(orderSn.substring(0, 15));
        } else {
            sb.append(orderSn);
            for (int i = 0; i < 15 - length; i++) {
                sb.append(randomInt());
            }
        }

        return sb.toString();
    }

}
