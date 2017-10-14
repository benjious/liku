package com.sqlService;

import com.db.DBManager;
import com.model.User;
import com.model.UsersALL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 */
public class SqlOperator {
    public static final String NOTHING ="NOTHING";
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

    public UsersALL login_name(String username, String password) {

        UsersALL usersALL;
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
            usersALL = new UsersALL(users, users.size(),NOTHING);
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }

        manager.closeDB();
        return null;
    }


}
