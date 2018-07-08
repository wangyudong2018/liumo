package com.yiyun.web.liumo.controller;

import java.util.Date;
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

import com.yiyun.domain.LmRecruit;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmRecruitService;

/**
 * @title 六漠招聘
 * @author WangYuDong
 * @date Sun Jul 08 10:02:59 CST 2018
 */
@Controller
@RequestMapping("/liumo/lmRecruit")
public class LmRecruitController {

	@Autowired
	private LmRecruitService lmRecruitService;

	@GetMapping()
	@RequiresPermissions("liumo:lmRecruit:lmRecruit")
	public String LmRecruit() {
		return "liumo/lmRecruit/lmRecruit";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("liumo:lmRecruit:lmRecruit")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LmRecruit> lmRecruitList = lmRecruitService.list(query);
		int total = lmRecruitService.count(query);
		PageUtil pageUtil = new PageUtil(lmRecruitList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmRecruit:add")
	public String add() {
		return "liumo/lmRecruit/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmRecruit:edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		LmRecruit lmRecruit = lmRecruitService.get(id);
		model.addAttribute("lmRecruit", lmRecruit);
		return "liumo/lmRecruit/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("liumo:lmRecruit:add")
	public R save(LmRecruit lmRecruit) {
		lmRecruit.setRelDate(new Date());
		lmRecruit.setCreateTime(new Date());
		lmRecruit.setUpdateTime(new Date());
		if (lmRecruitService.save(lmRecruit) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("liumo:lmRecruit:edit")
	public R edit(LmRecruit lmRecruit) {
		lmRecruit.setUpdateTime(new Date());
		lmRecruitService.edit(lmRecruit);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("liumo:lmRecruit:remove")
	public R remove(Long id) {
		if (lmRecruitService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("liumo:lmRecruit:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		lmRecruitService.batchRemove(ids);
		return R.ok();
	}

}
