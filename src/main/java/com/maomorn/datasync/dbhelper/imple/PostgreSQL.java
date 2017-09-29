package com.maomorn.datasync.dbhelper.imple;

import com.maomorn.datasync.Tool;
import com.maomorn.datasync.dbhelper.DbHelper;
import com.maomorn.datasync.entity.JobInfo;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: MaoMorn
 * Date: 2017/9/25
 * Time: 8:33
 * Description: PostgreSQL数据库的细节操作，包括装配和执行两个部分
 */
public class PostgreSQL implements DbHelper {
    private Logger logger = Logger.getLogger(PostgreSQL.class);

    /**
     * 装配将要执行的SQL语句
     *
     * @param srcSql  源数据库SQL语句
     * @param conn    数据库连接
     * @param jobInfo 作业信息
     * @return 执行的SQL语句
     * @throws SQLException
     */
    public String assembleSQL(String srcSql, Connection conn, JobInfo jobInfo) throws SQLException {
        String uniqueName = Tool.generateString(6) + "_" + jobInfo.getName();
        //确定目标表的作用列
        String[] fields = jobInfo.getDestTableFields().split(",");
        //确定目标表的更新列
        String[] updateFields = jobInfo.getDestTableUpdate().split(",");
        //确定目标表
        String destTable = jobInfo.getDestTable();
        //确定目标表的判定键
        String destTableKey = jobInfo.getDestTableKey();
        //执行源数据库的表项查询，保存数据
        PreparedStatement pst = conn.prepareStatement(srcSql);
        ResultSet rs = pst.executeQuery();
        StringBuffer sql = new StringBuffer();
        //装配SQL插入语句，预装所有的结果
        sql.append("INSERT INTO").
                append(destTable).append(" (").
                append(jobInfo.getDestTableFields()).
                append(") VALUES");
        long count = 0;
        while (rs.next()) {
            sql.append("(");
            for (int i = 0; i < fields.length; i++) {
                sql.append("'").
                        append(rs.getString(fields[i])).
                        append(i == (fields.length - 1) ? "'" : "',");
            }
            sql.append("),");
            count++;
        }
        if (null != rs) {
            rs.close();
        }
        if (null != pst) {
            pst.close();
        }
        if (count > 0) {
            sql = sql.deleteCharAt(sql.length() - 1);
            if ((!jobInfo.getDestTableUpdate().equals("")) && (!jobInfo.getDestTableKey().equals(""))) {
                sql.append(" ON DUPLICATE KEY UPDATE ");
                for (int i = 0; i < updateFields.length; i++) {
                    sql.append(updateFields[i]).
                            append("= VALUES(").
                            append(updateFields[i]).
                            append(i == (updateFields.length - 1) ? ")" : ",");
                }
                return new StringBuffer("ALTER TABLE ").
                        append(destTable).
                        append(" ADD CONSTRAINT ").
                        append(uniqueName).
                        append(" UNIQUE (").
                        append(destTableKey).
                        append(");").
                        append(sql.toString()).
                        append(";ALTER TABLE ").
                        append(destTable).
                        append(" DROP INDEX ").
                        append(uniqueName).toString();
            }
            return sql.toString();
        }
        return null;
    }

    /**
     * 执行SQL命令
     * @param sql SQL命令
     * @param conn 数据库连接
     * @throws SQLException
     */
    public void executeSQL(String sql, Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("");
        String[] sqlList = sql.split(";");
        for (int i = 0; i < sqlList.length; i++) {
            pst.addBatch(sqlList[i]);
        }
        pst.executeBatch();
        conn.commit();
        pst.close();
    }
}
