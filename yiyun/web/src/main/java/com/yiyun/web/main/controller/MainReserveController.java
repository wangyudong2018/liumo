package com.yiyun.web.main.controller;

import com.yiyun.constants.RedisConstants;
import com.yiyun.constants.ReserveConstants;
import com.yiyun.domain.OReserve;
import com.yiyun.resp.CheckSmsResp;
import com.yiyun.utils.DataUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import com.yiyun.web.order.service.OReserveService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @title 预约订单Controller
 * @description
 * @author Xingbz
 * @createDate 2018/3/12
 * @version 1.0
 */
@RestController
@RequestMapping("/mReserve")
public class MainReserveController extends BaseController {

    @Autowired
    private OReserveService reserveService;

    @Autowired
    private RedisTemplateDAO redisTemplateDAO;

    @PostMapping(value = "/commitReserve", produces = "application/json;charset=utf-8")
    public String commitReserve(OReserve reserve, String smsCode) {
        if (StringUtils.isBlank(smsCode)) {
            return returnFail("验证码不能为空");
        }

        if (reserve != null && DataUtil.isMobile(reserve.getUserPhone())) {
            CheckSmsResp resp = reserveService.checkcode(reserve.getUserPhone(), smsCode);
            if (!resp.isFlag()) {//验证码校验未通过
                return returnFail(resp.getMessage());
            }

            long current = System.currentTimeMillis();//当前时间毫秒数
            long timeLow = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
            long timeUp = timeLow + 24 * 60 * 60 * 1000;//明天零点零分零秒的毫秒数
            Map<String, Object> reserveParam = new HashMap<>();
            reserveParam.put("userPhone", reserve.getUserPhone());
            reserveParam.put("timeLow", timeLow);
            reserveParam.put("timeUp", timeUp);
            List<OReserve> list = reserveService.list(reserveParam);
            if (list != null && !list.isEmpty()) {
                return returnFail("您今日已提交过申请 , 请等待专员联系");
            }

            reserve.setReserveStauts(ReserveConstants.RESERVE_STATUS_YTJ);
            reserve.setCreateTime(System.currentTimeMillis());
            reserveService.save(reserve);

            //验证通过保存预约订单 , 并删除缓存中的验证码(即如果再次操作需要重新获取验证码 , 防止多次提交)
            redisTemplateDAO.delete(RedisConstants.CACHE_KEY_SMS_CODE + reserve.getUserPhone(), 10);
            return returnSucess("提交成功 , 请等待专员联系您");
        } else {
            return returnFail("信息不正确");
        }
    }

    @PostMapping(value = "/commitReserveNoCode", produces = "application/json;charset=utf-8")
    public String commitReserveNoCode(OReserve reserve) {

        if (reserve == null || !DataUtil.isMobile(reserve.getUserPhone())) {
            return returnFail("手机号码错误");
        }

        long current = System.currentTimeMillis();//当前时间毫秒数
        long timeLow = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long timeUp = timeLow + 24 * 60 * 60 * 1000;//明天零点零分零秒的毫秒数
        Map<String, Object> reserveParam = new HashMap<>();
        reserveParam.put("userPhone", reserve.getUserPhone());
        reserveParam.put("timeLow", timeLow);
        reserveParam.put("timeUp", timeUp);
        List<OReserve> list = reserveService.list(reserveParam);
        if (list != null && !list.isEmpty()) {
            return returnFail("您今日已提交申请 , 请等待专员联系");
        }

        reserve.setReserveStauts(ReserveConstants.RESERVE_STATUS_YTJ);
        reserve.setCreateTime(System.currentTimeMillis());
        reserveService.save(reserve);

        return returnSucess("提交成功 , 请等待专员联系您");
    }
}
