package com.yiyun.web.liumo.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yiyun.domain.LmUserHis;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.liumo.service.LmUserHisService;

/**
 * @title 六漠用户信息历史表
 * @author WangYuDong
 * @date Tue Jul 10 20:45:56 CST 2018
 */
@Controller
@RequestMapping("/liumo/lmUserHis")
public class LmUserHisController {

	@Autowired
	private LmUserHisService lmUserHisService;

	@GetMapping()
	@RequiresPermissions("liumo:lmUserHis:lmUserHis")
	public String LmUserHis() {
		return "liumo/lmUserHis/lmUserHis";
	}

	@ResponseBody
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

}
