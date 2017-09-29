package com.maomorn.datasync;

import java.sql.*;

/**
 * Author: MaoMorn
 * Date: 2017/9/25
 * Time: 9:38
 * Description:
 */
public class Test {
    public static void main(String[] args) {
        String url = "jdbc:sqlserver://localhost:1433;DatabaseName=test";
        String user = "test";
        String password = "test";
        String sql = "select * from student";
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.println("name " + resultSet.getString(1));
            }
            connection.close();
            statement.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
