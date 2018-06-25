package com.yiyun.web.liumo.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/liumo/public")
public class LmPublicController {

	@Autowired
	private LmProductService lmProductService;
	@Autowired
	private LmReleaseService lmReleaseService;

	@GetMapping("/productList")
	public List<LmProduct> productList() {
		// 查询列表数据
		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("state", "1");
		return lmProductService.list(map);
	}

	@GetMapping("/releaseList")
	public PageUtil newsList(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		params.put("sort", "sort asc, create_time desc");
		Query query = new Query(params);
		List<LmRelease> lmReleaseList = lmReleaseService.newList(query);
		int total = lmReleaseService.count(query);
		PageUtil pageUtil = new PageUtil(lmReleaseList, total);
		return pageUtil;
	}

	@GetMapping("/release/{id}")
	public void newsListId(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "max-age=604800"); // 设置缓存
		LmRelease lmRelease = lmReleaseService.get(id);
		response.getOutputStream().write(lmRelease.getLogo());
	}

}
