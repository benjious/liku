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
            usersALL = new UsersALL();
            usersALL.setUsers(users);
            return usersALL;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;
    }

    //返回int
    public  UsersALL CheckPallet(String pallet_id, int status)
    //status 为0时,查找组盘入库表白WMS_STACKING 里状态为0或者为1且托盘编号为pallet_id的任务是否存在
    //status不为0时则查找托盘号为pallet_id的托盘是否存在托盘表WMS_BA_PALLET_MAPPING 中
    {
        ArrayList<User> users = new ArrayList<>();
        int signal;
        String commandString;
        UsersALL usersALL;

        if (status == 0)
            commandString = "select * from WMS_STACKING where PALLET_ID = '" + pallet_id + "'and STATUS = 0 or PALLET_ID = '" + pallet_id + "'and STATUS = 1";
        else
            commandString = "select * from WMS_BA_PALLET_MAPPING where PALLET_ID = '" + pallet_id + "'";

        System.out.println("  xyz 登录的语句为：" + commandString);
        //初始化连接数据库对象
        DBManager manager = DBManager.createInstance();
        manager.connectDB();
        //使用对象进行查询
        try {
            ResultSet rs = manager.executeQuery(commandString);
            if (rs!=null && rs.getRow()!=0) {
                signal =1;
            }else {
                signal=-1;
            }
            usersALL = new UsersALL();
            usersALL.setUsers(users);
            usersALL.setNumber(signal);
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;
    }


    //返回int
    public  UsersALL CheckPort(String p_code)
    //status 为0时,查找组盘入库表白WMS_STACKING 里状态为0或者为1且托盘编号为pallet_id的任务是否存在
    //status不为0时则查找托盘号为pallet_id的托盘是否存在托盘表WMS_BA_PALLET_MAPPING 中
    {
        ArrayList<User> users = new ArrayList<>();
        boolean signal;
        UsersALL usersALL;
        String commandString = "select * from WMS_BA_PORTS where P_CODE = '" + p_code + "'";
        System.out.println("  xyz 登录的语句为：" + commandString);
        //初始化连接数据库对象
        DBManager manager = DBManager.createInstance();
        manager.connectDB();
        //使用对象进行查询
        try {
            ResultSet rs = manager.executeQuery(commandString);
            if (rs!=null && rs.getRow()!=0) {
                signal=true;
            }else {
                signal=false;
            }
            usersALL = new UsersALL();
            usersALL.setUsers(users);
            usersALL.setYesNo(signal);
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;
    }


}
