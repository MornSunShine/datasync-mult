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
 * Description: 包括配置信息的读取封装，作业调度队列的执行启动
 */
public class Application {
    private DbInfo srcDb;
    private DbInfo destDb;
    private List<JobInfo> jobList;
    private String code;
    private static Logger logger = Logger.getLogger(Application.class);

    /**
     * 读取配置文件，数据封装
     */
    public void init() {
        srcDb = new DbInfo();
        destDb = new DbInfo();
        jobList = new ArrayList<JobInfo>();
        SAXReader reader = new SAXReader();
        try {
            //获取作业配置文件的全路径
            URL url = Application.class.getResource("/jobs.xml");
            //读取xml的配置文件名，并获取其里面的节点
            //Element root = reader.read("jobs.xml").getRootElement();
            Element root = reader.read(url).getRootElement();
            Element src = root.element("source");
            Element dest = root.element("dest");
            Element jobs = root.element("jobs");
            //遍历job信息，将获取的job数据封装成job实体
            for (Iterator it = jobs.elementIterator("job"); it.hasNext(); ) {
                jobList.add((JobInfo) elementInObject((Element) it.next(), new JobInfo()));
            }
            //将获取的目标和源数据库信息，配置到DbInfo实体中
            elementInObject(src, srcDb);
            elementInObject(dest, destDb);
            code = root.element("code").getTextTrim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将配置文件数据节点下的数据封装至实体对象
     * @param e 从配置文件中获取的节点信息
     * @param o 数据待填充的实体对象
     * @return 封装完成后实体对象
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public Object elementInObject(Element e, Object o) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fields[i].set(o, e.element(fields[i].getName()).getTextTrim());
        }
        return o;
    }

    /**
     * 将job填入计划调度队列，根据设置的信息周期性触发执行
     */
    public void start() {
        for (int i = 0; i < jobList.size(); i++) {
            JobInfo jobInfo = jobList.get(i);
            String logTitle = "[" + code + "]" + jobInfo.getName() + " ";
            try {
                SchedulerFactory sf = new StdSchedulerFactory();
                Scheduler sched = sf.getScheduler();
                JobDetail job = newJob(DataTask.class).withIdentity("job-" + jobInfo.getName(), code).build();
                job.getJobDataMap().
                        put("srcDb", srcDb);
                job.getJobDataMap().
                        put("destDb", destDb);
                job.getJobDataMap().
                        put("jobInfo", jobInfo);
                job.getJobDataMap().
                        put("logTitle", logTitle);
                logger.info(jobInfo.getCron());
                //触发器设置
                CronTrigger trigger = newTrigger().
                        withIdentity("trigger-" + jobInfo.getName(), code).
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
        app.init();
        app.start();
    }
}
