package com.yiyun.web.system.service.impl;


import com.yiyun.dao.cluster.ClusterUserDao;
import com.yiyun.dao.master.DeptDao;
import com.yiyun.dao.master.UserDao;
import com.yiyun.dao.master.UserRoleDao;
import com.yiyun.domain.*;
import com.yiyun.web.common.utils.BuildTree;
import com.yiyun.web.system.service.UserService;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userMapper;

    @Autowired
    ClusterUserDao clusterUserMapper;

    @Autowired
    UserRoleDao userRoleMapper;
    @Autowired
    DeptDao deptMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDO get(Long id) {
        UserDO user = clusterUserMapper.get(id);
        List<Long> roleIds = userRoleMapper.listRoleId(id);
        user.setRoleIds(roleIds);

        Long deptId = user.getDeptId();
        if (null != deptId) {
            DeptDO deptDO = deptMapper.get(deptId);
            if (null != deptDO) {
                user.setDeptName(deptDO.getName());
            }
        }

        return user;
    }

    @Override
    public List<UserDO> list(Map<String, Object> map) {
//		return userMapper.list(map);
        List<UserDO> list1 = clusterUserMapper.list(map);
        System.out.println("主数据源 数据 : \n" + list1);

        List<UserDO> list2 = clusterUserMapper.list(map);
        System.out.println("从数据源 数据 : \n" + list2);

        return list1;
//        return userMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterUserMapper.count(map);
    }

    @Transactional
    @Override
    public int save(UserDO user) {
        int count = userMapper.save(user);
        Long userId = user.getUserId();
        List<Long> roles = user.getRoleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId : roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return count;
    }

    @Override
    public int update(UserDO user) {
        int r = userMapper.update(user);
        Long userId = user.getUserId();
        List<Long> roles = user.getRoleIds();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        for (Long roleId : roles) {
            UserRoleDO ur = new UserRoleDO();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return r;
    }

    @Override
    public int remove(Long userId) {
        userRoleMapper.removeByUserId(userId);
        return userMapper.remove(userId);
    }

    @Override
    public boolean exit(Map<String, Object> params) {
        boolean exit;
        exit = clusterUserMapper.list(params).size() > 0;
        return exit;
    }

    @Override
    public Set<String> listRoles(Long userId) {
        return null;
    }

    @Override
    public int resetPwd(UserDO user) {
        int r = userMapper.update(user);
        return r;
    }

    @Transactional
    @Override
    public int batchremove(Long[] userIds) {
        int count = userMapper.batchRemove(userIds);
        userRoleMapper.batchRemoveByUserId(userIds);
        return count;
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> depts = deptMapper.list(new HashMap<String, Object>(16));
        Long[] pDepts = deptMapper.listParentDept();
        List<Long> deptList = clusterUserMapper.listAllDept();
        Long[] uDepts = deptList.toArray(new Long[deptList.size()]);
        Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
        for (DeptDO dept : depts) {
            if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
                continue;
            }
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(dept.getDeptId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "dept");
            tree.setState(state);
            trees.add(tree);
        }
        List<UserDO> users = clusterUserMapper.list(new HashMap<String, Object>(16));
        for (UserDO user : users) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(user.getUserId().toString());
            tree.setParentId(user.getDeptId().toString());
            tree.setText(user.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "user");
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public UserDO getByInvestCode(String investCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("investCode", investCode);
        List<UserDO> userList = clusterUserMapper.list(param);
        if (null != userList && !userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    /**
     * 根据角色code获取用户
     * @param
     * @return
     */
    @Override
    public List<UserDO> getUserByRoleCode(Map<String, Object> map) {
        return clusterUserMapper.getUserByRoleCode(map);
    }

//	//查询某角色下的所有用户
//	@Override
//	public List<MMember> listByRole(String roleRemark) {
//		List<MMember> result = new ArrayList<>();
//		if (StringUtils.isBlank(roleRemark)) {
//			return result;
//		}
//
//		List<RoleDO> allRole = roleDao.list(null);//所有角色
//		if (null != allRole && !allRole.isEmpty()) {
//			for (RoleDO roleDO : allRole) {
//				if (roleRemark.equals(roleDO.getRemark())) {
//					Long roleId = roleDO.getRoleId();
//					Map<String, Object> userRoleParam = new HashMap<>();
//					userRoleParam.put("roleId", roleId);
//					List<UserRoleDO> userRoleList = userRoleDao.list(userRoleParam);
//					if (null != userRoleList && !userRoleList.isEmpty()) {
//						List<Long> ids = userRoleList.stream().map(UserRoleDO::getUserId).collect(Collectors.toList());
//						if (null != ids && !ids.isEmpty()) {
//							Map<String, Object> memberParam = new HashMap<>();
//							memberParam.put("ids", ids);
//							return clusterMMemberDao.list(memberParam);
//						}
//					}
//
//				}
//			}
//		}
//		return result;
//	}

}
