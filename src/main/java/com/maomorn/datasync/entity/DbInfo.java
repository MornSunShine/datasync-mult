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

    public String getUsername() {
        return username;
    }

    public String getDbname() {
        return dbname;
    }

    public String getPassword() {
        return password;
    }

    public String getDbtype() {
        return dbtype;
    }

    public String getDriver() {
        return driver;
    }
}
