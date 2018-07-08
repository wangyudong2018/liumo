package com.yiyun.web.liumo.service;

import com.yiyun.domain.LmRecruit;

import java.util.List;
import java.util.Map;

/**
 * @title 六漠招聘
 * @author WangYuDong
 * @date Sun Jul 08 10:02:59 CST 2018
 */
public interface LmRecruitService {

	LmRecruit get(Long id);

	List<LmRecruit> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmRecruit lmRecruit);

	int edit(LmRecruit lmRecruit);

	int remove(Long id);

	int batchRemove(Long[] ids);

}
