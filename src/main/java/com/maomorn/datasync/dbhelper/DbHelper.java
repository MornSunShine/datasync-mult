package com.maomorn.datasync.dbhelper;

import com.maomorn.datasync.entity.JobInfo;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: MaoMorn
 * Date: 2017/9/22
 * Time: 14:50
 * Description: 数据库操作辅助类，为目标数据库和源数据库的针对性操作提供接口
 */
public interface DbHelper {
    public String assembleSQL(String paramString, Connection paramConnection, JobInfo paramJobInfo)
            throws SQLException;
    public void executeSQL(String sql, Connection conn)
            throws SQLException;
}
