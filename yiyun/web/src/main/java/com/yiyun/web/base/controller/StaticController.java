package com.yiyun.web.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @title
 * @author Xingbz
 * @createDate 2018-6-8
 */
@Controller
public class StaticController {

    @RequestMapping("FontIcoList.html")
    public String iconList(){
        return "static/FontIcoList";
    }
}