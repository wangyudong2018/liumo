package com.yiyun.dao.master;

import com.yiyun.domain.LmBanner;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 六漠banner表
 * @author WangYuDong
 * @date Thu Jul 05 22:19:09 CST 2018
 */
public interface LmBannerDao extends MyMapper<LmBanner> {

	LmBanner get(Long id);

	List<LmBanner> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmBanner lmBanner);

	int edit(LmBanner lmBanner);

	int remove(Long id);

	int batchRemove(Long[] ids);

}
