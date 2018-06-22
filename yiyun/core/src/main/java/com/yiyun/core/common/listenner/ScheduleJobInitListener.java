package com.yiyun.core.common.listenner;

import com.yiyun.core.base.service.JobService;
import com.yiyun.core.common.quartz.utils.QuartzManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

    @Autowired
    JobService scheduleJobService;

    @Autowired
    QuartzManager quartzManager;

    @Override
    public void run(String... arg0) throws Exception {
        try {
            scheduleJobService.initSchedule();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}