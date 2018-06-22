package com.yiyun.constants;

/**
 * @author XieLinGe
 * @version 1.0
 * @Title 短信模板常量类
 * @Description
 * @createDate 2017年8月14日
 * @modifier
 * @modifyDate
 */
public class MessageConstant {

    public static String MESSAGE_BWTD = "百悟科技";

    public static String MESSAGE_CLTD = "253云通讯";


    // 0发送验证码    A1 验证码
    public static String MESSAGE_FSYZM = "【熠云】您的手机号验证码为：A1，请勿向任何单位或个人泄露。";

    /*
    用户短信
	 */

    // 1确认借款    A1 商户名 A2课程名
    public static String MESSAGE_QRJK = "【熠云】您在（A1）的A2课程已成功提交，正在审核中，请您耐心等待。";


    // 2审核通过 首次付款   A1 商户名
    public static String MESSAGE_SHTGSF = "【熠云】您在（A1）的英语课程已审核通过，请尽快支付定金以便开始上课。支付方式微信搜索小程序“熠云” ";

    // 3 定金支付成功  A1 商户名  A2课程名
    public static String MESSAGE_DJZFCG = "【熠云】您在（A1）预定的A2课程定金已成功支付，联系商家确认上课。";

    // 4提醒支付   A1 期次
    public static String MESSAGE_TXZF = "【熠云】您的第A1期账单已到期，避免耽误以后课程，请您尽快支付。";

    //5 取消订单
    public final static String MESSAGE_QXDD = "【熠云】您的已成功账单取消，详情请在公众微信号内查看。";

    //6 订单支付成功 A1期次
    public final static String MESSAGE_DDZFCG = "【熠云】您的第A1期账单支付成功，详情请在公众微信号内查看。";
    //7 逾期提醒  A1期次
    public final static String MESSAGE_YQTX = "【熠云】您的第A1期账单支付超期，避免耽误以后课程，请您尽快支付。";
    //8 还款失败  A1期次
    public final static String MESSAGE_HKSB = "【熠云】您的第A1期账单支付失败，避免耽误以后课程，请您尽快核对支付。";


    /*
    业务员短信
	 */
    //1 审核成功 A1订单
    public final static String MESSAGE_SHCG = "【熠云】您录入的（A1）订单已审核通过，可以通知用户支付定金。";
    //2 审核失败 A1订单 A2不合格信息项（/分隔）
    public final static String MESSAGE_SHSB = "【熠云】您录入的（A1）订单（A2）审核失败，请重新填写资料。";


    /*
    商户短信
     */
    // 1放款   A1 订单号  A2  金额
    public static String MESSAGE_FK = "【熠云】（A1）订单已成功放款A2元，请注意查收核实。";
    // 2用户取消   A1 订单号
    public static String MESSAGE_QX = "【熠云】（A1）订单的用户已取消订单，请核实确认。";
    // 3逾期提醒   A1 订单号
    public static String MESSAGE_TX = "【熠云】（A1）订单的用户支付超期，请尽快与用户联系核实，询问情况。";
    // 4催收   A1 订单号
    public static String MESSAGE_CS = "【熠云】（A1）订单的用户支付超期，为不影响本店的用户质量，请尽快与用户联系还款。";


    // 1验证码
    public final static Integer COMMON_MESSAGE_0 = 0;


    /*
    用户短信类别
     */
    // 1确认借款
    public final static Integer USER_MESSAGE_1 = 1;
    // 2审核通过 首次付款
    public final static Integer USER_MESSAGE_2 = 2;
    // 3定金支付成功
    public final static Integer USER_MESSAGE_3 = 3;
    // 4 提醒支付
    public final static Integer USER_MESSAGE_4 = 4;
    // 5取消订单
    public final static Integer USER_MESSAGE_5 = 5;
    // 6订单支付成功
    public final static Integer USER_MESSAGE_6 = 6;
    // 7逾期提醒
    public final static Integer USER_MESSAGE_7 = 7;
    // 8还款失败
    public final static Integer USER_MESSAGE_8 = 8;


    /*
        业务员短信类别
    */
    // 1 审核成功
    public final static Integer SALESMAN_MESSAGE_1 = 11;
    // 2 审核失败
    public final static Integer SALESMAN_MESSAGE_2 = 12;


    /*
        商户短信类别
    */
    //  1放款
    public final static Integer BUSSINESS_MESSAGE_1 = 21;
    //  2用户取消
    public final static Integer BUSSINESS_MESSAGE_2 = 22;
    //  3逾期提醒
    public final static Integer BUSSINESS_MESSAGE_3 = 23;
    //  4催收
    public final static Integer BUSSINESS_MESSAGE_4 = 24;


    public String getMessageContentByType(Integer i) {
        String content = "";
        switch (i) {
            case 0:
                content = MESSAGE_FSYZM;
                break;
            case 1:
                content = MESSAGE_QRJK;
                break;
            case 2:
                content = MESSAGE_SHTGSF;
                break;
            case 3:
                content = MESSAGE_DJZFCG;
                break;
            case 4:
                content = MESSAGE_TXZF;
                break;
            case 5:
                content = MESSAGE_QXDD;
                break;
            case 6:
                content = MESSAGE_DDZFCG;
                break;
            case 7:
                content = MESSAGE_YQTX;
                break;
            case 8:
                content = MESSAGE_HKSB;
                break;
            case 11:
                content = MESSAGE_SHCG;
                break;
            case 12:
                content = MESSAGE_SHSB;
                break;
            case 21:
                content = MESSAGE_FK;
                break;
            case 22:
                content = MESSAGE_QX;
                break;
            case 23:
                content = MESSAGE_TX;
                break;
            case 24:
                content = MESSAGE_CS;
                break;
        }

        return content;
    }

}
