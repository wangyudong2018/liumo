package com.yiyun.web.order.controller;

import com.yiyun.domain.OOrder;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.order.service.OOrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @title 订单
 * @author Xingbz
 * @date Thu Mar 08 16:26:18 CST 2018
 */
@Controller
@RequestMapping("/order")
public class OOrderController {
    @Autowired
    private OOrderService orderService;

    @GetMapping()
    @RequiresPermissions("order:order")
    public String OOrder() {
        return "order/oOrder/oOrder";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("order:order")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<OOrder> orderList = orderService.list(query);
        int total = orderService.count(query);
        return new PageUtil(orderList, total);
    }

    @GetMapping("/add")
    @RequiresPermissions("order:add")
    public String add() {
        return "order/oOrder/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("order:edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        OOrder order = orderService.get(id);
        model.addAttribute("order", order);
        return "order/oOrder/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("order:add")
    public R save(OOrder order) {
        if (orderService.save(order) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("order:edit")
    public R update(OOrder order) {
        orderService.update(order);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("order:remove")
    public R remove(Long id) {
        if (orderService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("order:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        orderService.batchRemove(ids);
        return R.ok();
    }

}
