package com.yiyun.web.wechat.controller;

import com.yiyun.constants.MemberConstants;
import com.yiyun.constants.RedisConstants;
import com.yiyun.req.MessageReq;
import com.yiyun.resp.CheckSmsResp;
import com.yiyun.resp.LoginResp;
import com.yiyun.resp.MemberResp;
import com.yiyun.resp.SendMessageResp;
import com.yiyun.utils.DataUtil;
import com.yiyun.web.base.controller.MyBaseController;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import com.yiyun.web.common.utils.SmsUtil;
import com.yiyun.web.rabbitMQ.util.SendMqUtil;
import com.yiyun.web.wechat.service.IMemberService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Xingbz
 * @version 1.0
 * @Title 前端用户控制类
 * @Description 前端用户控制类
 * @createDate 2018年3月20日
 */
@Controller
@Log4j2
@RequestMapping("/wechat/appMember")
public class AppMemberController extends MyBaseController {

//    private static final Logger logger = LogManager.getLogger(AppUsersController.class.getName());

    @Autowired
    private RedisTemplateDAO redisTemplateDAO;

    @Autowired
    private IMemberService memberService;

    /**
     * @title 个人中心
     * @param memberId 会员编号
     * @param phone 手机号
     */
    @RequestMapping(value = "/mine", method = RequestMethod.POST)
    public void mine(Long memberId, String phone, HttpServletResponse response) {
        if (StringUtils.isBlank(phone)) {
            returnFail("请求参数有误", response);
            return;
        }

        MemberResp memberResp = memberService.findMember(memberId, phone);
        if (memberResp == null) {
            returnFail("未找到该用户信息", response);
        } else {
            returnSuccess(memberResp, response);
        }
    }

    /**
     * @Title 前端用户登录
     * @Description
     * @author Xbz
     * @createDate 2017年7月20日
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(String phone, String msgInputCode, String type, String redirectUrl, HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(type)) {
            type = MemberConstants.LOGIN_PHONE;//默认手机号登录
        }

        if (StringUtils.equalsAny(type, MemberConstants.LOGIN_PHONE, MemberConstants.BIND_PHONE)) {
            if (StringUtils.isAnyBlank(phone, msgInputCode)) {
                returnFail("手机号或验证码不能为空", response);
                return;
            }

            if (!DataUtil.isMobile(phone)) {
                returnFail("手机号码格式不正确", response);
                return;
            }

            //校验验手机验证码
            CheckSmsResp resp = memberService.checkcode(phone, msgInputCode);
            if (!resp.isFlag()) {
                returnFail(resp.getMessage(), response);
                return;
            }
        } else if (MemberConstants.LOGIN_WECHAT.equals(type)) {
//            //判断openId
//            if (org.apache.commons.lang.StringUtils.isBlank(openId)) {
//                returnFail("微信标识不能为空", response);
//                return;
//            }
        } else {
            returnFail("请求参数有误", response);
            return;
        }

        LoginResp result = memberService.login(phone, type);
        if (result != null) {
            //登录成功 , 并删除缓存中的验证码(即如果再次操作需要重新获取验证码 , 防止多次提交)
            redisTemplateDAO.delete(RedisConstants.CACHE_KEY_LOGIN_SMS_CODE + phone, 10);
            result.setRedirectUrl(redirectUrl);
            returnSuccess(result, "请求成功", response);
        } else {
            returnFail("请求失败", response);
        }
    }

    /**
     * @Title 退出登录
     * @Description
     * @author Xingbz
     * @createDate 2017年8月14日
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(String phone, HttpServletResponse response) throws Exception {
        if (StringUtils.isAnyBlank(phone)) {
            returnFail("请求参数有误", response);
            return;
        }

        boolean result = memberService.logout(phone);
        if (result) {
            returnSuccess("退出成功", response);
        } else {
            returnFail("退出失败", response);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/sendSms", produces = "application/json; charset=utf-8")
    public String sendSms(MessageReq req) {
        String phone = req.getPhone();
        if (!DataUtil.isMobile(phone)) {
            return returnFail("手机号有误");
        }

        final String SMS_KEY = RedisConstants.CACHE_KEY_LOGIN_SMS_CODE + phone;
        final String COUNT_KEY = RedisConstants.CACHE_KEY_LOGIN_SMS_CODE_COUNT + phone;

        if (!SmsUtil.getSmsConfigurationProperties().isSmsSwitch()) {//短信开关
            redisTemplateDAO.put(SMS_KEY, "1111", 10, 1800L);
            return returnSucess("短信发送成功");
        }

        String count = redisTemplateDAO.get(COUNT_KEY, 10);//发送短信的次数
        if (StringUtils.isNotBlank(count) && Integer.valueOf(count) >= 20) {
            return returnFail("短信发送失败 , 该号码已达今日上限");
        }

        int code = (int) ((Math.random() * 9 + 1) * 1000);//机4位验证码

        SendMessageResp resp = SendMqUtil.sendShortMessageMine(phone, String.valueOf(code));
        if (resp.isFlag()) {
            redisTemplateDAO.put(SMS_KEY, String.valueOf(code), 10, 1800L);
            String smsCount = StringUtils.isBlank(count) ? "1" : Integer.valueOf(count) + 1 + "";
            redisTemplateDAO.put(COUNT_KEY, smsCount, 10, RedisConstants.OUT_TIME_ONE_DAY);//24h限制
            return returnSucess("短信发送成功");
        } else {
            return returnFail("短信发送失败");
        }
    }


    // ========================= 微信公众号相关网页

    @GetMapping("/toTemp")
    public String toTemp(String redirectUrl, Model model) {
        model.addAttribute("redirectUrl", redirectUrl);//登录成功后需要跳转的路径
        return "wechat/loading";
    }

    @GetMapping("/toLogin")
    public String toLogin(String redirectUrl, Model model) {
        model.addAttribute("redirectUrl", redirectUrl);//登录成功后需要跳转的路径
        return "wechat/onload";
    }

    @GetMapping("/toOrderList")
    public String toOrderList(String phone, Model model) {
        model.addAttribute("phone", phone);
        return "wechat/list";
    }

    @GetMapping("/toMine")
    public String toMine(String phone, Model model) {
        model.addAttribute("phone", phone);
        return "wechat/person";
    }

}