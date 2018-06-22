package com.yiyun.app.system.controller;

import com.yiyun.app.common.annotation.JSONParam;
import com.yiyun.app.common.controller.BaseController;
import com.yiyun.app.common.req.CommonParamReq;
import com.yiyun.app.system.req.SysDictReq;
import com.yiyun.app.system.resp.SysDictResp;
import com.yiyun.app.system.service.IAppSysDictService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @title app数据字典信息
 * @author Xingbz
 * @createDate 2017年12月18日
 * @version 1.0
 *
 */
@Api(value = "AppSysDictController", description = "数据字典信息获取 -by:Xbz")
@RestController
@RequestMapping("/appDict")
public class AppSysDictController extends BaseController {

    @Autowired
    private IAppSysDictService sysDictService;

	/**
	 * @title 获取所有字典数据
	 * @author Xingbz
	 * @createDate 2017年12月18日
	 * @param common
	 * @param response
	 * @version 1.0
	 */
	@ApiOperation(value = "获取最新的字典数据" , notes = "如果存在新的字典数据 , 则返回新的数据字典内容 , 否则返回空的字典信息(app端仍用原来的),若指定强制获取 , 则是否有新的数据字典都将返回" , produces = "application/json")
    @ApiImplicitParams({@ApiImplicitParam(name = "common" , value="系统通用参数 " , paramType = "query"),
                                        @ApiImplicitParam(name = "data" , value="数据字典参数 . dictVersion:app端字典版本号 ; force:是否强制更新" , paramType = "query" , defaultValue = "{\"dictVersion\":\"101\" , \"force\":\"1\"}")})
    @ApiResponses({
            @ApiResponse(code=200,message="isUpdate : 是否需要更新 , 为1说明获取到了新的内容 , 需要更新本地数据 ; 为0则无需同步;dictVersion:最新的版本号;dicts:最新的字典数据 , 若isUpdate为0则不返回"),
    })
    @PostMapping(value = "/getAllDict")
	public void getAllDict(@JSONParam("common")CommonParamReq common, @JSONParam("data")SysDictReq data, HttpServletResponse response){
		try {
            SysDictResp result = sysDictService.getAllDictsByVersion(data);
			returnSuccess(result, "字典数据获取成功",response);
		} catch (Exception e) {
			returnFail("字典数据获取失败", response);
			e.printStackTrace();
		}
	}

}
