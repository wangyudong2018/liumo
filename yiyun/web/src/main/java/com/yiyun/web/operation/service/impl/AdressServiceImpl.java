package com.yiyun.web.operation.service.impl;

import com.yiyun.dao.cluster.ClusterAdressDao;
import com.yiyun.dao.master.AdressDao;
import com.yiyun.domain.Adress;
import com.yiyun.web.operation.service.AdressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdressServiceImpl implements AdressService {

    @Autowired
	private AdressDao adressDao;

	@Autowired
	private ClusterAdressDao clusterAdressDao;
	
	@Override
	public Adress get(Long id){
		return clusterAdressDao.get(id);
	}
	
	@Override
	public List<Adress> list(Map<String, Object> map){
		return clusterAdressDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return clusterAdressDao.count(map);
	}
	
	@Override
	public int save(Adress adress){
		return adressDao.save(adress);
	}
	
	@Override
	public int update(Adress adress){
		return adressDao.update(adress);
	}
	
	@Override
	public int remove(Long id){
		return adressDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return adressDao.batchRemove(ids);
	}
	
}
