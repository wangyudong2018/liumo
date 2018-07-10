package com.yiyun.web.liumo.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yiyun.domain.LmUserHis;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmUserHisService;

/**
 * @title 六漠用户信息历史表
 * @author WangYuDong
 * @date Tue Jul 10 20:45:56 CST 2018
 */
@RestController
@RequestMapping("/liumo/lmUserHis")
public class LmUserHisController {

	@Autowired
	private LmUserHisService lmUserHisService;

	@GetMapping()
	@RequiresPermissions("liumo:lmUserHis:lmUserHis")
	public String LmUserHis() {
		return "liumo/lmUserHis/lmUserHis";
	}

	@GetMapping("/list")
	@RequiresPermissions("liumo:lmUserHis:lmUserHis")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LmUserHis> lmUserHisList = lmUserHisService.list(query);
		int total = lmUserHisService.count(query);
		PageUtil pageUtil = new PageUtil(lmUserHisList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmUserHis:add")
	public String add() {
		return "liumo/lmUserHis/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmUserHis:edit")
	public String edit(@PathVariable("id") String id, Model model) {
		LmUserHis lmUserHis = lmUserHisService.get(id);
		model.addAttribute("lmUserHis", lmUserHis);
		return "liumo/lmUserHis/edit";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	@RequiresPermissions("liumo:lmUserHis:add")
	public R save(LmUserHis lmUserHis) {
		if (lmUserHisService.save(lmUserHis) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@PostMapping("/edit")
	@RequiresPermissions("liumo:lmUserHis:edit")
	public R edit(LmUserHis lmUserHis) {
		lmUserHisService.edit(lmUserHis);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@RequiresPermissions("liumo:lmUserHis:remove")
	public R remove(String id) {
		if (lmUserHisService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@RequiresPermissions("liumo:lmUserHis:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids) {
		lmUserHisService.batchRemove(ids);
		return R.ok();
	}

}
