package com.yiyun.web.order.controller;

import com.yiyun.constants.ReserveConstants;
import com.yiyun.constants.SystemConstants;
import com.yiyun.domain.OReserve;
import com.yiyun.domain.RoleDO;
import com.yiyun.resp.AddReserveResp;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.order.service.OReserveService;
import com.yiyun.web.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @title 预约
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
@Controller
@RequestMapping("/oReserve")
public class OReserveController extends BaseController {
    @Autowired
    private OReserveService oReserveService;

    @Autowired
    private RoleService roleService;

    @GetMapping()
    @RequiresPermissions("oReserve:oReserve")
    public String OReserve() {
        return "order/oReserve/oReserve";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("oReserve:oReserve")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        Long userId = getUserId();
        List<RoleDO> roleList = roleService.list(userId); //查询当前用户下所有的角色
        List<String> roleRemarkList = roleList.stream()
                .filter(role -> Objects.equals(role.getRoleSign(), "true"))
                .map(RoleDO::getRemark).collect(Collectors.toList());//当前用户拥有的所有角色code
        if (!roleRemarkList.contains(SystemConstants.ROLE_CODE_SUPER) && roleRemarkList.contains(SystemConstants.ROLE_CODE_SALESMAN)) {//业务员 , 根据业务员ID查询
            params.put("salesmanId", userId);
            params.put("reserveStauts", ReserveConstants.RESERVE_STATUS_YSL);//业务员只查询已受理的
        }

        params.put("sort", "reserve_time");//预约时间最近的优先
        //查询列表数据
        Query query = new Query(params);//
        List<OReserve> oReserveList = oReserveService.list(query);
        int total = oReserveService.count(query);
        return new PageUtil(oReserveList, total);
    }

    @ResponseBody
    @PostMapping("/add")
    @RequiresPermissions("oReserve:add")
    public R add() {
        Long userId = getUserId();//当前登录角色
        AddReserveResp resp = oReserveService.receive(userId);
        if (resp.isFlag()) {
            Map<String, Object> map = new HashMap<>();
            map.put("reserveId", resp.getReserveId());
            map.put("reserveTime", resp.getReserveTime());
            return R.ok(map);
        } else {
            return R.error(resp.getMessage());
        }
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("oReserve:edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        OReserve oReserve = oReserveService.get(id);
        model.addAttribute("oReserve", oReserve);
        return "order/oReserve/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("oReserve:add")
    public R save(OReserve oReserve) {
        if (oReserveService.save(oReserve) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("oReserve:edit")
    public R update(OReserve oReserve) {
        oReserveService.update(oReserve);
        return R.ok();
    }

    /**
     * 最终审核
     */
    @ResponseBody
    @RequestMapping("/check")
    @RequiresPermissions("oReserve:check")
    public R check(OReserve oReserve) {
        oReserveService.check(oReserve);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/cancel")
    @ResponseBody
    @RequiresPermissions("oReserve:cancel")
    public R cancel(Long id) {
        if (oReserveService.cancel(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("oReserve:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        oReserveService.batchRemove(ids);
        return R.ok();
    }

}
