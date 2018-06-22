package com.yiyun.web.base.controller;

import com.yiyun.domain.SysHelpDocDO;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.service.SysHelpDocService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 帮助中心
 * 
 * @author yangliu
 * @date Sat Dec 16 09:53:49 CST 2017
 */
 
@Controller
@RequestMapping("/common/sysHelpDoc")
public class SysHelpDocController extends BaseController {
	@Autowired
	private SysHelpDocService sysHelpDocService;
	
	@GetMapping()
	@RequiresPermissions("common:sysHelpDoc:sysHelpDoc")
	String SysHelpDoc(){
	    return "common/sysHelpDoc/sysHelpDoc";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:sysHelpDoc:sysHelpDoc")
	public PageUtil list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<SysHelpDocDO> sysHelpDocList = sysHelpDocService.list(query);
		int total = sysHelpDocService.count(query);
		PageUtil pageUtils = new PageUtil(sysHelpDocList, total);
		return pageUtils;
	}
	
    @GetMapping("/type")
    @ResponseBody
    public List<SysHelpDocDO> listType() {
        return sysHelpDocService.listType();
    }

	@GetMapping("/add")
	@RequiresPermissions("common:sysHelpDoc:add")
	String add(){
	    return "common/sysHelpDoc/add";
	}

    // 类别已经指定增加
    @GetMapping("/add/{type}/{description}")
    @RequiresPermissions("common:sysHelpDoc:add")
    String addD(Model model, @PathVariable("type") String type, @PathVariable("description") String description) {
        model.addAttribute("type", type);
        model.addAttribute("description", description);
        return "common/sysHelpDoc/add";
    }

	@GetMapping("/edit/{id}")
	@RequiresPermissions("common:sysHelpDoc:edit")
	String edit(@PathVariable("id") Long id,Model model){
		SysHelpDocDO sysHelpDoc = sysHelpDocService.get(id);
		model.addAttribute("sysHelpDoc", sysHelpDoc);
	    return "common/sysHelpDoc/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:sysHelpDoc:add")
	public R save(SysHelpDocDO sysHelpDoc){
		sysHelpDoc.setCreator(super.getUsername());
		sysHelpDoc.setCreateTime(System.currentTimeMillis());
		if(sysHelpDocService.save(sysHelpDoc)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:sysHelpDoc:edit")
	public R update( SysHelpDocDO sysHelpDoc){
		sysHelpDoc.setCreator(super.getUsername());
		sysHelpDoc.setCreateTime(System.currentTimeMillis());
		sysHelpDocService.update(sysHelpDoc);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("common:sysHelpDoc:remove")
	public R remove( Long id){
		if(sysHelpDocService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 批量删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:sysHelpDoc:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		sysHelpDocService.batchRemove(ids);
		return R.ok();
	}
	
}
