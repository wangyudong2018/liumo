package com.yiyun.web.liumo.common;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yiyun.domain.LmFile;
import com.yiyun.domain.LmUser;
import com.yiyun.resp.SendMessageResp;
import com.yiyun.web.common.info.SmsManager;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmFileService;
import com.yiyun.web.liumo.service.LmSmsLogService;
import com.yiyun.web.liumo.service.LmUserService;
import com.yiyun.web.liumo.util.SecretUtils;
import com.yiyun.web.liumo.util.SessionUtil;
import com.yiyun.web.liumo.util.UUIDGenerator;

/**
 * @title 六漠用户相关
 * @author wangyudong
 * @date 2018年7月13日下午9:17:49
 */
@RestController
@RequestMapping("/liumo/app/user")
public class LmAppUserController {

	@Autowired
	private LmUserService lmUserService;
	@Autowired
	private LmFileService lmFileService;
	@Autowired
	private LmSmsLogService lmSmsLogService;

	/**
	 * 获取短信验证码
	 * 
	 * @param params
	 * @return
	 */
	@PostMapping("/sendCode")
	public R sendCode(@RequestParam Map<String, String> params) {
		String smsType = params.get("smsType");
		String mobile = params.get("mobile");
		String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000)); // 随机6位验证码
		String content = "";

		if (StringUtils.isBlank(mobile) || StringUtils.length(mobile) != 11) {
			return R.error("手机号录入错误");
		}

		// 按手机号检查短信是否超过限制
		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("mobile", mobile);
		map.put("smsType", smsType);
		map.put("sendDate", new java.sql.Date(System.currentTimeMillis()));
		int count = lmSmsLogService.count(map);
		if (count > 10) {
			return R.error("该手机号当日发送短信次数过多，不允许发送短信");
		}

		if (StringUtils.equals("01", smsType)) { // 登录或注册短信验证码
			content = "您好，您的登录验证码是" + code;
		} else if (StringUtils.equals("02", smsType)) { // 忘记密码验证码
			content = "您好，您的忘记密码验证码是" + code;
		} else if (StringUtils.equals("03", smsType)) { // 修改手机号验证码
			content = "您好，您的修改手机号验证码是" + code;
		} else if (StringUtils.equals("04", smsType)) { // 设置新手机号验证码
			content = "您好，您的设置新手机号验证码是" + code;
		} else {
			return R.error("短信类型录入错误");
		}

		SendMessageResp resp = SmsManager.sendMessage(mobile, content);
		if (!resp.isFlag()) {
			return R.error("短信发送失败，请稍候再试");
		}

		// 记录短信历史
		lmSmsLogService.save(mobile, smsType, content);
		SessionUtil.saveCode(mobile, smsType, code);

		return R.ok();
	}

	/**
	 * 用户登录（如未注册，则注册后自动登录）
	 * 
	 * @param params
	 * @return
	 */
	@PostMapping("/login")
	public R login(@RequestParam Map<String, String> params) {

		String mobile = params.get("mobile");
		String code = params.get("code");
		String password = params.get("password");

		if (StringUtils.isBlank(mobile) || StringUtils.length(mobile) != 11) {
			return R.error("手机号录入错误");
		}

		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("mobile", mobile);
		List<LmUser> users = lmUserService.list(map);
		LmUser lmUser = null;

		// 验证短信验证码并登录
		if (StringUtils.isNotBlank(code)) {
			// 验证短信验证码
			if (!SessionUtil.verifyCode(mobile, "01", code)) {
				return R.error("短信验证码输入错误，请重新获取");
			}

			// 判断用户是否存在（不存在则注册）
			if (CollectionUtils.isEmpty(users)) {
				lmUser = new LmUser();
				lmUser.setId(UUIDGenerator.generate());
				lmUser.setMobile(mobile);
				lmUser.setCertSign(null);
				lmUser.setCreateTime(new Date());
				lmUser.setUsrUpdateTime(new Date());
				lmUser.setLastUpdateTime(new Date());
				lmUserService.save(lmUser);
			} else {
				lmUser = users.get(0);
			}

			// 登录
			SessionUtil.saveLmUser(lmUser);
		}

		// 验证密码并登录
		if (StringUtils.isNotBlank(password)) {

			if (CollectionUtils.isEmpty(users)) {
				return R.error("请先注册后再登录");
			}

			lmUser = users.get(0);
			if (!StringUtils.equals(lmUser.getPassword(), password)) {
				return R.error("手机号或密码输入错误");
			}

			// 登录
			SessionUtil.saveLmUser(lmUser);

		}

		return R.ok().put("token", SecretUtils.getToken());
	}

	/**
	 * 刷新Token
	 * 
	 * @param params
	 * @return
	 */
	@PostMapping("/refreshToken")
	public R refreshToken(@RequestParam Map<String, String> params) {
		String token = params.get("token");
		String userId = SecretUtils.getTokenUserId(token);
		if (StringUtils.isBlank(userId)) {
			return R.error();
		}

		LmUser lmUser = lmUserService.get(userId);
		if (null == lmUser) {
			return R.error();
		}

		SessionUtil.saveLmUser(lmUser);

		return R.ok().put("token", SecretUtils.getToken());
	}

	/**
	 * 验证短信验证码
	 * 
	 * @return
	 */
	@PostMapping("/verifyCode")
	public R verifyCode(@RequestParam Map<String, String> params) {
		String mobile = params.get("mobile");
		String smsType = params.get("smsType");
		String code = params.get("code");

		if (!SessionUtil.verifyCode(mobile, smsType, code)) {
			return R.error("动态码验证错误，请重新获取");
		}

		if (StringUtils.equals(smsType, "02")) { // 忘记密码验证码
			Map<String, Object> map = new HashMap<String, Object>(3);
			map.put("mobile", mobile);
			List<LmUser> users = lmUserService.list(map);
			if (CollectionUtils.isEmpty(users)) {
				return R.error("系统中无此用户，请先注册");
			}

			LmUser lmUser = users.get(0);
			SessionUtil.saveLmUser(lmUser);
			SessionUtil.save(SessionUtil.LM_UPDATE_PWD + "_" + lmUser.getMobile(), System.currentTimeMillis());
		} else if (StringUtils.equals(smsType, "03")) { // 修改手机号验证码
			LmUser lmUser = SessionUtil.getLmUser();
			if (null == lmUser) {
				return R.error("用户未登录，请返回后重试");
			}
			SessionUtil.save(SessionUtil.LM_UPDATE_MB + "_" + lmUser.getMobile(), System.currentTimeMillis());
		}

		return R.ok();
	}

	/**
	 * 修改手机号(执行修改)
	 * 
	 * @return
	 */
	@PostMapping("/updateMobile")
	public R updateMobile(@RequestParam Map<String, String> params) {
		String newMobile = params.get("newMobile");
		String code = params.get("code");

		LmUser lmUser = SessionUtil.getLmUser();
		if (null == lmUser) {
			return R.error("用户未登录，请返回后重试");
		}

		if (!SessionUtil.verifyCode(newMobile, "04", code)) {
			return R.error("动态码验证错误，请重新获取");
		}

		Long time = SessionUtil.get(SessionUtil.LM_UPDATE_MB + "_" + lmUser.getMobile(), Long.class);
		if (null == time) {
			return R.error("用户未修改手机号，请返回后重试");
		}

		// 5分钟未修改手机号
		if (time < System.currentTimeMillis() - DateUtils.MILLIS_PER_MINUTE * 5) {
			return R.error("用户修改手机号时间太长，请返回后重新录入原手机号进行修改");
		}

		lmUser = lmUserService.get(lmUser.getId());
		lmUser.setMobile(newMobile);
		lmUserService.edit(lmUser, "02");

		// 更新session中的用户
		SessionUtil.saveLmUser(lmUser);

		return R.ok("您的电话号码已修改成功！");
	}

	/**
	 * 验证原密码
	 */
	@PostMapping("/verifyPwd")
	public R verifyPwd(@RequestParam Map<String, String> params) {
		String oldPassword = params.get("oldPassword");

		LmUser lmUser = SessionUtil.getLmUser();
		if (null == lmUser) {
			return R.error("用户未登录，请返回后重试");
		}

		if (!StringUtils.equals(lmUser.getPassword(), oldPassword)) {
			return R.error("原密码录入错误");
		}

		SessionUtil.save(SessionUtil.LM_UPDATE_PWD + "_" + lmUser.getMobile(), System.currentTimeMillis());

		return R.ok();
	}

	/**
	 * 保存新密码
	 */
	@PostMapping("/savePwd")
	public R savePwd(@RequestParam Map<String, String> params) {
		String newPassword = params.get("newPassword");

		LmUser lmUser = SessionUtil.getLmUser();
		if (null == lmUser) {
			return R.error("用户未登录，请返回后重试");
		}

		Long time = SessionUtil.get(SessionUtil.LM_UPDATE_PWD + "_" + lmUser.getMobile(), Long.class);
		if (null == time) {
			return R.error("用户未重置密码，请返回后重试");
		}

		// 5分钟未修改密码
		if (time < System.currentTimeMillis() - DateUtils.MILLIS_PER_MINUTE * 5) {
			return R.error("用户重置密码时间太长，请返回后重新修改密码");
		}

		lmUser = lmUserService.get(lmUser.getId());
		lmUser.setPassword(newPassword);
		lmUserService.edit(lmUser, "03");

		// 更新session中的用户
		SessionUtil.saveLmUser(lmUser);
		SessionUtil.save(SessionUtil.LM_UPDATE_PWD + "_" + lmUser.getMobile(), null);

		return R.ok("您的登录密码已修改成功！");
	}

	/**
	 * 实名信息上传
	 * 
	 * @param certPositive
	 *            证件正面
	 * @param certReverse
	 *            证件反面
	 * @param certHand
	 *            手持证件照
	 * @return
	 */
	@PostMapping("/uploadRealName")
	public R uploadRealName(@RequestParam("certPositive") MultipartFile certPositive, @RequestParam("certReverse") MultipartFile certReverse, @RequestParam("certHand") MultipartFile certHand) throws IOException {
		LmUser lmUser = SessionUtil.getLmUser();
		if (null == lmUser) {
			return R.error("用户未登录，请返回后重试");
		}

		if (StringUtils.equals(lmUser.getCertSign(), "1")) {
			return R.error("用户已实名认证");
		}

		if (certPositive.isEmpty() || certReverse.isEmpty() || certHand.isEmpty()) {
			return R.error("没有接收到三个文件");
		}

		// 用户上传实名信息不存历史
		lmUser = lmUserService.get(lmUser.getId());
		lmUser.setCertSign("2");
		lmUser.setCertPositive(saveFile(certPositive));
		lmUser.setCertReverse(saveFile(certReverse));
		lmUser.setCertHand(saveFile(certHand));
		lmUser.setUsrUpdateTime(new Date());
		lmUser.setLastUpdateTime(new Date());
		lmUserService.edit(lmUser);

		// 更新session中的用户
		SessionUtil.saveLmUser(lmUser);

		return R.ok();
	}

	/**
	 * 实名信息查询
	 * 
	 * @return
	 */
	@GetMapping("/getRealName")
	public R getRealName() {
		LmUser lmUser = SessionUtil.getLmUser();
		if (null == lmUser) {
			return R.error("用户未登录，请返回后重试");
		}

		Map<String, Object> user = new HashMap<String, Object>(8);

		String username = "*" + StringUtils.substring(lmUser.getUsername(), 1, StringUtils.length(lmUser.getUsername()));
		String certNo = StringUtils.substring(lmUser.getCertNo(), 0, 1) + "***************" + StringUtils.substring(lmUser.getCertNo(), StringUtils.length(lmUser.getCertNo()) - 1, StringUtils.length(lmUser.getCertNo()));

		user.put("username", username);
		user.put("certType", "身份证");
		user.put("certNo", certNo);
		user.put("certSign", lmUser.getCertSign()); // 实名认证标志（1是0否2认证中）
		user.put("certRemark", lmUser.getCertRemark()); // 评语

		return R.ok(user);
	}

	/**
	 * 查询昵称（如果昵称不存在则返回手机号）
	 * 
	 * @return
	 */
	@GetMapping("/getName")
	public R getName() {
		LmUser lmUser = SessionUtil.getLmUser();
		if (null == lmUser) {
			return R.error("用户未登录，请返回后重试");
		}

		if (StringUtils.isNotBlank(lmUser.getUsername())) {
			return R.ok(lmUser.getUsername());
		}

		return R.ok(lmUser.getMobile());
	}

	/**
	 * 保存文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private String saveFile(MultipartFile file) throws IOException {
		if (null == file || file.isEmpty()) {
			return null;
		}

		LmFile lmFile = new LmFile();
		lmFile.setId(UUIDGenerator.generate());
		lmFile.setLmFile(file.getBytes());
		lmFile.setLmType(file.getContentType());
		lmFile.setCreateTime(new Date());
		lmFile.setUpdateTime(new Date());
		lmFileService.save(lmFile);

		return lmFile.getId();
	}

}
