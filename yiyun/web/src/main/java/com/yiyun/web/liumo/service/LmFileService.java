package com.yiyun.web.liumo.service;

import com.yiyun.domain.LmFile;

import java.util.List;
import java.util.Map;

/**
 * @title 六漠文件表
 * @author wangyudong
 * @date Thu Jun 28 23:09:06 CST 2018
 */
public interface LmFileService {

	LmFile get(Long id);

	List<LmFile> list(Map<String, Object> map);

	List<LmFile> newList(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmFile lmFile);

	int edit(LmFile lmFile);

	int remove(Long id);

	int batchRemove(Long[] ids);

}
