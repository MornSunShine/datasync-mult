package com.maomorn.datasync;

import com.maomorn.datasync.entity.DbInfo;
import com.maomorn.datasync.entity.JobInfo;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/9/29
 * Time: 14:03
 * Description: 配置类
 * 读取jobs.xml内的配置信息，并封装成对象
 */
public class Config {
    private static class ConfigHolder{
        private static final Config INSTANCE=new Config();
    }
    private DbInfo srcDb;
    private DbInfo desDb;
    private DbInfo interDb;
    private ArrayList<JobInfo> jobList;
    private String code;

    private Config(){
        srcDb = new DbInfo();
        desDb = new DbInfo();
        interDb = new DbInfo();
        jobList = new ArrayList<JobInfo>();
        SAXReader reader = new SAXReader();
        try {
            URL url = Config.class.getResource("/jobs.xml");
            System.out.println(url.toString());
            Element root = reader.read(url).getRootElement();
            Element src = root.element("source");
            Element inter = root.element("inter");
            Element dest = root.element("dest");
            Element jobs = root.element("jobs");
            for (Iterator it = jobs.elementIterator("job"); it.hasNext(); ) {
                jobList.add((JobInfo) elementInObject((Element) it.next(), new JobInfo()));
            }

            elementInObject(src,srcDb);
            elementInObject(inter,interDb);
            elementInObject(dest,desDb);
            code=root.element("code").getTextTrim();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将配置文件数据节点下的数据封装至实体对象
     *
     * @param e 从配置文件中获取的节点信息
     * @param o 数据待填充的实体对象
     * @return 封装完成后实体对象
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private Object elementInObject(Element e, Object o) throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fields[i].set(o, e.element(fields[i].getName()).getTextTrim());
        }
        return o;
    }

    public static Config getInstance(){
        return ConfigHolder.INSTANCE;
    }

    public DbInfo getSrcDb() {
        return srcDb;
    }

    public DbInfo getDesDb() {
        return desDb;
    }

    public DbInfo getInterDb() {
        return interDb;
    }

    public ArrayList<JobInfo> getJobList() {
        return jobList;
    }

    public String getCode() {
        return code;
    }
}


