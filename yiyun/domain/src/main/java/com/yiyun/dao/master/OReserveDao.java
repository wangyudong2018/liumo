package com.yiyun.dao.master;

import com.yiyun.common.MyMapper;
import com.yiyun.domain.OReserve;

/**
 * @title 预约
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
public interface OReserveDao extends MyMapper<OReserve> {
    int save(OReserve oReserve);

    int update(OReserve oReserve);

    int remove(Long id);

    int batchRemove(Long[] ids);

    int cancel(Long id);
}
