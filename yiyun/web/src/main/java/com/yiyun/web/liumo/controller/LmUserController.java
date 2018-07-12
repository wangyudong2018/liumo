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
@Controller
@RequestMapping("/liumo/lmUser")
public class LmUserController {

	@Autowired
	private LmUserService lmUserService;

	@GetMapping()
	@RequiresPermissions("liumo:lmUser:lmUser")
	public String LmUser() {
		return "liumo/lmUser/lmUser";
	}

	@ResponseBody
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

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmUser:edit")
	public String edit(@PathVariable("id") String id, Model model) {
		LmUser lmUser = lmUserService.get(id);
		model.addAttribute("lmUser", lmUser);
		return "liumo/lmUser/edit";
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@PostMapping("/edit")
	@RequiresPermissions("liumo:lmUser:edit")
	public R edit(LmUser lmUser) {
		lmUserService.edit(lmUser, "01");
		return R.ok();
	}

	@GetMapping("/img")
	public String img() {
		return "liumo/img";
	}

}
