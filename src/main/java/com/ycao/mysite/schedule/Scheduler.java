package com.ycao.mysite.schedule;

import com.ycao.mysite.dao.AutotaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
    this class is used to make auto-tasks
 */
@Component
public class Scheduler {

    @Autowired
    AutotaskDao autotask;

    @Scheduled(cron="0 0 0 1 * *") //auto-execute At 00:00 on day-of-month 1
    public String clearRemovedData(){
        int delAttach = autotask.realDeleteAttach();
        int delCat = autotask.realDeleteCategory();
        int delMd = autotask.realDeleteMarkdown();
        return "["+delAttach+"] attaches deleted, ["+delCat+"] categories deleted, ["+delMd+"] Markdowns deleted";
    }

}

