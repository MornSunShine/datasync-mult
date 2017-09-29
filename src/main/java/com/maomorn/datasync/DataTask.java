package com.maomorn.datasync;

import com.maomorn.datasync.dbhelper.DbHelper;
import com.maomorn.datasync.entity.DbInfo;
import com.maomorn.datasync.entity.JobInfo;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

/**
 * Author: MaoMorn
 * Date: 2017/9/25
 * Time: 8:38
 * Description: 作业的细节操作，包括数据库连接断开
 */
public class DataTask implements Job {
    private Logger logger = Logger.getLogger(DataTask.class);

    /**
     * 作业的核心执行细节
     * @param context 作业执行的环境
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        this.logger.info("开始任务调度: " + new Date());
        Connection inConn = null;
        Connection outConn = null;
        //作业信息获取
        JobDataMap data = context.getJobDetail().getJobDataMap();
        DbInfo srcDb = (DbInfo) data.get("srcDb");
        DbInfo destDb = (DbInfo) data.get("destDb");
        JobInfo jobInfo = (JobInfo) data.get("jobInfo");
        String logTitle = (String) data.get("logTitle");
        //数据库连接
        try {
            inConn = createConnection(srcDb);
            outConn = createConnection(destDb);
            if (inConn == null) {
                this.logger.error("请检查源数据连接!");
                return;
            } else if (outConn == null) {
                this.logger.error("请检查目标数据连接!");
                return;
            }
            //创建个性化的数据库执行实体对象，组装并执行
            try {
                DbHelper dbHelper = null;
                dbHelper = (DbHelper) Class.forName("com.maomorn.datasync.dbhelper.impl." + destDb.getDbtype()).newInstance();
                long start = new Date().getTime();
                String sql = dbHelper.assembleSQL(jobInfo.getSrcSql(), inConn, jobInfo);
                this.logger.info("组装SQL耗时: " + (new Date().getTime() - start) + "ms");
                if (sql != null) {
                    this.logger.debug(sql);
                    long eStart = new Date().getTime();
                    dbHelper.executeSQL(sql, outConn);
                    this.logger.info("执行SQL语句耗时: " + (new Date().getTime() - eStart) + "ms");
                }
            } catch (ClassNotFoundException e) {
                this.logger.error(logTitle + e.getMessage());
                this.logger.error(logTitle + "请检查类路径是否配置正确或者存在");
            } catch (InstantiationException e) {
                this.logger.error(logTitle + e.getMessage());
                this.logger.error(logTitle + "配置的类对象不能正常实例化");
            } catch (IllegalAccessException e) {
                this.logger.error(logTitle + e.getMessage());
                this.logger.error(logTitle + "请检查是否有执行该方法的权限");
            }
        } catch (SQLException e) {
            this.logger.error(logTitle + e.getMessage());
            this.logger.error(logTitle + " SQL执行出错，请检查是否存在语法错误");
        } finally {
            this.logger.info("关闭源数据库连接");
            destoryConnection(inConn);
            this.logger.info("关闭目标数据库连接");
            destoryConnection(outConn);
        }

    }

    /**
     * 根据实体中的信息创建数据库连接
     *
     * @param db 待连接数据库实体信息
     * @return 创建的数据库连接，成功返回连接实例，否则返回 null
     */
    private Connection createConnection(DbInfo db) {
        try {
            Class.forName(db.getDriver());
            Connection conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            conn.setAutoCommit(false);
            return conn;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 断开与数据库之间的连接
     *
     * @param conn 与数据库的连接
     */
    private void destoryConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
                this.logger.error("数据库连接关闭");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}