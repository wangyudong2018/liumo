package com.yiyun.web.liumo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
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

import com.yiyun.domain.LmApp;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.config.SystemConfig;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmAppService;
import com.yiyun.web.liumo.util.UUIDGenerator;

/**
 * @title 六漠APP表
 * @author wangyudong
 * @date Fri Jul 20 21:51:20 CST 2018
 */
@Controller
@RequestMapping("/liumo/lmApp")
public class LmAppController {

	@Autowired
	private LmAppService lmAppService;
	@Autowired
	private SystemConfig systemConfig;

	private static final String FILE_URL_PRE = "http://rmwangyudong:8000/upload/";

	@GetMapping()
	@RequiresPermissions("liumo:lmApp:lmApp")
	public String LmApp() {
		return "liumo/lmApp/lmApp";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("liumo:lmApp:lmApp")
	public PageUtil list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<LmApp> lmAppList = lmAppService.list(query);
		int total = lmAppService.count(query);
		PageUtil pageUtil = new PageUtil(lmAppList, total);
		return pageUtil;
	}

	@GetMapping("/add")
	@RequiresPermissions("liumo:lmApp:add")
	public String add() {
		return "liumo/lmApp/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("liumo:lmApp:edit")
	public String edit(@PathVariable("id") String id, Model model) {
		LmApp lmApp = lmAppService.get(id);
		model.addAttribute("lmApp", lmApp);
		return "liumo/lmApp/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("liumo:lmApp:add")
	public R save(@RequestParam("file") MultipartFile file, LmApp lmApp) throws IOException {
		writeFile(file, lmApp);

		lmApp.setId(UUIDGenerator.generate());
		lmApp.setUpdateTime(new Date());
		if (lmAppService.save(lmApp) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("liumo:lmApp:edit")
	public R edit(@RequestParam("file") MultipartFile file, LmApp lmApp) throws IOException {
		writeFile(file, lmApp);

		lmApp.setUpdateTime(new Date());
		lmAppService.edit(lmApp);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("liumo:lmApp:remove")
	public R remove(String id) {
		if (lmAppService.remove(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("liumo:lmApp:batchRemove")
	public R remove(@RequestParam("ids[]") String[] ids) {
		lmAppService.batchRemove(ids);
		return R.ok();
	}

	/**
	 * 写文件
	 * 
	 * @param file
	 * @param lmApp
	 * @throws IOException
	 */
	private void writeFile(MultipartFile file, LmApp lmApp) throws IOException {
		if (null != file && !file.isEmpty()) {
			byte[] data = file.getBytes();

			FileUtils.writeByteArrayToFile(new File(systemConfig.getUploadPath() + file.getOriginalFilename()), data, false);

			lmApp.setSize(file.getSize());
			lmApp.setMd5(DigestUtils.md5Hex(data));
			lmApp.setFileUrl(FILE_URL_PRE + file.getOriginalFilename());
		}
	}

}
