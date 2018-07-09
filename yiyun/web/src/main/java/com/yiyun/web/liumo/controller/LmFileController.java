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

import com.yiyun.domain.LmFile;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmFileService;
import com.yiyun.web.liumo.util.UUIDGenerator;

/**
 * @title 六漠文件表
 * @author WangYuDong
 * @date Mon Jul 09 19:45:18 CST 2018
 */
@Controller
@RequestMapping("/liumo/lmFile")
public class LmFileController {
	@Autowired
	private LmFileService lmFileService;

	@GetMapping()
	@RequiresPermissions("liumo:lmFile:lmFile")
	public String LmFile() {
		return "liumo/lmFile/lmFile";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("liumo:lmFile:lmFile")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LmFile> lmFileList = lmFileService.list(query);
		int total = lmFileService.count(query);
		PageUtil pageUtil = new PageUtil(lmFileList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmFile:add")
	public String add() {
		return "liumo/lmFile/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmFile:edit")
	public String edit(@PathVariable("id") String id, Model model) {
		LmFile lmFile = lmFileService.get(id);
		model.addAttribute("lmFile", lmFile);
		return "liumo/lmFile/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	// @RequiresPermissions("liumo:lmFile:add")
	public String save(@RequestParam("file") MultipartFile file) throws IOException {
		LmFile lmFile = new LmFile();
		lmFile.setId(UUIDGenerator.generate());
		lmFile.setLmFile(file.getBytes());
		lmFile.setLmType(file.getContentType());
		lmFile.setCreateTime(new Date());
		lmFile.setUpdateTime(new Date());
		lmFileService.save(lmFile);
		if (lmFileService.save(lmFile) > 0) {
			return "{\"success\": true,\"msg\": \"上传成功\", \"file_path\": \"/liumo/public/file/" + lmFile.getId() + "\"}";
		}
		return "{\"success\": false,\"msg\": \"上传失败\"}";
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("liumo:lmFile:edit")
	public R edit(LmFile lmFile) {
		lmFileService.edit(lmFile);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("liumo:lmFile:remove")
	public R remove(String id) {
		if (lmFileService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("liumo:lmFile:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids) {
		lmFileService.batchRemove(ids);
		return R.ok();
	}

}
