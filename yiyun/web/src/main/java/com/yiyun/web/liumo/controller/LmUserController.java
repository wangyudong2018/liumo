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

import com.yiyun.domain.LmUser;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmUserService;

/**
 * @title 六漠用户信息表
 * @author WangYuDong
 * @date Tue Jul 10 20:45:56 CST 2018
 */
@RestController
@RequestMapping("/liumo/lmUser")
public class LmUserController {

	@Autowired
	private LmUserService lmUserService;

	@GetMapping()
	@RequiresPermissions("liumo:lmUser:lmUser")
	public String LmUser() {
		return "liumo/lmUser/lmUser";
	}

	@GetMapping("/list")
	@RequiresPermissions("liumo:lmUser:lmUser")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LmUser> lmUserList = lmUserService.list(query);
		int total = lmUserService.count(query);
		PageUtil pageUtil = new PageUtil(lmUserList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmUser:add")
	public String add() {
		return "liumo/lmUser/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmUser:edit")
	public String edit(@PathVariable("id") String id, Model model) {
		LmUser lmUser = lmUserService.get(id);
		model.addAttribute("lmUser", lmUser);
		return "liumo/lmUser/edit";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	@RequiresPermissions("liumo:lmUser:add")
	public R save(LmUser lmUser) {
		if (lmUserService.save(lmUser) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@PostMapping("/edit")
	@RequiresPermissions("liumo:lmUser:edit")
	public R edit(LmUser lmUser) {
		lmUserService.edit(lmUser);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@RequiresPermissions("liumo:lmUser:remove")
	public R remove(String id) {
		if (lmUserService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@RequiresPermissions("liumo:lmUser:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids) {
		lmUserService.batchRemove(ids);
		return R.ok();
	}

}
