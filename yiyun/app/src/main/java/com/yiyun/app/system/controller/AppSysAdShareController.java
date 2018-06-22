package com.yiyun.app.system.controller;

import com.yiyun.app.common.annotation.JSONParam;
import com.yiyun.app.common.controller.BaseController;
import com.yiyun.app.common.utils.BeanValidator;
import com.yiyun.app.system.req.SysAdShareReq;
import com.yiyun.app.system.resp.SysAdShareResp;
import com.yiyun.app.system.service.SysAdShareService;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @author YanXiaoGuang
 * @version 1.0
 * @Title 广告分享类
 * @Description
 * @createDate 2017年12月19日
 * @modifier
 * @modifyDate
 */
@RestController
@Api(value = "AppSysAdShareController", description = "广告分享")
@RequestMapping("/appSysAdShare")
public class AppSysAdShareController extends BaseController {

    private static final Logger logger = LogManager.getLogger(AppSysAdShareController.class.getName());

    @Autowired
    private SysAdShareService sysAdShareService;

    /**
     * @Title 获取广告信息
     * @Description
     * @author YanXiaoGuang
     * @createDate 2017年12月19日2017年12月19日
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    @ApiOperation(value = "获取广告信息", notes = "获取广告信息")
    @ApiImplicitParams({@ApiImplicitParam(required = true, name = "data", value = "类别,category:0获取全部 1开屏页 2悬浮窗 3弹窗 4icon 5banner;sysType:1安卓 2苹果版微信 3苹果 4微信通用 5安卓版微信 6手机通用 7通用", dataType = "String", paramType = "query", defaultValue = "{\"category\":\"1\",\"sysType\":\"1\"}")})
    @ApiResponses({@ApiResponse(code = 200, message = "androidImg:安卓图片，commonImg：通用图片，content：内容，iosImg：ios图片，link：链接，打开方式1不跳转 2调起app 3直接打开，remark:备注，sortNo:排序号，summary:简介，title:标题，wxImg:微信图片")})
    @PostMapping(value = "/getSysAdShare")
    public void getSysAdShare(@JSONParam("data") SysAdShareReq req, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 校验数据格式
        BeanValidator.ValidateResult validateResult = BeanValidator.validateBean(req);
        if (!validateResult.isValid()) {
            returnFail(validateResult.getMessage(), response);
            return;
        }
        List<SysAdShareResp> list = null;
        Integer category = Integer.parseInt(req.getCategory());
        Integer sysType;
        try {
            if (null != req.getSysType()) {
                sysType = Integer.parseInt(req.getSysType());
                list = sysAdShareService.list(category, sysType);
            } else if (category == 0) {//查询所有
                list = sysAdShareService.list(null, null);
            } else {
                list = sysAdShareService.list(category, null);
            }
            if (list != null && list.size() > 0) {
                logger.info("查询广告信息" + list.toString());
                returnSuccess(list, "查询数据成功！", response);
            } else {
                logger.info("没有查询到数据");
                returnFail("没有符合数据！", response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取广告信息失败", e);
            returnFail("网络异常");
        }
    }
}
