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

import com.yiyun.domain.LmBanner;
import com.yiyun.domain.LmFile;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmBannerService;
import com.yiyun.web.liumo.service.LmFileService;

/**
 * @title 六漠banner表
 * @author WangYuDong
 * @date Thu Jul 05 22:19:09 CST 2018
 */
@Controller
@RequestMapping("/liumo/lmBanner")
public class LmBannerController {

	@Autowired
	private LmBannerService lmBannerService;
	@Autowired
	private LmFileService lmFileService;

	@GetMapping()
	@RequiresPermissions("liumo:lmBanner:lmBanner")
	public String LmBanner() {
		return "liumo/lmBanner/lmBanner";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("liumo:lmBanner:lmBanner")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LmBanner> lmBannerList = lmBannerService.list(query);
		int total = lmBannerService.count(query);
		PageUtil pageUtil = new PageUtil(lmBannerList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmBanner:add")
	public String add() {
		return "liumo/lmBanner/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmBanner:edit")
	public String edit(@PathVariable("id") Long id, Model model) {
		LmBanner lmBanner = lmBannerService.get(id);
		model.addAttribute("lmBanner", lmBanner);
		return "liumo/lmBanner/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("liumo:lmBanner:add")
	public R save(LmBanner lmBanner, @RequestParam("file") MultipartFile file) throws IOException {
		LmFile lmFile = new LmFile();
		lmFile.setLmFile(file.getBytes());
		lmFile.setLmType(file.getContentType());
		lmFile.setCreateTime(new Date());
		lmFile.setUpdateTime(new Date());
		lmFileService.save(lmFile);

		lmBanner.setFileId(lmFile.getId());
		lmBanner.setCreateTime(new Date());
		lmBanner.setUpdateTime(new Date());
		if (lmBannerService.save(lmBanner) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("liumo:lmBanner:edit")
	public R edit(LmBanner lmBanner, @RequestParam("file") MultipartFile file) throws IOException {

		if (!file.isEmpty()) {
			LmFile lmFile = new LmFile();
			lmFile.setId(lmBannerService.get(lmBanner.getId()).getFileId());
			lmFile.setLmFile(file.getBytes());
			lmFile.setLmType(file.getContentType());
			lmFile.setUpdateTime(new Date());
			lmFileService.edit(lmFile);
		}

		lmBanner.setUpdateTime(new Date());
		lmBannerService.edit(lmBanner);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("liumo:lmBanner:remove")
	public R remove(Long id) {
		lmFileService.remove(lmBannerService.get(id).getFileId());
		if (lmBannerService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("liumo:lmBanner:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids) {
		lmBannerService.batchRemoveFile(ids);
		lmBannerService.batchRemove(ids);
		return R.ok();
	}

}
