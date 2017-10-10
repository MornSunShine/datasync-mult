package com.maomorn.datasync;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Iterator;

/**
 * Author: MaoMorn
 * Date: 2017/9/30
 * Time: 14:13
 * Description:
 */
public class Test {
    public static void database(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection con=DriverManager.
                    getConnection("jdbc:sqlserver://localhost:1433;DatabaseName=test",
                            "test",
                            "test");
            PreparedStatement pst=con.
                    prepareStatement("SELECT  count(*) from student WHERE name=?");
            pst.setString(1,"0");
            ResultSet rs=pst.executeQuery();
            int i=rs.getMetaData().getColumnCount();
            rs.next();
            String temp=rs.getString(i);
            rs.close();
            pst.close();
            con.commit();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Test.database();
//        Config config=Config.getInstance();
//        System.out.println(config.getCode());
    }
}
