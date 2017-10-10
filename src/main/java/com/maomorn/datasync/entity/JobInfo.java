package com.maomorn.datasync.entity;

/**
 * Author: MaoMorn
 * Date: 2017/9/22
 * Time: 16:08
 * Description: 作业实体类，存储从jobs.xml中解析的作业配置信息
 */
public class JobInfo {
    String name;
    String cron;
    String srcSql;
    String interTable;
    String interTableFields;
    String interTableKeys;
    String interTableUpdate;
    String destTable;
    String destTableFields;
    String destTableUpdate;
    String destTableUpdateValues;
    String destTableKeys;

    public String getName() {
        return name;
    }

    public String getCron() {
        return cron;
    }

    public String getSrcSql() {
        return srcSql;
    }

    public String getInterTable() {
        return interTable;
    }

    public String getInterTableFields() {
        return interTableFields;
    }

    public String getInterTableKeys() {
        return interTableKeys;
    }

    public String getInterTableUpdate() {
        return interTableUpdate;
    }

    public String getDestTable() {
        return destTable;
    }

    public String getDestTableFields() {
        return destTableFields;
    }

    public String getDestTableUpdate() {
        return destTableUpdate;
    }

    public String getDestTableUpdateValues() {
        return destTableUpdateValues;
    }

    public String getDestTableKeys() {
        return destTableKeys;
    }
}
