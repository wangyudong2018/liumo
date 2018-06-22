package com.yiyun.web.base.controller;

import com.alibaba.fastjson.JSON;
import com.yiyun.constants.CommonConstants;
import com.yiyun.domain.SysParam;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.service.SysParamService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xbz
 * @email 1992lcg@163.com
 * @date Thu Dec 14 18:00:10 CST 2017
 */
 
@Controller
@RequestMapping("/common/sysParam")
public class SysParamController extends  BaseController{
	@Autowired
	private SysParamService sysParamService;
	
	@GetMapping()
	@RequiresPermissions("common:sysParam:sysParam")
	String Param(){
	    return "common/sysParam/sysParam";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:sysParam:sysParam")
	public PageUtil list(@RequestParam Map<String, Object> sysParams){
		//查询列表数据
        Query query = new Query(sysParams);
		List<SysParam> sysParamList = sysParamService.list(query);
		int total = sysParamService.count(query);
		PageUtil pageUtil = new PageUtil(sysParamList, total);
		return pageUtil;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("common:sysParam:add")
	String add(){
	    return "common/sysParam/add";
	}


	@GetMapping("/edit/{id}")
	@RequiresPermissions("common:sysParam:edit")
	String edit(@PathVariable("id") Long id,Model model){
		SysParam sysParam = sysParamService.get(id);
		model.addAttribute("sysParam", sysParam);
	    return "common/sysParam/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:sysParam:add")
	public R save(SysParam sysParam){

	    if(sysParam.getStatus() == null){
	        sysParam.setStatus(CommonConstants.NO);
        }
		sysParam.setCreator(getUsername());
		sysParam.setCreateTime(System.currentTimeMillis());

		if(sysParamService.save(sysParam)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:sysParam:edit")
	public R update( SysParam sysParam){
        if(sysParam.getStatus() == null){
            sysParam.setStatus(CommonConstants.NO);
        }
		sysParamService.update(sysParam);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("common:sysParam:remove")
	public R remove( Long id){
		if(sysParamService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:sysParam:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		sysParamService.batchRemove(ids);
		return R.ok();
	}

	@GetMapping("/type")
	@ResponseBody
	public List<SysParam> listType() {
		return sysParamService.listType();
	}

	// 类别已经指定增加
	@GetMapping("/add/{type}/{description}")
	@RequiresPermissions("common:sysParam:add")
	String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {
		model.addAttribute("type", type);
		model.addAttribute("description", description);
		return "common/sysParam/add";
	}

	@ResponseBody
	@GetMapping("/list/{type}")
	public List<SysParam> listByType(@PathVariable("type") String type) {
		// 查询列表数据
		Map<String, Object> map = new HashMap<>(16);
		map.put("type", type);
		List<SysParam> sysParamList = sysParamService.list(map);
		return sysParamList;
	}

	@ResponseBody
	@GetMapping("/code")
	public String get(String code){
		return JSON.toJSONString(sysParamService.getByCode(code));
	}
}
