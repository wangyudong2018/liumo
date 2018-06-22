package com.yiyun.app.system.controller;

import com.yiyun.app.common.annotation.JSONParam;
import com.yiyun.app.common.controller.BaseController;
import com.yiyun.app.common.req.CommonParamReq;
import com.yiyun.app.common.utils.BeanValidator;
import com.yiyun.app.system.req.FeedbackReq;
import com.yiyun.app.system.service.UFeedbackService;
import com.yiyun.domain.UFeedbackDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户反馈
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:40:36
 */

@Api(value = "UFeedbackController", description = "用户反馈")
@RestController
@RequestMapping("/appFeedback")
public class AppUFeedbackController extends BaseController {

    private static final Logger logger = LogManager.getLogger(AppUFeedbackController.class.getName());

    @Autowired
    private UFeedbackService feedbackService;


    /**
     * @Title 添加意见反馈
     * @Description
     * @author XieLinGe
     * @createDate 2017年7月28日
     * @modifier
     * @modifyDate
     * @version 1.0
     */
    @ApiOperation(value = "用户反馈保存", tags = {""}, notes = "用户反馈保存")
    @RequestMapping(value = "/addFeedback", method = RequestMethod.POST)
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "String", name = "common", value = "公共参数"),
            @ApiImplicitParam(paramType = "query", name = "data", value = "反馈参数,memberId：用户ID,phone:手机号，name：姓名，type:反馈类别：APP_FEEDBACK_TYPE，message:用户反馈信息，phoneModel:手机型号，operatingSystem:操作系统，phoneImei：手机串码", required = true, defaultValue = "{\"memberId\":\"11\",\"phone\":\"123423556233\",\"type\":\"1\",\"message\":\"测试反馈\",\"phoneModel\":\"iPhone7\",\"operatingSystem\":\"IOS10.2\"}")})
    public void addFeedback(@JSONParam("common") CommonParamReq common,
                            @JSONParam("data") FeedbackReq req, HttpServletResponse response) {
        //请求参数校验数据格式
        BeanValidator.ValidateResult validateResult = BeanValidator.validateBean(req);
        if (!validateResult.isValid()) {
            returnFail(validateResult.getMessage(), response);
            return;
        }
        try {
            UFeedbackDO back = new UFeedbackDO(req.getMemberId(), req.getPhone(), req.getType(), req.getMessage(), req.getPhoneModel(), req.getOperatingSystem(), req.getPhoneImei());
            feedbackService.save(back);
            logger.info("意见反馈保存成功，请求参数：" + req.toString());
            returnSuccess("意见反馈录入成功", response);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("意见反馈保存失败！", e);
            returnFail("网络繁忙", response);
        }
    }
}


