package com.yiyun.web.operation.controller;

import com.yiyun.domain.PProduct;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.base.controller.BaseController;
import com.yiyun.web.base.service.DictService;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.operation.service.ProductService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author yxg
 * @title 产品
 * @date Fri Mar 09 13:56:36 CST 2018
 */
@Controller
@RequestMapping("/operation/product")
public class ProductController extends BaseController{
    @Autowired
    private ProductService productService;

    @Autowired
    private DictService dictService;

    @GetMapping()
    @RequiresPermissions("operation:product:product")
    public String Product() {
        return "operation/product/product";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("operation:product:product")
    public PageUtil list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<PProduct> productList = productService.list(query);
        int total = productService.count(query);
        return new PageUtil(productList, total);
    }



    @GetMapping("/add")
    @RequiresPermissions("operation:product:add")
    public String add() {
        return "operation/product/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("operation:product:edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        PProduct product = productService.get(id);
        model.addAttribute("product", product);
        return "operation/product/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("operation:add")
    public R save(PProduct product) {
        product.setProductStatus(1);
        product.setCreateTime(System.currentTimeMillis());
        if (productService.save(product) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("operation:product:edit")
    public R update(PProduct product) {
        productService.update(product);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("operation:product:remove")
    public R remove(Long id) {
        if (productService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("operation:product:batchRemove")
    public R remove(@RequestParam("ids[]") Long[] ids) {
        productService.batchRemove(ids);
        return R.ok();
    }

}
