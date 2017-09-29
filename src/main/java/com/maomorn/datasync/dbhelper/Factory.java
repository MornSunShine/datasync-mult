package com.maomorn.datasync.dbhelper;

import com.maomorn.datasync.dbhelper.imple.PostgreSQL;
import com.maomorn.datasync.dbhelper.imple.SQLServer;

/**
 * Author: MaoMorn
 * Date: 2017/9/25
 * Time: 8:34
 * Description:
 */
public class Factory {
    public static DbHelper create(String type) {
        if (type.toLowerCase().equals("sqlserver")) {
            return new SQLServer();
        } else if (type.toLowerCase().equals("postgresql")) {
            return new PostgreSQL();
        }
        return null;
    }
}
