package com.yiyun.web.liumo.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yiyun.domain.LmApp;
import com.yiyun.domain.LmBanner;
import com.yiyun.domain.LmFile;
import com.yiyun.domain.LmProduct;
import com.yiyun.domain.LmRecruit;
import com.yiyun.domain.LmRelease;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmAppService;
import com.yiyun.web.liumo.service.LmBannerService;
import com.yiyun.web.liumo.service.LmFileService;
import com.yiyun.web.liumo.service.LmProductService;
import com.yiyun.web.liumo.service.LmRecruitService;
import com.yiyun.web.liumo.service.LmReleaseService;

import lombok.extern.slf4j.Slf4j;

/**
 * @title 六漠公共
 * @author wangyudong
 * @date Fri Jun 22 23:28:52 CST 2018
 */
@Slf4j
@RestController
@RequestMapping("/liumo/public")
public class LmPublicController {

	@Autowired
	private LmBannerService lmBannerService;
	@Autowired
	private LmProductService lmProductService;
	@Autowired
	private LmReleaseService lmReleaseService;
	@Autowired
	private LmFileService lmFileService;
	@Autowired
	private LmRecruitService lmRecruitService;
	@Autowired
	private LmAppService lmAppService;

	/**
	 * banner列表
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("/bannerList")
	public List<Map<String, Object>> bannerList(@RequestParam Map<String, Object> params) {
		List<LmBanner> records = lmBannerService.list(params);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(16);
		if (!CollectionUtils.isEmpty(records)) {
			Map<String, Object> map = null;
			for (LmBanner record : records) {
				map = new HashMap<String, Object>(16);
				map.put("fileId", record.getFileId());
				list.add(map);
			}
		}

		return list;
	}

	/**
	 * 读取文件流
	 * 
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/file/{id}")
	public void banner(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "max-age=604800"); // 设置缓存
		LmFile lmFile = lmFileService.get(id);
		response.getOutputStream().write(lmFile.getLmFile());
	}

	/**
	 * 六漠产品列表
	 * 
	 * @return
	 */
	@GetMapping("/productList")
	public List<Map<String, Object>> productList() {
		Map<String, Object> paramMap = new HashMap<String, Object>(8);
		paramMap.put("state", "1");

		List<LmProduct> records = lmProductService.list(paramMap);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(16);
		if (!CollectionUtils.isEmpty(records)) {
			Map<String, Object> map = null;
			for (LmProduct record : records) {
				map = new HashMap<String, Object>(16);
				map.put("prdType", record.getPrdType());
				map.put("title", record.getTitle());
				map.put("content", record.getContent());
				map.put("agreemt", record.getAgreemt());
				map.put("people", record.getPeople());
				list.add(map);
			}
		}

		return list;
	}

	/**
	 * 六漠资讯列表
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("/releaseList")
	public PageUtil releaseList(@RequestParam Map<String, Object> params) {
		if (Integer.parseInt(params.get("limit").toString()) > 50) {
			params.put("limit", 50);
		}
		if (StringUtils.equals("pc", (String) params.get("channel"))) {
			params.put("terminal", "'00','01'");
		} else if (StringUtils.equals("mb", (String) params.get("channel"))) {
			params.put("terminal", "'00','02'");
		} else {
			params.put("terminal", "'00'");
		}
		params.put("state", "1");
		Query query = new Query(params);
		List<LmRelease> records = lmReleaseService.list(query);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(16);
		if (!CollectionUtils.isEmpty(records)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> map = null;
			for (LmRelease record : records) {
				map = new HashMap<String, Object>(16);
				map.put("id", record.getId());
				map.put("category", record.getCategory());
				map.put("thumbnail", record.getThumbnail());
				map.put("title", record.getTitle());
				map.put("brief", record.getBrief());
				map.put("original", record.getOriginal());
				map.put("title", record.getTitle());
				map.put("stick", record.getStick());
				map.put("outChain", record.getOutChain());
				map.put("releaseDate", sdf.format(record.getReleaseDate()));
				map.put("releaseTime", record.getCreateTime());
				list.add(map);
			}
		}

		int total = lmReleaseService.count(query);
		PageUtil pageUtil = new PageUtil(list, total);
		return pageUtil;
	}

	/**
	 * 六漠资讯详情
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/release/{id}")
	public Map<String, Object> release(@PathVariable("id") Long id) {
		Map<String, Object> map = new HashMap<String, Object>(16);
		LmRelease record = lmReleaseService.get(id);
		if (null == record) {
			return map;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		map.put("id", record.getId());
		map.put("category", record.getCategory());
		map.put("thumbnail", record.getThumbnail());
		map.put("title", record.getTitle());
		map.put("brief", record.getBrief());
		map.put("original", record.getOriginal());
		map.put("title", record.getTitle());
		map.put("stick", record.getStick());
		map.put("outChain", record.getOutChain());
		map.put("releaseDate", sdf.format(record.getReleaseDate()));
		map.put("releaseTime", record.getCreateTime());
		map.put("content", record.getContent());

		return map;
	}

	/**
	 * 招聘列表
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("/recruitList")
	public PageUtil recruitList(@RequestParam Map<String, Object> params) {
		if (Integer.parseInt(params.get("limit").toString()) > 50) {
			params.put("limit", 50);
		}
		params.put("state", "1");
		Query query = new Query(params);
		List<LmRecruit> records = lmRecruitService.list(query);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(16);
		if (!CollectionUtils.isEmpty(records)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> map = null;
			for (LmRecruit record : records) {
				map = new HashMap<String, Object>(16);
				map.put("workplace", record.getWorkplace());
				map.put("recType", record.getRecType());
				map.put("category", record.getCategory());
				map.put("jobTitle", record.getJobTitle());
				map.put("jobNature", record.getJobNature());
				map.put("salaryRange", record.getSalaryRange());
				map.put("hiring", record.getHiring());
				map.put("jobDuty", record.getJobDuty());
				map.put("jobQuality", record.getJobQuality());
				map.put("relDate", sdf.format(record.getRelDate()));
				list.add(map);
			}
		}

		int total = lmRecruitService.count(query);
		PageUtil pageUtil = new PageUtil(list, total);
		return pageUtil;
	}

	@GetMapping("/version")
	public R version(@RequestParam Map<String, Object> params) {
		try {
			List<LmApp> list = lmAppService.list(params);
			if (CollectionUtils.isEmpty(list)) {
				return R.error();
			}

			LmApp record = list.get(0);
			Map<String, Object> map = new HashMap<String, Object>(8);
			map.put("version", record.getVersion());
			map.put("fileUrl", record.getFileUrl());
			map.put("log", record.getLog());
			map.put("size", (record.getSize() / 1024 / 1024) + "M");
			map.put("md5", record.getMd5());
			map.put("constraint", StringUtils.equals("1", record.getConstraint()) ? Boolean.TRUE : Boolean.FALSE);
			map.put("updateTime", DateFormatUtils.format(record.getUpdateTime(), "yyyy-MM-dd"));

			return R.ok(map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error();
		}
	}

}
