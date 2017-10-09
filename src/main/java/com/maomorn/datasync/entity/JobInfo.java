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
    String destTable;
    String destTableFields;
    String destTableUpdate;
    String destTableUpdateValues;
    String destTableKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getSrcSql() {
        return srcSql;
    }

    public void setSrcSql(String srcSql) {
        this.srcSql = srcSql;
    }

    public String getInterTable() {
        return interTable;
    }

    public void setInterTable(String interTable) {
        this.interTable = interTable;
    }

    public String getInterTableFields() {
        return interTableFields;
    }

    public void setInterTableFields(String interTableFields) {
        this.interTableFields = interTableFields;
    }

    public String getInterTableKeys() {
        return interTableKeys;
    }

    public void setInterTableKeys(String interTableKeys) {
        this.interTableKeys = interTableKeys;
    }

    public String getDestTable() {
        return destTable;
    }

    public void setDestTable(String destTable) {
        this.destTable = destTable;
    }

    public String getDestTableFields() {
        return destTableFields;
    }

    public void setDestTableFields(String destTableFields) {
        this.destTableFields = destTableFields;
    }

    public String getDestTableUpdate() {
        return destTableUpdate;
    }

    public void setDestTableUpdate(String destTableUpdate) {
        this.destTableUpdate = destTableUpdate;
    }

    public String getDestTableUpdateValues() {
        return destTableUpdateValues;
    }

    public void setDestTableUpdateValues(String destTableUpdateValues) {
        this.destTableUpdateValues = destTableUpdateValues;
    }

    public String getDestTableKey() {
        return destTableKey;
    }

    public void setDestTableKey(String destTableKey) {
        this.destTableKey = destTableKey;
    }
}
