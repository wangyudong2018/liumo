package com.yiyun.core;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CoreHelloController {

    @ResponseBody
    @GetMapping(value = "/hello")
    public String hello() {
//        MqRepayNotifyVO vo = new MqRepayNotifyVO();
//        vo.setSerialNum("15174815441711666");
//        vo.setSuccess(true);
//        vo.setRespNum("PONCb27086e2ee5b4ed0b35329c69a0cbae4");
//        vo.setAmount(BigDecimal.valueOf(0.02));
//        repaymentService.deductNotify(vo);

//        refundService.refund("O931517558494576");
        return "CoreServer启动成功...";
    }
}
