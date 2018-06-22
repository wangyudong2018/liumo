package com.yiyun.web.operation.controller;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.domain.NewProduct;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.operation.service.NewProductService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @title
 * @author Xing
 * @date Fri Jun 08 10:55:33 CST 2018
 */
@Controller
@RequestMapping("/operation/newProduct")
public class NewProductController {
    @Autowired
    private NewProductService newProductService;

    @Autowired
    private DictService dictService;

    @GetMapping()
    @RequiresPermissions("operation:newProduct:newProduct")
    public String NewProduct(Model model) {
        addSelects(model);
        return "operation/newProduct/newProduct";
    }

    private void addSelects(Model model) {
        Map<String, Object> param = new HashedMap();
        param.put("delFlag", CommonConstants.YES);
        param.put("type", SysDictConstants.DICT_TYPE_CITYS);
        model.addAttribute("cityList", dictService.list(param));

        param.put("delFlag", CommonConstants.YES);
        param.put("type", SysDictConstants.NEW_TYPE_PRODUCT_TYPE);
        model.addAttribute("typeList", dictService.list(param));
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("operation:newProduct:newProduct")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        Object searchTime = params.get("searchTime");

        if (null != searchTime && StringUtils.isNotBlank((String) searchTime)) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse((String) searchTime, dtf);
                LocalDateTime createTimeBegin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime createTimeEnd = createTimeBegin.plusDays(1L);
                params.put("createTimeBegin", createTimeBegin.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
                params.put("createTimeEnd", createTimeEnd.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            } catch (Exception e) {
                System.out.println("时间转换异常");
            }
        }
        Object modifyTime = params.get("modifyTime");
        if (null != modifyTime && StringUtils.isNotBlank((String) modifyTime)) {
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse((String) modifyTime, dtf);
                LocalDateTime updateTimeBegin = LocalDateTime.of(localDate, LocalTime.MIN);
                LocalDateTime updateTimeEnd = updateTimeBegin.plusDays(1L);
                params.put("updateTimeBegin", updateTimeBegin.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
                params.put("updateTimeEnd", updateTimeEnd.toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
            } catch (Exception e) {
                System.out.println("时间转换异常");
            }
        }

        //查询列表数据
        Query query = new Query(params);
        List<NewProduct> newProductList = newProductService.list(query);
        int total = newProductService.count(query);
        return new PageUtil(newProductList, total);
    }

    @GetMapping("/add")
    @RequiresPermissions("operation:newProduct:add")
    public String add(Model model) {
        addSelects(model);
        return "operation/newProduct/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("operation:newProduct:edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        NewProduct newProduct = newProductService.get(id);
        model.addAttribute("newProduct", newProduct);
        addSelects(model);
        return "operation/newProduct/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("operation:newProduct:add")
    public R save(NewProduct newProduct) {
        if (null == newProduct.getStatus()) {
            newProduct.setStatus(CommonConstants.NO);
        }
        if (null == newProduct.getCreateTime()) {
            newProduct.setCreateTime(Instant.now().toEpochMilli());
        }
        if (newProductService.save(newProduct) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("operation:newProduct:edit")
    public R update(NewProduct newProduct) {
        if (null == newProduct.getStatus()) {
            newProduct.setStatus(CommonConstants.NO);
        }
        newProduct.setUpdateTime(Instant.now().toEpochMilli());
        newProductService.update(newProduct);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("operation:newProduct:remove")
    public R remove(Long id) {
        if (newProductService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("operation:newProduct:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        newProductService.batchRemove(ids);
        return R.ok();
    }

}
