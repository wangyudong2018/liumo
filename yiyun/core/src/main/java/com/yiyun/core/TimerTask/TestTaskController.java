package com.yiyun.core.TimerTask;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title
 * @description
 * @author Xingbz
 * @createDate 2018/2/8
 * @version 1.0
 */
@RestController
@RequestMapping("/test")
public class TestTaskController {



    @ResponseBody
    @GetMapping(value = "/autoDeduct")
    public String autoDeduct() {
        return "自动代扣请求成功 . . . ";
    }
}
