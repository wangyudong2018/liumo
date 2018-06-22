package com.yiyun.app.mapp.controller;

import com.yiyun.app.common.config.redis.RedisTemplateDAO;
import com.yiyun.app.common.controller.MappBaseController;
import com.yiyun.app.common.utils.SmsUtil;
import com.yiyun.app.mapp.service.MMemberService;
import com.yiyun.app.rabbitMQ.util.SendMqUtil;
import com.yiyun.constants.RedisConstants;
import com.yiyun.resp.CheckSmsResp;
import com.yiyun.resp.LoginResp;
import com.yiyun.resp.SendMessageResp;
import com.yiyun.utils.DataUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @title 登录注册
 * @author Xingbz
 * @createDate 2018-6-10
 */
@Controller
@RequestMapping(value = "/appminiprog")
public class MMainController extends MappBaseController {
    private static final Logger logger = LoggerFactory.getLogger(MMainController.class);

    @Autowired
    private MMemberService mMemberService;

    @Autowired
    private RedisTemplateDAO redisTemplateDAO;

    @ResponseBody
    @RequestMapping(value = "/logminiprog", produces = "application/json; charset=utf-8")
    public Object logminiprog(String mobile, String code, String investCode, HttpSession session) {
        logger.info("XInfO.[登录]请求参数 : " + mobile + " , " + code + " , " + investCode);
        //检查参数是否合法
        if (StringUtils.isBlank(mobile)) {
            return returnFailCode("05", "缺少参数：手机号为空");
        }
        if (!DataUtil.isMobile(mobile)) {
            return returnFail("手机号码格式不正确");
        }

        if (StringUtils.isNotBlank(investCode) && (!NumberUtils.isDigits(investCode) || investCode.length() != 6)) {//如果填写了邀请码 , 必须符合格式
            return returnFail("邀请码格式错误 , 只能为6位数字");
        }

        try {
//校验验手机验证码
            CheckSmsResp resp = mMemberService.checkcode(mobile, code, session);
            if (!resp.isFlag()) {
                return returnFail(resp.getMessage());
            }

            LoginResp result = mMemberService.login(mobile, investCode, session);
            if (result != null) {
                //登录成功 , 并删除缓存中的验证码(即如果再次操作需要重新获取验证码 , 防止多次提交)
//                session.removeAttribute(RedisConstants.CACHE_KEY_LOGIN_SMS_CODE + mobile);
                redisTemplateDAO.delete(RedisConstants.CACHE_KEY_LOGIN_SMS_CODE + mobile, 10);
                Map<String, Object> map = new HashMap<>();
                map.put("PARTNER_ID", result.getMemberId());
                logger.info("XInfO.[登录]登录成功 . partnerId : " + result.getMemberId());
                return returnData(map);
            }
            return returnFailCode("01", "登录失败");
        } catch (Exception e) {
            logger.error("XErroR.[登录]请求异常", e);
            return returnFailCode("01", "登录出现异常");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getSmscode", produces = "application/json; charset=utf-8")
    public String sendSms(String mobile, HttpSession session) {
        logger.info("XInfO.[获取验证码]请求参数 : " + mobile);
        if (StringUtils.isBlank(mobile)) {
            return returnFailCode("05", "缺少参数：手机号为空");
        }

        if (!DataUtil.isMobile(mobile)) {
            return returnFail("手机号有误");
        }

        final String SMS_KEY = RedisConstants.CACHE_KEY_LOGIN_SMS_CODE + mobile;
        final String COUNT_KEY = RedisConstants.CACHE_KEY_LOGIN_SMS_CODE_COUNT + mobile;

        if (!SmsUtil.getSmsConfigurationProperties().isSmsSwitch()) {//短信开关
//            session.setAttribute(SMS_KEY, "1111");
            redisTemplateDAO.put(SMS_KEY, "1111", 10, 1800L);
            Map<String, Object> data = new HashMap<>();
            data.put("code", "1111");
            return returnData(data);
        }

        String count = redisTemplateDAO.get(COUNT_KEY, 10);//发送短信的次数
//        String count = (String) session.getAttribute(COUNT_KEY);
        if (StringUtils.isNotBlank(count) && Integer.valueOf(count) >= 20) {
            return returnFail("短信发送失败 , 该号码已达今日上限");
        }

        int code = (int) ((Math.random() * 9 + 1) * 1000);//随机4位验证码
        logger.error("XErroR.[获取验证码] 短信验证码 : " + code + " , redisKey : " + SMS_KEY);
        SendMessageResp resp = SendMqUtil.sendShortMessageMine(mobile, String.valueOf(code));
        if (resp.isFlag()) {
            redisTemplateDAO.put(SMS_KEY, String.valueOf(code), 10, 1800L);
//            session.setAttribute(SMS_KEY, code);
            String smsCount = StringUtils.isBlank(count) ? "1" : Integer.valueOf(count) + 1 + "";
            redisTemplateDAO.put(COUNT_KEY, smsCount, 10, RedisConstants.OUT_TIME_ONE_DAY);//24h限制
//            session.setAttribute(COUNT_KEY, smsCount);
            Map<String, Object> data = new HashMap<>();
            data.put("code", code);
            logger.warn("XWarn.[获取验证码]发送成功 . 验证码 : " + code);
            return returnData(data);
        } else {
            return returnFail("短信发送失败");
        }

    }
}