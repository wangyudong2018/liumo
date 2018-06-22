package com.yiyun.web.system.controller;

import com.yiyun.domain.MenuDO;
import com.yiyun.domain.Tree;
import com.yiyun.utils.MD5Util;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.common.annotation.Log;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.common.utils.ShiroUtils;
import com.yiyun.web.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value = "LoginController", description = "用户相关api")
@Controller
public class LoginController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuService menuService;

    @GetMapping({"/manager", ""})
    String welcome(Model model) {
        return "redirect:/index";
    }

    @Log("请求访问主页")
    @GetMapping({"/index"})
    String index(Model model) {
        List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
        model.addAttribute("menus", menus);
        model.addAttribute("name", getUser().getName());
        logger.info(getUser().getName());
        return "index_v1";
    }

    //前台首页
    @GetMapping("/")
    String main() {
        return "redirect:/main/index";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }


    @ApiOperation(value = "登录", tags = {""}, notes = "后台登录")
    @Log("登录")
    @PostMapping("/login")
    @ResponseBody
    R ajaxLogin(@ApiParam(name = "username", value = "用户名", required = true) String username, @ApiParam(name = "password", value = "用户密码", required = true) String password) {
        password = MD5Util.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);


            return R.ok();
        } catch (AuthenticationException e) {
            return R.error("用户或密码错误");
        }
    }

    @GetMapping("/logout")
    String logout() {
        ShiroUtils.logout();
        return "redirect:/login";
    }

	@GetMapping("/main")
	String main(Model model) {
		return "main";
	}

    @GetMapping("/403")
    String error403() {
        return "403";
    }

}
