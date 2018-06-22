package com.yiyun.app.system.controller;

import com.alibaba.fastjson.JSONArray;
import com.yiyun.app.common.annotation.JSONParam;
import com.yiyun.app.common.controller.BaseController;
import com.yiyun.app.common.req.CommonParamReq;
import com.yiyun.app.system.service.SysHelpDocService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


/**
 * @title app帮助中心控制器
 * @author Xingbz
 * @createDate 2017年7月28日
 * @version 1.0
 *
 */
@Api(value = "AppSysHelpController", description = "帮助中心")
@RestController
@RequestMapping("/appHelp")
public class AppSysHelpController extends BaseController {

	private static final Logger logger = LogManager.getLogger(AppSysHelpController.class.getName());


	@Autowired
	private SysHelpDocService sysHelpDocService;
	
	/**
	 * @title 获取帮助内容
	 * @author Xingbz
	 * @createDate 2017年7月28日
	 * @param common
	 * @param response
	 * @version 1.0
	 */
    @ApiOperation(value="获取帮助内容",tags={""},notes="获取帮助内容")
	@ApiResponses({@ApiResponse(code = 200, message = "helpDocType：帮助类别，icon：图标url路径，answer：问题，question：回答")})
	@RequestMapping(value="/getHelpDoc" , method=RequestMethod.POST)
	public void getHelpInfo(@JSONParam("common")CommonParamReq common, HttpServletResponse response){
		try {
            JSONArray helpDocData = sysHelpDocService.getAllHelpDocByType();
            logger.info("帮助文档获取成功，返回参数："+helpDocData.toJSONString());
			returnSuccess(helpDocData, "帮助文档获取成功",response);
		} catch (Exception e) {
			logger.error("帮助文档获取失败！", e);
			returnFail("帮助文档获取失败", response);
			e.printStackTrace();
		}
		
	}
	
}
