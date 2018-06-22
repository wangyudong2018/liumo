package com.yiyun.app.product.controller;

import com.yiyun.app.common.controller.BaseController;
import com.yiyun.app.system.service.IAppSysDictService;
import com.yiyun.constants.SysDictConstants;
import com.yiyun.domain.DictDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produdct")
public class ProductController extends BaseController {

    @Autowired
    private IAppSysDictService dictService;

    @RequestMapping("/getProductList")
    public void getProductList(HttpServletResponse response) {
        Map<String, Object> productMap = new HashMap<>();//支持的产品列表
        productMap.put("type", SysDictConstants.DICT_TYPE_PRODUCT_TYPE);
        List<DictDO> productList = dictService.list(productMap);
        returnSuccess(productList, "查询成功", response);
    }

}
