package com.yiyun.dao.master;

import com.yiyun.domain.LmRelease;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 六漠新闻媒体发布
 * @author WangYuDong
 * @date Sat Jul 07 12:07:07 CST 2018
 */
public interface LmReleaseDao extends MyMapper<LmRelease> {

	LmRelease get(Long id);

	List<LmRelease> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmRelease lmRelease);

	int edit(LmRelease lmRelease);

	int remove(Long id);

	int batchRemove(Long[] ids);

	int batchRemoveFile(Long[] ids);

}
