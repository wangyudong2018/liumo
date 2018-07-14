package com.yiyun.dao.master;

import com.yiyun.domain.LmSmsLog;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 六漠短信流水表
 * @author wangyudong
 * @date Sat Jul 14 12:58:33 CST 2018
 */
public interface LmSmsLogDao extends MyMapper<LmSmsLog> {

	LmSmsLog get(Long id);

	List<LmSmsLog> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmSmsLog lmSmsLog);

	int edit(LmSmsLog lmSmsLog);

	int remove(Long id);

	int batchRemove(Long[] ids);

}
