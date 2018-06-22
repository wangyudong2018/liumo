package com.yiyun.web.system.service.impl;

import com.yiyun.constants.RedisConstants;
import com.yiyun.dao.master.RoleDao;
import com.yiyun.dao.master.RoleMenuDao;
import com.yiyun.dao.master.UserDao;
import com.yiyun.dao.master.UserRoleDao;
import com.yiyun.domain.RoleDO;
import com.yiyun.domain.RoleMenuDO;
import com.yiyun.web.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleDao roleMapper;
	@Autowired
	RoleMenuDao roleMenuMapper;
	@Autowired
	UserDao userMapper;
	@Autowired
	UserRoleDao userRoleMapper;

	@Cacheable(value = RedisConstants.CACHE_NAME_SYSTEM, key ="'" +  RedisConstants.CACHE_KEY_SYS_ROLE_ALL + "'")
	@Override
	public List<RoleDO> list() {
        return roleMapper.list(new HashMap<>(16));
	}

	@Override
	public List<RoleDO> list(Long userId) {
		List<Long> rolesIds = userRoleMapper.listRoleId(userId);
		List<RoleDO> roles = roleMapper.list(new HashMap<>(16));
		for (RoleDO roleDO : roles) {
			roleDO.setRoleSign("false");
			for (Long roleId : rolesIds) {
				if (Objects.equals(roleDO.getRoleId(), roleId)) {
					roleDO.setRoleSign("true");
					break;
				}
			}
		}
		return roles;
	}

	@CacheEvict(value = RedisConstants.CACHE_NAME_SYSTEM, key ="'" +  RedisConstants.CACHE_KEY_SYS_ROLE_ALL + "'")
	@Transactional
	@Override
	public int save(RoleDO role) {
		int count = roleMapper.save(role);
		List<Long> menuIds = role.getMenuIds();
		Long roleId = role.getRoleId();
		List<RoleMenuDO> rms = new ArrayList<>();
		menuIds.forEach( menuId -> {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        });

		roleMenuMapper.removeByRoleId(roleId);
		if (rms.size() > 0) {
			roleMenuMapper.batchSave(rms);
		}
		return count;
	}

	@CacheEvict(value = RedisConstants.CACHE_NAME_SYSTEM, key ="'" +  RedisConstants.CACHE_KEY_SYS_ROLE_ALL + "'")
	@Transactional
	@Override
	public int remove(Long id) {
		int count = roleMapper.remove(id);
		roleMenuMapper.removeByRoleId(id);
		return count;
	}

	@Override
	public RoleDO get(Long id) {
        return roleMapper.get(id);
	}

	@CacheEvict(value = RedisConstants.CACHE_NAME_SYSTEM, key ="'" +  RedisConstants.CACHE_KEY_SYS_ROLE_ALL + "'")
	@Override
	public int update(RoleDO role) {
		int r = roleMapper.update(role);
		List<Long> menuIds = role.getMenuIds();
		Long roleId = role.getRoleId();
		roleMenuMapper.removeByRoleId(roleId);
		List<RoleMenuDO> rms = new ArrayList<>();
        menuIds.forEach( menuId -> {
            RoleMenuDO rmDo = new RoleMenuDO();
            rmDo.setMenuId(menuId);
            rmDo.setRoleId(roleId);
            rms.add(rmDo);
        });
		// roleMenuMapper.removeByRoleId(roleId);
		if (rms.size() > 0) {
			roleMenuMapper.batchSave(rms);
		}
		return r;
	}

	@Override
	public int batchremove(Long[] ids) {
        return roleMapper.batchRemove(ids);
	}

}
