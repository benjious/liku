package com.sqlService;

import com.db.DBManager;
import com.model.User;
import com.model.UserAll;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SqlOperator {
    public Boolean login(String username, String password) {

        //获取sql查询语句
        String logSql = "select * from student where username ='" + username + "' and password ='" + password + "'";
        System.out.println("  xyz 登录的语句为：" + logSql);
        //初始化连接数据库对象
        DBManager manager = DBManager.createInstance();
        manager.connectDB();
        //使用对象进行查询
        try {

            ResultSet rs = manager.executeQuery(logSql);
            if (rs.next()) {
                manager.closeDB();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        manager.closeDB();
        return false;
    }

    public UserAll login_name(String username, String password) {

        UserAll userAll;
        ArrayList<User> users = new ArrayList<>();

        //获取sql查询语句
        String logSql = "select * from student where username ='" + username + "' and password ='" + password + "'";
        System.out.println("  xyz 登录的语句为：" + logSql);
        //初始化连接数据库对象
        DBManager manager = DBManager.createInstance();
        manager.connectDB();
        //使用对象进行查询
        try {

            ResultSet rs = manager.executeQuery(logSql);
            while (rs.next()) {
                String name = rs.getString("username");
                System.out.println("数据库的结果是：" + name + " ");
                users.add(new User(name, null));
            }
            userAll = new UserAll(users, users.size());
            return userAll;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }

        manager.closeDB();
        return null;
    }


}
