package com.yiyun.app.order.controller;

import com.yiyun.app.common.annotation.JSONParam;
import com.yiyun.app.common.config.redis.RedisTemplateDAO;
import com.yiyun.app.common.controller.BaseController;
import com.yiyun.app.order.service.OReserveService;
import com.yiyun.req.ReserveReq;
import com.yiyun.vo.ReserveDetailResp;
import com.yiyun.vo.ReserveResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;


/**
 * @author Xingbz
 * @title 预约
 * @date Thu Mar 08 16:26:18 CST 2018
 */
@RestController
@Api(value = "OReserveController", description = "预约")
@RequestMapping("/oReserve")
public class OReserveController extends BaseController {

    @Autowired
    private OReserveService oReserveService;

    @Autowired
    private RedisTemplateDAO redisTemplateDAO;

    /*
        评价订单
         */
    @PostMapping("/comment")
    @ApiImplicitParams({@ApiImplicitParam(required = true, name = "data", value = "", paramType = "query", defaultValue = "")})
    public void comment(@JSONParam("data") ReserveReq req, HttpServletResponse response) throws ParseException {
        if (null == req.getOrderId() || StringUtils.isBlank(req.getOrderId())) {
            returnFail("订单ID不能为空", response);
            return;
        }
        if (null == req.getComment() || StringUtils.isBlank(req.getComment())) {
            returnFail("评价不能为空", response);
            return;
        }
        try {
            oReserveService.commentOrder(req);
        } catch (Exception e) {
            e.printStackTrace();
            returnFail("网络繁忙，请稍后重试", response);
        }
        returnSuccess("操作成功", response);
    }

    /*
        预约取消、修改接口
         */
    @PostMapping("/cancleOrUpdateById")
    @ApiImplicitParams({@ApiImplicitParam(required = true, name = "data", value = "", paramType = "query", defaultValue = "{\"type\":\"2\",\"orderId\":\"1001\",\"productId\":\"2\",\"reserveTime\":\"1101111\"}")})
    public void cancleOrUpdateById(@JSONParam("data") ReserveReq req, HttpServletResponse response) throws ParseException {
        if (null == req.getOrderId() || StringUtils.isBlank(req.getOrderId())) {
            returnFail("订单ID不能为空", response);
            return;
        }
        if (null == req.getType() || StringUtils.isBlank(req.getType())) {
            returnFail("类别不能为空", response);
            return;
        }
        try {
            oReserveService.cancelOrUpdateById(req);
        } catch (Exception e) {
            e.printStackTrace();
            returnFail("网络繁忙，请稍后重试", response);
        }
        returnSuccess("操作成功", response);
    }

    /*
        预约详情展示
         */
    @PostMapping("/getInfoById")
    @ApiImplicitParams({@ApiImplicitParam(required = true, name = "data", value = "", paramType = "query", defaultValue = "{\"orderId\":\"1\"}")})
    public void getInfoById(@JSONParam("data") ReserveReq req, HttpServletResponse response) {
        if (null == req.getOrderId() || StringUtils.isBlank(req.getOrderId())) {
            returnFail("订单ID不能为空", response);
            return;
        }
        ReserveDetailResp resp = oReserveService.getById(Long.valueOf(req.getOrderId()));
        returnSuccess(resp, "查询成功", response);
    }

    /*
        产品列表展示
         */
    @PostMapping("/listReserve")
    @ApiImplicitParams({@ApiImplicitParam(required = true, name = "data", value = "", paramType = "query", defaultValue = "{\"loginPhone\":\"134324233\"}")})
    public void listReserve(@JSONParam("data") ReserveReq req, HttpServletResponse response) throws ParseException {
        // 校验数据格式
        if (null == req.getLoginPhone() || StringUtils.isBlank(req.getLoginPhone())) {
            returnFail("登录手机号不能为空", response);
            return;
        }
        List<ReserveResp> list = oReserveService.listReserve(req.getLoginPhone());
        if (null == list) {
            returnFail("没有查到相关数据", response);
        } else {
            returnSuccess(list, "查询成功", response);
        }
    }

    /*
    产品预约
     */
    @PostMapping("/commitReserve")
    @ApiImplicitParams({@ApiImplicitParam(required = true, name = "data", value = "", paramType = "query", defaultValue = "{\"userPhone\":\"134324233\",\"productId\":\"1\",\"reserveTime\":\"2018-2-2 10:20\"}")})
    public void commitReserve(@JSONParam("data") ReserveReq req, HttpServletResponse response) {
        // 校验数据格式
        if (null == req.getProductId() || StringUtils.isBlank(req.getProductId())) {
            returnFail("产品ID不能为空", response);
            return;
        }
        if (null == req.getReserveTime() || StringUtils.isBlank(req.getReserveTime())) {
            returnFail("预约时间不能为空", response);
            return;
        }
        if (null == req.getUserPhone() || StringUtils.isBlank(req.getUserPhone())) {
            returnFail("预约手机号不能为空", response);
            return;
        }
        /*
            同一手机号只能存在一个已提交订单
         */
        Boolean flag = oReserveService.isExistCommitOrder(req.getUserPhone());
        if (true == flag) {
            returnFail("您已有预约提交，请耐心等待", response);
            return;
        }

        try {
            oReserveService.commitReserve(req);
        } catch (Exception e) {
            e.printStackTrace();
            returnFail("网络繁忙，请稍后重试", response);
        }
        returnSuccess("预约成功", response);
    }


}
