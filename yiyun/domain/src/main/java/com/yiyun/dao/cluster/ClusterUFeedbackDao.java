package com.yiyun.dao.cluster;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.UFeedbackDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户反馈表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-12-13 16:02:47
 */
@Mapper
public interface ClusterUFeedbackDao extends MyMapper<UFeedbackDO> {

	UFeedbackDO get(Long id);

	List<UFeedbackDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

}
