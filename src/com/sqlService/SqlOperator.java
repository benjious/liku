package com.sqlService;

import com.db.DBManager;
import com.model.StockDetail;
import com.model.User;
import com.model.UsersALL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class SqlOperator {
    public static final String NOTHING = "NOTHING";

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
    public UsersALL CheckPallet(String pallet_id, int status)
    //status 为0时,查找组盘入库表白WMS_STACKING 里状态为0或者为1且托盘编号为pallet_id的任务是否存在
    //status不为0时则查找托盘号为pallet_id的托盘是否存在托盘表WMS_BA_PALLET_MAPPING 中
    {
        int signal;
        int count = 0;
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
            while (rs.next()) {
                count = count + 1;
            }
            if (count != 0) {
                signal = 1;
            } else {
                signal = 0;
            }
            usersALL = new UsersALL();
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
    public UsersALL CheckPort(String p_code)
    //status 为0时,查找组盘入库表白WMS_STACKING 里状态为0或者为1且托盘编号为pallet_id的任务是否存在
    //status不为0时则查找托盘号为pallet_id的托盘是否存在托盘表WMS_BA_PALLET_MAPPING 中
    {
        int count = 0;
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
            while (rs.next()) {
                count = count + 1;
            }
            if (count != 0) {
                signal = true;
            } else {
                signal = false;
            }
            usersALL = new UsersALL();
            usersALL.setYesNo(signal);
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;
    }


    public UsersALL GetNewStack_id(String strKind) {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        String str = simpleDateFormat.format(date);
        String stack_id = str + "000";
        String str1 = "";
        String commandString = "select MAX(STACK_ID) as stack_id from WMS_STACKING where STACK_ID like '" + strKind + "%'";
        System.out.println("  xyz 登录的语句为：" + commandString);

        //初始化连接数据库对象
        DBManager manager = DBManager.createInstance();
        manager.connectDB_cursor_sroll();
        //使用对象进行查询

        int count = 0;
        try {
            ResultSet rs = manager.executeQuery(commandString);
            while (rs.next()) {
                count = count + 1;
            }
            for (int i = 1; i <= count; i++) {
                rs.absolute(i);
                str1 = rs.getString("STACK_ID").substring(3);
            }
            if (stack_id.compareTo(str1) <= 0) {
                String str2 = str1.substring(6);
                int k = Integer.decode(str2) + 1;
                stack_id = strKind + str + String.format("{0,d3}", k);
            } else {
                stack_id = strKind + stack_id;
            }
            UsersALL usersALL = new UsersALL();
            usersALL.setData(stack_id);
            System.out.println("是不是执行到这里");
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;


    }

    public UsersALL GetStockDetail(int stock_oid) {
        List<StockDetail> stockDetails = new ArrayList<>();
        String commString = "select * from WMS_STOCK_DETAIL where STOCK_OID = '" + stock_oid + "'";
        System.out.println("查询语句为： "+commString);
        //初始化连接数据库对象
        DBManager manager = DBManager.createInstance();
        manager.connectDB_cursor_sroll();
        //使用对象进行查询

        int count = 0;
        try {
            ResultSet rs = manager.executeQuery(commString);
            while (rs.next()) {
                count = count + 1;
            }
            if (count != 0) {
                for (int i = 1; i <= count; i++) {
                    rs.absolute(i);
                    StockDetail sd = new StockDetail();
                    sd.set_oID(Integer.decode(rs.getString("OID")));
                    sd.set_sTOCK_OID(Integer.decode(rs.getString("STOCK_OID")));
                    sd.set_iTEM_ID(rs.getString("ITEM_ID"));
                    sd.set_oUT_QTY(rs.getDouble("OUT_QTY"));
                    stockDetails.add(sd);
                }
            }

            UsersALL usersALL = new UsersALL();
            usersALL.setStockDetails(stockDetails);
            System.out.println("是不是执行到这里");
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;


    }


}
