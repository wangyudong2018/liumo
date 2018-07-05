package com.yiyun.dao.master;

import com.yiyun.domain.LmFile;

import java.util.List;
import java.util.Map;
import com.yiyun.common.MyMapper;

/**
 * @title 六漠文件表
 * @author WangYuDong
 * @date Thu Jul 05 22:19:09 CST 2018
 */
public interface LmFileDao extends MyMapper<LmFile> {

	LmFile get(Long id);

	List<LmFile> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(LmFile lmFile);

	int edit(LmFile lmFile);

	int remove(Long id);

	int batchRemove(Long[] ids);

}
