package com.yiyun.web.liumo.service;

import com.yiyun.domain.LmRelease;

import java.util.List;
import java.util.Map;

/**
 * @title 六漠新闻媒体发布
 * @author wangyudong
 * @date Sun Jun 24 10:27:30 CST 2018
 */
public interface LmReleaseService {

	LmRelease get(Long id);

	LmRelease getLogo(Long id);

	List<LmRelease> list(Map<String, Object> map);

	List<LmRelease> newList(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmRelease lmRelease);

	int edit(LmRelease lmRelease);

	int remove(Long id);

	int batchRemove(Long[] ids);

}
