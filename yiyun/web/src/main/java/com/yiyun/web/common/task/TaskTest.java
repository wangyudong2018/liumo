package com.yiyun.web.common.task;

import com.yiyun.domain.Response;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskTest implements Job{
	@Autowired
	SimpMessagingTemplate template;
//	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		template.convertAndSend("/topic/getResponse", new Response("Welcome,websocket!"));
		
	}
}