package com.maomorn.datasync.dbhelper.imple;

import com.maomorn.datasync.dbhelper.DbHelper;
import com.maomorn.datasync.entity.JobInfo;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * Author: MaoMorn
 * Date: 2017/9/25
 * Time: 8:31
 * Description: SQLServer数据库的执行细节，包括SQL命令的装配和执行
 */
public class SQLServer implements DbHelper {
    private Logger logger = Logger.getLogger(SQLServer.class);

    /**
     * 装配SQL命令
     * @param srcSql 源数据库SQL查询命令
     * @param conn 数据库连接
     * @param jobInfo 作业数据实例
     * @return 装配得到的SQL命令
     * @throws SQLException
     */
    public String assembleSQL(String srcSql, Connection conn, JobInfo jobInfo) throws SQLException {
        //获取目标表单作用列
        String fieldStr = jobInfo.getDestTableFields();
        String[] fields = fieldStr.split(",");
        //获取更新列
        String[] updateFields = jobInfo.getDestTableUpdate().split(",");
        //获取目标表单键
        String destTableKey = jobInfo.getDestTableKey();
        //获取目标表单
        String destTable = jobInfo.getDestTable();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(srcSql);
        StringBuffer sql = new StringBuffer();
        long count = 0;
        //SQL命令装配
        while (rs.next()) {
            sql.append("IF NOT EXISTS (SELECT ").
                    append(destTableKey).
                    append(" FROM ").
                    append(destTable).
                    append(" WHERE ").
                    append(destTableKey).
                    append("='").
                    append(rs.getString(destTableKey)).
                    append("')").
                    append("INSERT INTO ").
                    append(destTable).
                    append("(").
                    append(fieldStr).
                    append(") VALUES(");
            for (int i = 0; i < fields.length; i++) {
                sql.append("'").
                        append(rs.getString(fields[i])).
                        append(i == (fields.length - 1) ? "'" : "',");
            }
            sql.append(") ELSE UPDATE ").
                    append(destTable).
                    append(" SET ");
            for (int i = 0; i < updateFields.length; i++) {
                sql.append(updateFields[i]).
                        append("='").
                        append(rs.getString(updateFields[i])).
                        append(i == (updateFields.length - 1) ? "'" : "',");
            }
            sql.append(" where ").
                    append(destTableKey).
                    append("='").
                    append(rs.getString(destTableKey)).
                    append("';");
            count++;
            // this.logger.info("第" + count + "耗时: " + (new Date().getTime() - oneStart) + "ms");
        }
        this.logger.info("总共查询到 " + count + " 条记录");
        if (rs != null) {
            rs.close();
        }
        if (stat != null) {
            stat.close();
        }
        return count > 0 ? sql.toString() : null;
    }

    /**
     * 执行SQL命令
     * @param sql SQL命令
     * @param conn 数据库连接
     * @throws SQLException
     */
    public void executeSQL(String sql, Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.executeUpdate();
        conn.commit();
        pst.close();
    }
}
