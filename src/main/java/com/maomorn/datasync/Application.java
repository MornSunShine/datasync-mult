package com.maomorn.datasync;

import com.maomorn.datasync.entity.DbInfo;
import com.maomorn.datasync.entity.JobInfo;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Author: MaoMorn
 * Date: 2017/9/25
 * Time: 8:37
 * Description: 作业调度队列的执行启动
 */
public class Application {
    private static Logger logger = Logger.getLogger(Application.class);

    /**
     * 将job填入计划调度队列，根据设置的信息周期性触发执行
     */
    public void start() {
        Config config=Config.getInstance();
        List<JobInfo> jobList=config.getJobList();
        for (int i = 0; i < jobList.size(); i++) {
            JobInfo jobInfo = jobList.get(i);
            String logTitle = "[" + config.getCode()+ "]" + jobInfo.getName() + " ";
            try {
                SchedulerFactory sf = new StdSchedulerFactory();
                Scheduler sched = sf.getScheduler();
                JobDetail job = newJob(DataTask.class).
                        withIdentity("job-" + jobInfo.getName(), config.getCode()).
                        build();
                job.getJobDataMap().
                        put("srcDb", config.getSrcDb());
                job.getJobDataMap().
                        put("interDb", config.getInterDb());
                job.getJobDataMap().
                        put("destDb", config.getDesDb());
                job.getJobDataMap().
                        put("jobInfo", jobInfo);
                job.getJobDataMap().
                        put("logTitle", logTitle);
                logger.info(jobInfo.getCron());
                //触发器设置
                CronTrigger trigger = newTrigger().
                        withIdentity("trigger-" + jobInfo.getName(), config.getCode()).
                        withSchedule(cronSchedule(jobInfo.getCron())).build();
                sched.scheduleJob(job, trigger);
                sched.start();
            } catch (Exception e) {
                logger.info(logTitle + e.getMessage());
                logger.info(logTitle + " run failed");
                continue;
            }
        }
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.start();
    }
}
