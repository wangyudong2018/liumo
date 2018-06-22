package com.yiyun.web.base.controller;

import com.yiyun.constants.SysDictConstants;
import com.yiyun.dao.master.UserRoleDao;
import com.yiyun.domain.RoleDO;
import com.yiyun.domain.UserDO;
import com.yiyun.web.common.utils.ShiroUtils;
import com.yiyun.web.system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleDao userRoleDao;

    public String returnData(String data) {
        String result = "{\"flag\":\"1\",\"DATA\":" + data + ",\"message\":\"\"}";

        return result;
    }

    public String returnData(String data, String message) {
        String result = "{\"flag\":\"1\",\"DATA\":" + data + ",\"message\":\"" + message + "\"}";

        return result;
    }

    public String returnData(int data) {
        String result = "{\"flag\":\"1\",\"DATA\":" + data + ",\"message\":\"\"}";

        return result;
    }

    public String returnSucess(String message) {
        String result = "{\"flag\":\"1\",\"message\":\"" + message + "\"}";

        return result;
    }

    public String returnFail(String message) {
        String result = "{\"flag\":\"0\",\"message\":\"" + message + "\"}";

        return result;
    }

    public String returnFailData(String data) {
        String result = "{\"flag\":\"0\",\"DATA\":" + data + ",\"message\":\"\"}";

        return result;
    }

    public UserDO getUser() {
        return ShiroUtils.getUser();
    }

    public Long getUserId() {
        return getUser().getUserId();
    }

    public String getUsername() {
        return getUser().getUsername();
    }

    public String getName() {
        return getUser().getName();
    }


    public String getParamString(Map<String, Object> param, String key) {
        if (null == param || param.isEmpty() || StringUtils.isBlank(key)) {
            return null;
        }
        Object obj = param.get(key);
        if (null == obj || StringUtils.isBlank(obj + "") || "null".equals(obj + "") || "undefined".equals(obj + "")) {
            return null;
        }
        return obj + "";
    }

    protected boolean containsOperator(List<Long> roleIds) {
        if (null == roleIds || roleIds.isEmpty()) {
            return false;
        }
        List<RoleDO> allRole = roleService.list();//所有角色
        if (null == allRole || allRole.isEmpty()) {
            return false;
        }
        for (RoleDO roleDO : allRole) {
            if (null != roleDO && roleIds.contains(roleDO.getRoleId()) && Objects.equals(SysDictConstants.OPERATOR, roleDO.getRemark())) {//如果当前用户包含该角色 , 而该角色为运营
                return true;
            }
        }
        return false;
    }

    protected boolean containsCommerce(List<Long> roleIds) {
        if (null == roleIds || roleIds.isEmpty()) {
            return false;
        }
        List<RoleDO> allRole = roleService.list();//所有角色
        if (null == allRole || allRole.isEmpty()) {
            return false;
        }
        for (RoleDO roleDO : allRole) {
            if (null != roleDO && roleIds.contains(roleDO.getRoleId()) && Objects.equals(SysDictConstants.COMMERCE, roleDO.getRemark())) {//如果当前用户包含该角色 , 而该角色为商务
                return true;
            }
        }
        return false;
    }


    protected boolean containsSuper(List<Long> roleIds) {
        if (null == roleIds || roleIds.isEmpty()) {
            return false;
        }
        List<RoleDO> allRole = roleService.list();//所有角色
        if (null == allRole || allRole.isEmpty()) {
            return false;
        }
        for (RoleDO roleDO : allRole) {
            if (null != roleDO && roleIds.contains(roleDO.getRoleId()) && Objects.equals(SysDictConstants.SUPER_MANAGER, roleDO.getRemark())) {//如果当前用户包含该角色 , 而该角色为管理员
                return true;
            }
        }
        return false;
    }

    /** 是否是超级管理员 (分权使用) */
    protected int isSuper() {
        UserDO loginUser = ShiroUtils.getUser();
        if (null == loginUser) {
            return 0;
        }
        List<Long> roleIds = loginUser.getRoleIds();
        if (null == roleIds || roleIds.isEmpty()) {
            roleIds = userRoleDao.listRoleId(loginUser.getUserId());
        }
        if (null == roleIds || roleIds.isEmpty()) {
            return 0;
        }
        return containsSuper(roleIds) ? 1 : 0;
    }
}