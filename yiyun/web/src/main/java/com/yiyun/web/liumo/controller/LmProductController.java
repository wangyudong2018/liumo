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

import com.yiyun.domain.LmProduct;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmProductService;

/**
 * @title 六漠产品介绍
 * @author Xing
 * @date Fri Jun 22 23:28:52 CST 2018
 */
@Controller
@RequestMapping("/liumo/lmProduct")
public class LmProductController {
	@Autowired
	private LmProductService lmProductService;

	@GetMapping()
	@RequiresPermissions("liumo:lmProduct:lmProduct")
	public String LmProduct() {
		return "liumo/lmProduct/lmProduct";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("liumo:lmProduct:lmProduct")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LmProduct> lmProductList = lmProductService.list(query);
		int total = lmProductService.count(query);
		PageUtil pageUtil = new PageUtil(lmProductList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmProduct:add")
	public String add() {
		return "liumo/lmProduct/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmProduct:edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		LmProduct lmProduct = lmProductService.get(id);
		model.addAttribute("lmProduct", lmProduct);
		return "liumo/lmProduct/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("liumo:lmProduct:add")
	public R save(LmProduct lmProduct) {
		lmProduct.setCreateTime(new Date());
		lmProduct.setUpdateTime(new Date());
		if (lmProductService.save(lmProduct) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("liumo:lmProduct:edit")
	public R edit(LmProduct lmProduct) {
		lmProduct.setUpdateTime(new Date());
		lmProductService.edit(lmProduct);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("liumo:lmProduct:remove")
	public R remove(Long id) {
		if (lmProductService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("liumo:lmProduct:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		lmProductService.batchRemove(ids);
		return R.ok();
	}

}
