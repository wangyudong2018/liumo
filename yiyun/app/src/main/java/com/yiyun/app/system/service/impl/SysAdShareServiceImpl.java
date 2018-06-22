package com.yiyun.app.system.service.impl;

import com.yiyun.app.system.resp.SysAdShareResp;
import com.yiyun.app.system.resp.SysAdShareVO;
import com.yiyun.app.system.service.SysAdShareService;
import com.yiyun.dao.cluster.ClusterSysAdShareDao;
import com.yiyun.domain.SysAdShareDO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysAdShareServiceImpl implements SysAdShareService {

    @Autowired
    private ClusterSysAdShareDao clusterSysAdShareDao;

    @Override
    public SysAdShareDO get(Long id) {
        return clusterSysAdShareDao.get(id);
    }

    @Override
    public List<SysAdShareDO> list(Map<String, Object> map) {
        return clusterSysAdShareDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterSysAdShareDao.count(map);
    }

    @Override
    public List<SysAdShareResp> list(Integer category, Integer sysType) throws InvocationTargetException, IllegalAccessException {
//        List<SysAdShareResp> list = new ArrayList<>();
        List<SysAdShareDO> list = clusterSysAdShareDao.listAll(category, sysType);
        SysAdShareResp resp = new SysAdShareResp();
        Map<String, String> m = new HashMap<>();

        if (list != null && !list.isEmpty()) {
            List<SysAdShareResp> result = new ArrayList<>();
            list.forEach(sdo -> {
                Integer cate = sdo.getCategory();
                SysAdShareResp bean = getBeanByClassNm(cate, result);
                if (bean == null) {//第一次遍历到 , 还没有数据
                    bean = new SysAdShareResp();
                    bean.setCategory(cate);
                    List<SysAdShareVO> datas = new ArrayList<>();
                    SysAdShareVO s = new SysAdShareVO();
                    BeanUtils.copyProperties(sdo, s);
                    datas.add(s);
                    bean.setContent(datas);
                    result.add(bean);
                } else {
                    SysAdShareVO s = new SysAdShareVO();
                    BeanUtils.copyProperties(sdo, s);
                    bean.getContent().add(s);
                }
            });

            return result;
        }
        return null;
    }

    @Override
    public List<Map> getBannerList() {
        List<SysAdShareDO> bannerList = clusterSysAdShareDao.listAll(5, null);
        ArrayList list = new ArrayList();
        HashMap<String, Object> map = new HashMap();
        if (bannerList.size() > 0) {
            for (SysAdShareDO sysAdShareDO : bannerList) {
                map.put("organId", sysAdShareDO.getAdvertiser());
                map.put("bannerPic", sysAdShareDO.getCommonImg());
                list.add(map);
            }
        }
        return list;
    }

    private static SysAdShareResp getBeanByClassNm(Integer category, List<SysAdShareResp> list) {
        if (null == category || list == null || list.isEmpty()) {
            return null;
        }
        for (SysAdShareResp bean : list) {
            if (category.equals(bean.getCategory())) {
                return bean;
            }
        }
        return null;
    }

}
