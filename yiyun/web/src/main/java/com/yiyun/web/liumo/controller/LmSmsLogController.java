package com.yiyun.web.liumo.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yiyun.domain.LmSmsLog;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmSmsLogService;

/**
 * @title 六漠短信流水表
 * @author wangyudong
 * @date Sat Jul 14 12:58:33 CST 2018
 */
@Controller
@RequestMapping("/liumo/lmSmsLog")
public class LmSmsLogController {

	@Autowired
	private LmSmsLogService lmSmsLogService;

	@GetMapping()
	@RequiresPermissions("liumo:lmSmsLog:lmSmsLog")
	public String LmSmsLog() {
		return "liumo/lmSmsLog/lmSmsLog";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("liumo:lmSmsLog:lmSmsLog")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LmSmsLog> lmSmsLogList = lmSmsLogService.list(query);
		int total = lmSmsLogService.count(query);
		PageUtil pageUtil = new PageUtil(lmSmsLogList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmSmsLog:add")
	public String add() {
		return "liumo/lmSmsLog/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmSmsLog:edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		LmSmsLog lmSmsLog = lmSmsLogService.get(id);
		model.addAttribute("lmSmsLog", lmSmsLog);
		return "liumo/lmSmsLog/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("liumo:lmSmsLog:add")
	public R save(LmSmsLog lmSmsLog) {
		if (lmSmsLogService.save(lmSmsLog) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("liumo:lmSmsLog:edit")
	public R edit(LmSmsLog lmSmsLog) {
		lmSmsLogService.edit(lmSmsLog);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("liumo:lmSmsLog:remove")
	public R remove(Long id) {
		if (lmSmsLogService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("liumo:lmSmsLog:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		lmSmsLogService.batchRemove(ids);
		return R.ok();
	}

}
