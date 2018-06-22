package com.yiyun.core.rabbitMQ.controller;

import com.yiyun.core.rabbitMQ.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;


/**
 * Rabbit MQ
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/rabbitmq")
public class RabbitMqController {

    @Autowired
    private Sender sender;

    /**
     * 发送消息队列
     */
    @RequestMapping(value = "/addRabbitMq", method = RequestMethod.POST)
    public String addEntity(String queueName, String content, HttpSession httpSession) {

        try {
//			sender.send(queueName, content);
//			List<LoanRecordVo> delayRecord = loanRecordTimerService.getDelayRecord();
//			HashMap<String,String> mp=new HashMap<String,String>();
//			for (int i = 0; i < delayRecord.size(); i++) {
//				mp.put("phone", delayRecord.get(i).getPhone().toString());
//				mp.put("memberId", delayRecord.get(i).getMemberId().toString());
//				mp.put("title", "钱进推送");
//				mp.put("content","您的订单已逾期，请及时还款！");
//				JSONObject js=new JSONObject();
//				js.putAll(mp);


            sender.send(queueName, content);
            System.out.println("发送完成！");

//			}
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }

}
