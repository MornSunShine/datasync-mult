package com.maomorn.datasync.dbhelper;

import com.maomorn.datasync.entity.JobInfo;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: MaoMorn
 * Date: 2017/9/22
 * Time: 14:50
 * Description: 数据库操作辅助类，为目标数据库的针对性操作提供接口
 */
public interface DbHelper {
    /**
     * 装配同步SQL语句
     * @param srcSQL 源数据库查询语句
     * @param srcConn 源数据库连接
     * @param jobInfo 目标作业
     * @return 装配得到的同步SQL语句
     * @throws SQLException
     */
    public String assembleSQL(String srcSQL, Connection srcConn, JobInfo jobInfo)
            throws SQLException;

    /**
     * 执行同步语句
     * @param sql 装配得到的SQL同步命令
     * @param desConn 目标数据库连接
     * @throws SQLException
     */
    public void executeSQL(String sql, Connection desConn)
            throws SQLException;
}
