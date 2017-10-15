package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    //数据库连接常量
//    public static final String DRIVER = "com.mysql.jdbc.Driver";
//    public static final String USER = "root";
//    public static final String PASS = "12345678";
//    public static final String MYSQL_URL = "jdbc:mysql://localhost:3306/liti";

    public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String USER = "sa";
    public static final String PASS = "12345678";
    public static final String MYSQL_URL = "jdbc:sqlserver://localhost:1433;DatabaseName=ASRS";

    //静态成员，支持单态模式
    private static DBManager per = null;
    private Connection mConnection = null;
    private Statement mStatement = null;

    //单态模式-懒汉式
    private DBManager() {

    }

    public static DBManager createInstance() {
        if (per == null) {
            per = new DBManager();
            per.initDB();
        }
        return per;
    }


    //加载驱动
    private void initDB() {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //连接数据库，获取句柄和对象
    public void connectDB() {
        System.out.println("Connecting ot database........");
        try {

            mConnection = DriverManager.getConnection(MYSQL_URL, USER, PASS);
            mStatement = mConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("SqlManager: Connect to database successful.");
    }

    //关闭数据库，关闭对象，释放句柄
    public void closeDB() {
        System.out.println("Close connection to database....");
        try {

            mStatement.close();
            mConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Close connection successful ");
    }

    //查询
    public ResultSet executeQuery(String sql) {
        ResultSet re = null;
        try {
            re = mStatement.executeQuery(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return re;
    }

    //增删/删除/修改
    public int executeUpdate(String sql) {
        int ret = 0;
        try {

            ret = mStatement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
