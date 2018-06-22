package com.yiyun.app.system.controller;

import com.yiyun.app.common.annotation.JSONParam;
import com.yiyun.app.common.config.redis.RedisTemplateDAO;
import com.yiyun.app.common.controller.BaseController;
import com.yiyun.app.common.req.CommonParamReq;
import com.yiyun.app.common.utils.BeanValidator;
import com.yiyun.app.common.utils.SmsUtil;
import com.yiyun.app.rabbitMQ.util.SendMqUtil;
import com.yiyun.app.system.req.SendSmsReq;
import com.yiyun.app.system.utils.ImgCodeUtil;
import com.yiyun.app.user.resp.ImgCodeResp;
import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.MessageConstants;
import com.yiyun.utils.DataUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Title 图片验证码类
 * @Description 
 * @author XieLinGe
 * @createDate 2017年7月21日
 * @modifier
 * @modifyDate
 * @version 1.0
 */
@Api(value = "ImageCodeController", description = "图片验证码")
@RestController
@RequestMapping("/appImgCode")
public class AppImageCodeController  extends BaseController {
    
//    @Autowired
//	private UUsersService userService;

    @Autowired
	private RedisTemplateDAO redisTemplateDAO;
    
    /**
     * 
     * 
     * @Title 
     * @Description 获取图片验证码
     * @author XieLinGe
     * @createDate 2017年7月24日
     * @modifier
     * @modifyDate
     * @version 1.0
     */
	@ApiOperation(value = "获取图片验证码", tags = {""}, notes = "获取图片验证码")
	@ApiResponses({@ApiResponse(code = 200, message = "isSendMessage:是否发送短信 0展示验证码 1发送短信验证码，imgCode：图形验证码")})
    @RequestMapping(value = "/getImgCode", method = RequestMethod.GET)
    public void getImgCode(@ApiParam("手机号") String phone,@ApiParam("是否需要一直有验证码 0：登录需要图形验证码，1：需要有图片验证码传 2：商户业务员登录")String type, HttpServletRequest request, HttpServletResponse response){
        try {
        	if ("".equals(phone)||phone==null) {
        		returnFail("手机号不能为空", response);
				return;
			}
        	if(!DataUtil.isMobile(phone)){
    			returnFail("手机号格式不正确", response);
				return;
    		}
			String errorCode = redisTemplateDAO.get(MessageConstants.ERROR_SMS_CODE + phone);
			ImgCodeResp resp = new ImgCodeResp();
        	if((StringUtils.isNotBlank(errorCode)||type.equals("1"))&&!type.equals("2")){
				resp.setIsSendMessage(String.valueOf(CommonConstants.NO));
				resp.setImgCode(ImgCodeUtil.createCode(phone));
			}else{
//        		//商户登陆需要校验用户是否存在
//        		if(type.equals("2")){
//					MSalesman salesman = mSalesmanService.getSalemanByPhone(phone);
//					if(salesman==null){
//						returnFail("用户不存在！", response);
//						return;
//					}
//				}
//				resp.setIsSendMessage(String.valueOf(CommonConstants.YES));
//				resp.setImgCode(" ");
//				//发送短信
//				int code = (int)((Math.random()*9+1)*1000);
//				if(!Boolean.valueOf(SmsUtil.getSmsConfigurationProperties().isSmsSwitch())){
//					code=1111;
//				}else{
//					//发送短信
//					SendMqUtil.sendShortMessage(MessageConstants.COMMON_MESSAGE_0.toString(), phone, String.valueOf(code), null, null, null);
//				}
//				redisTemplateDAO.put(MessageConstants.MESSAGE_CODE + phone, String.valueOf(code), null, 1800L);
			}
            returnSuccess(resp,"验证码获取成功",response);
			//ImageIO.write(bi, "JPG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			returnFail("验证码获取失败", response);
		}
    }
    
	
    /**
     * 
     * 
     * @Title 
     * @Description 验证图片验证码
     * @author XieLinGe
     * @createDate 2017年7月24日
     * @modifier
     * @modifyDate
     * @version 1.0
     */
	@ApiOperation(value = "验证图片验证码", tags = {""}, notes = "验证图片验证码")
    @RequestMapping(value = "/checkImgCode", method = RequestMethod.POST)
    public void checkImgCode(@JSONParam("common")CommonParamReq common, @JSONParam("data")SendSmsReq req, HttpServletRequest request, HttpServletResponse response){
        try {
        	//请求参数校验数据格式
    		BeanValidator.ValidateResult validateResult = BeanValidator.validateBean(req);
    		if(!validateResult.isValid()){
    			returnFail(validateResult.getMessage(),response);
    			return;
    		}
    		boolean falg =false;
			String imgCode = redisTemplateDAO.get(MessageConstants.IMG_CODE + req.getPhone());
			if(imgCode != null && req.getCode() != null){
				if(imgCode.toLowerCase().equals(req.getCode().toLowerCase())){
					int code = (int)((Math.random()*9+1)*1000);
					if(!Boolean.valueOf(SmsUtil.getSmsConfigurationProperties().isSmsSwitch())){
						code=1111;
					}else{
						//发送短信
						SendMqUtil.sendShortMessage(MessageConstants.COMMON_MESSAGE_0.toString(), req.getPhone(), String.valueOf(code), null, null, null);
					}
					redisTemplateDAO.put(MessageConstants.MESSAGE_CODE + req.getPhone(), String.valueOf(code), null, 1800L);
					falg = true;
					returnSuccess("短信发送成功", response);
				}
			}
    		if(!falg){
    			returnFail(ImgCodeUtil.createCode(req.getPhone()),"验证码不正确", response);
    			return;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			returnFail("网络繁忙", response);
		}
    }
}
