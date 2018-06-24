package com.yiyun.web.liumo.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yiyun.domain.LmProduct;
import com.yiyun.domain.LmRelease;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.liumo.service.LmProductService;
import com.yiyun.web.liumo.service.LmReleaseService;

/**
 * @title 六漠公共
 * @author wangyudong
 * @date Fri Jun 22 23:28:52 CST 2018
 */
@Controller
@RequestMapping("/liumo/public")
public class LmPublicController {

	@Autowired
	private LmProductService lmProductService;
	@Autowired
	private LmReleaseService lmReleaseService;

	@ResponseBody
	@GetMapping("/productList")
	public List<LmProduct> productList() {
		// 查询列表数据
		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("state", "1");
		return lmProductService.list(map);
	}

	@ResponseBody
	@GetMapping("/releaseList")
	public PageUtil newsList(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		params.put("sort", "sort asc, create_time desc");
		Query query = new Query(params);
		List<LmRelease> lmReleaseList = lmReleaseService.list(query);
		int total = lmReleaseService.count(query);
		PageUtil pageUtil = new PageUtil(lmReleaseList, total);
		return pageUtil;
	}

}
