package com.yiyun.web.liumo.service;

import com.yiyun.domain.LmSmsLog;

import java.util.List;
import java.util.Map;

/**
 * @title 六漠短信流水表
 * @author wangyudong
 * @date Sat Jul 14 12:58:33 CST 2018
 */
public interface LmSmsLogService {

	LmSmsLog get(Long id);

	List<LmSmsLog> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmSmsLog lmSmsLog);

	int save(String mobile, String type, String content);

	int edit(LmSmsLog lmSmsLog);

	int remove(Long id);

	int batchRemove(Long[] ids);

}
