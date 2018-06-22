package com.yiyun.web.system.controller;


import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.domain.DeptDO;
import com.yiyun.domain.RoleDO;
import com.yiyun.domain.Tree;
import com.yiyun.domain.UserDO;
import com.yiyun.utils.MD5Util;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.annotation.Log;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.query.service.MMemberService;
import com.yiyun.web.system.service.RoleService;
import com.yiyun.web.system.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@ApiIgnore
@RequestMapping("/sys/user")
@Controller
public class UserController extends BaseController {
    private String prefix = "system/user";
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Autowired
    private MMemberService memberService;

    @Autowired
    private DictService dictService;

    @RequiresPermissions("sys:user:user")
    @GetMapping("")
    String user(Model model) {
        return prefix + "/user";
    }

    @GetMapping("/list")
    @ResponseBody
    PageUtil list(@RequestParam Map<String, Object> params) {
        // 查询列表数据
        Query query = new Query(params);
        List<UserDO> sysUserList = userService.list(query);
        int total = userService.count(query);
        return new PageUtil(sysUserList, total);
    }

    private void addSelects(Model model) {
        Map<String, Object> param = new HashedMap();
        param.put("delFlag", CommonConstants.YES);
        param.put("type", SysDictConstants.DICT_TYPE_CITYS);
        model.addAttribute("cityList", dictService.list(param));
    }

    @RequiresPermissions("sys:user:add")
    @Log("添加用户")
    @GetMapping("/add")
    String add(Model model) {
        addSelects(model);

        List<RoleDO> roles = roleService.list();
        model.addAttribute("roles", roles);
        return prefix + "/add";
    }

    @RequiresPermissions("sys:user:edit")
    @Log("编辑用户")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") Long id) {
        addSelects(model);

        UserDO userDO = userService.get(id);
        model.addAttribute("user", userDO);
        List<RoleDO> roles = roleService.list(id);
        model.addAttribute("roles", roles);
        return prefix + "/edit";
    }

    @RequiresPermissions("sys:user:add")
    @Log("保存用户")
    @PostMapping("/save")
    @ResponseBody
    R save(UserDO user) {
        if (null != user) {
            String investCode = user.getInvestCode();
            if (StringUtils.isNotBlank(investCode) && !memberService.checkInvestCode(investCode)) {
                return R.error("邀请码重复");
            }
            if (StringUtils.isBlank(investCode)) {
                user.setInvestCode(memberService.createInvestCode());
            }
            user.setPassword(MD5Util.encrypt(user.getUsername(), user.getPassword()));
        }

        if (userService.save(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:edit")
    @Log("更新用户")
    @PostMapping("/update")
    @ResponseBody
    R update(UserDO user) {
        if(null != user && StringUtils.isNotBlank(user.getInvestCode())){
            user.setInvestCode(null);
        }
        if (userService.update(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:remove")
    @Log("删除用户")
    @PostMapping("/remove")
    @ResponseBody
    R remove(Long id) {
        if (userService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @RequiresPermissions("sys:user:batchRemove")
    @Log("批量删除用户")
    @PostMapping("/batchRemove")
    @ResponseBody
    R batchRemove(@RequestParam("ids[]") Long[] userIds) {
        int r = userService.batchremove(userIds);
        if (r > 0) {
            return R.ok();
        }
        return R.error();
    }

    @PostMapping("/exit")
    @ResponseBody
    boolean exit(@RequestParam Map<String, Object> params) {
        // 存在，不通过，false
        return !userService.exit(params);
    }

    @RequiresPermissions("sys:user:resetPwd")
    @Log("请求更改用户密码")
    @GetMapping("/resetPwd/{id}")
    String resetPwd(@PathVariable("id") Long userId, Model model) {
        UserDO userDO = new UserDO();
        userDO.setUserId(userId);
        model.addAttribute("user", userDO);
        return prefix + "/reset_pwd";
    }

    @Log("提交更改用户密码")
    @PostMapping("/resetPwd")
    @ResponseBody
    R resetPwd(UserDO user) {
        user.setPassword(MD5Util.encrypt(userService.get(user.getUserId()).getUsername(), user.getPassword()));
        if (userService.resetPwd(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    @GetMapping("/tree")
    @ResponseBody
    public Tree<DeptDO> tree() {
        Tree<DeptDO> tree = new Tree<DeptDO>();
        tree = userService.getTree();
        return tree;
    }

    @GetMapping("/treeView")
    String treeView() {
        return prefix + "/userTree";
    }

    @GetMapping("/createInvestCode")
    @ResponseBody
    public String createInvestCode() {
        return memberService.createInvestCode();
    }

}
