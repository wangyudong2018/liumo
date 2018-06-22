package com.yiyun.web.wechat.controller;

import com.yiyun.vo.ReserveResp;
import com.yiyun.web.base.controller.MyBaseController;
import com.yiyun.web.wechat.service.AppReserveService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;


/**
 * @author Xingbz
 * @title 预约
 * @date Thu Mar 08 16:26:18 CST 2018
 */
@Controller
@RequestMapping("/wechat/appReserve")
public class AppReserveController extends MyBaseController {

    @Autowired
    private AppReserveService appReserveService;

    /* 订单列表展示 */
    @PostMapping("/listReserve")
    public void listReserve(String phone, HttpServletResponse response) throws ParseException {
        // 校验数据格式
        if (StringUtils.isAnyBlank(phone)) {
            returnFail("手机号不能为空", response);
            return;
        }

        List<ReserveResp> list = appReserveService.listReserve(phone);
        if (null == list) {
            returnFail("没有查到相关数据", response);
        } else {
            returnSuccess(list, "查询成功", response);
        }
    }
}
