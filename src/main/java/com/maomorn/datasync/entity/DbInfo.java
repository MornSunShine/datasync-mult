package com.maomorn.datasync.entity;

/**
 * Author: MaoMorn
 * Date: 2017/9/22
 * Time: 16:08
 * Description: 数据库的实体类，保存数据库相关的属性
 */
public class DbInfo {
    String url;
    String username;
    String dbname;
    String password;
    String dbtype;
    String driver;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
