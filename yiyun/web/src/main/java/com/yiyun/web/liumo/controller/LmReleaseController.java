package com.yiyun.web.liumo.controller;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.yiyun.domain.LmRelease;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmReleaseService;

/**
 * @title 六漠新闻媒体发布
 * @author wangyudong
 * @date Sun Jun 24 10:27:30 CST 2018
 */
@Controller
@RequestMapping("/liumo/lmRelease")
public class LmReleaseController {
	@Autowired
	private LmReleaseService lmReleaseService;

	@GetMapping()
	@RequiresPermissions("liumo:lmRelease:lmRelease")
	public String LmRelease() {
		return "liumo/lmRelease/lmRelease";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("liumo:lmRelease:lmRelease")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		params.put("sort", "");
		params.put("order", "sort asc, create_time desc");
		Query query = new Query(params);
		List<LmRelease> lmReleaseList = lmReleaseService.list(query);
		int total = lmReleaseService.count(query);
		PageUtil pageUtil = new PageUtil(lmReleaseList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmRelease:add")
	public String add() {
		return "liumo/lmRelease/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmRelease:edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		LmRelease lmRelease = lmReleaseService.get(id);
		model.addAttribute("lmRelease", lmRelease);
		return "liumo/lmRelease/edit";
	}

	/**
	 * 保存
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("liumo:lmRelease:add")
	public R save(@RequestParam("file") MultipartFile logo, LmRelease lmRelease) throws IOException {
		lmRelease.setLogo(logo.getBytes());
		lmRelease.setCreateTime(new Date());
		lmRelease.setUpdateTime(new Date());
		if (lmReleaseService.save(lmRelease) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 * 
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("liumo:lmRelease:edit")
	public R edit(@RequestParam("file") MultipartFile logo, LmRelease lmRelease) throws IOException {
		if (null != logo && logo.getBytes().length > 0) {
			lmRelease.setLogo(logo.getBytes());
		} else {
			lmRelease.setLogo(null);
		}
		lmRelease.setUpdateTime(new Date());
		lmReleaseService.edit(lmRelease);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("liumo:lmRelease:remove")
	public R remove(Long id) {
		if (lmReleaseService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("liumo:lmRelease:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		lmReleaseService.batchRemove(ids);
		return R.ok();
	}

}