package com.yiyun.web.liumo.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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

import com.yiyun.domain.LmOrder;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmOrderService;

/**
 * @title 六漠订单表
 * @author wangyudong
 * @date Mon Jul 16 23:06:06 CST 2018
 */
@Controller
@RequestMapping("/liumo/lmOrder")
public class LmOrderController {
	@Autowired
	private LmOrderService lmOrderService;

	@GetMapping()
	@RequiresPermissions("liumo:lmOrder:lmOrder")
	public String LmOrder() {
		return "liumo/lmOrder/lmOrder";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("liumo:lmOrder:lmOrder")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LmOrder> lmOrderList = lmOrderService.list(query);
		int total = lmOrderService.count(query);
		PageUtil pageUtil = new PageUtil(lmOrderList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmOrder:add")
	public String add() {
		return "liumo/lmOrder/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmOrder:edit")
	public String edit(@PathVariable("id") String id, Model model) {
		LmOrder lmOrder = lmOrderService.get(id);
		model.addAttribute("lmOrder", lmOrder);
		return "liumo/lmOrder/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("liumo:lmOrder:add")
	public R save(LmOrder lmOrder) {
		if (lmOrderService.save(lmOrder) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("liumo:lmOrder:edit")
	public R edit(LmOrder lmOrder) {
		if (StringUtils.equals(lmOrder.getState(), "01")) {
			lmOrder.setOrderDate01(new Date());
		} else if (StringUtils.equals(lmOrder.getState(), "02")) {
			lmOrder.setOrderDate02(new Date());
		} else if (StringUtils.equals(lmOrder.getState(), "03")) {
			lmOrder.setOrderDate03(new Date());
		} else if (StringUtils.equals(lmOrder.getState(), "04")) {
			lmOrder.setOrderDate04(new Date());
		} else if (StringUtils.equals(lmOrder.getState(), "05")) {
			lmOrder.setOrderDate05(new Date());
		} else if (StringUtils.equals(lmOrder.getState(), "06")) {
			lmOrder.setOrderDate06(new Date());
		}

		lmOrder.setOprUpdateTime(new Date());
		lmOrder.setLastUpdateTime(new Date());
		lmOrderService.edit(lmOrder);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("liumo:lmOrder:remove")
	public R remove(String id) {
		if (lmOrderService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("liumo:lmOrder:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids) {
		lmOrderService.batchRemove(ids);
		return R.ok();
	}

}
