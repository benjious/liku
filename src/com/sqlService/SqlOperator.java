package com.sqlService;

import com.db.DBManager;
import com.model.StockDetail;
import com.model.User;
import com.model.UsersALL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 游标查询是从第一行开始的，
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
    public UsersALL checkPallet(String pallet_id, int status)
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
    public UsersALL checkPort(String p_code)
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

    //返回int
    public UsersALL checkProNo(String pro_no)
    //status 为0时,查找组盘入库表白WMS_STACKING 里状态为0或者为1且托盘编号为pallet_id的任务是否存在
    //status不为0时则查找托盘号为pallet_id的托盘是否存在托盘表WMS_BA_PALLET_MAPPING 中
    {
        int count = 0;
        String str_name = "";
        UsersALL usersALL;
        String commandString = "select PRODUCT_NAME from WMS_BA_PRODUCT where PRODUCT_ID= '" + pro_no + "'";
        System.out.println("  xyz 登录的语句为：" + commandString);
        //初始化连接数据库对象
        DBManager manager = DBManager.createInstance();
        manager.connectDB_cursor_sroll();

        //使用对象进行查询
        try {
            ResultSet rs = manager.executeQuery(commandString);
            while (rs.next()) {
                count = count + 1;
                System.out.println("count : " + count);
            }
            if (count != 0) {
                for (int i = 1; i <= count; i++) {
                    rs.absolute(i);
                    str_name = rs.getString("PRODUCT_NAME");
                }
            }
            usersALL = new UsersALL();
            usersALL.setData(str_name);
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;
    }


    public UsersALL getNewStackId(String strKind) {

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
        System.out.println("查询语句为： " + commString);
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
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;

    }

    //insert into WMS_STACKING (STACK_ID,WH_NO,PALLET_ID,P_CODE,KIND,BIN_NO,FULL_FLAG,STATUS,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY)
// values('SIE090117100','JL_ASRS_01','P0002',0001,0,'00100507',0,0,'2009-01-17 04:57:16.000','1011','2009-01-17 04:57:16.000',1011);
    public UsersALL insertStack(String stack_id, String wh_no, String pallet_id, String p_code, String kind, String bin_no, String full_flag, String status, String creation_date, String created_by, String last_update_date, String last_updated_by) {
        String commString =
                "insert into WMS_STACKING (STACK_ID,WH_NO,PALLET_ID,P_CODE,KIND,BIN_NO,FULL_FLAG,STATUS,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY) values('" + stack_id + "','" + wh_no + "','" + pallet_id + "'," + p_code + "," + kind + "," + bin_no + ",'" + full_flag + "'," + status + ",'" + creation_date + "'," + created_by + ",'" + last_update_date + "'," + last_updated_by + ")";
        System.out.println("查询语句为： " + commString);
        //初始化连接数据库对象
        DBManager manager = DBManager.createInstance();
        manager.connectDB();
        //使用对象进行查询
        UsersALL usersALL = insertProccess(commString, manager);
        if (usersALL != null) return usersALL;
        return null;
    }

    private UsersALL insertProccess(String commString, DBManager manager) {
        try {
            int sign = manager.executeUpdate(commString);
            UsersALL usersALL = new UsersALL();
            usersALL.setNumber(sign);
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;
    }

    // "insert into WMS_STACKING_ITEM (ITEM_ID,STACK_ID,LIST_NO,QTY,PROD_DATE,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY)
    // values(@item_id,@stack_id,@list_no,@qty,@prod_date,@creation_date,@created_by,@last_update_date,@last_updated_by)";
    public UsersALL insertStackItem(String item_id, String stack_id, String list_no, String qty, String prod_date, String creation_date, String createdBy, String lastUpdateDate, String lastUpdatedBy) {
        String sqlcommand =
                MessageFormat.format("insert into WMS_STACKING_ITEM (ITEM_ID,STACK_ID,LIST_NO,QTY,PROD_DATE,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY) values('%s','%s','%s',%s,'%s','%s',%s,'%s',%s)", item_id, stack_id, list_no, qty, null, creation_date, createdBy, lastUpdateDate, lastUpdatedBy);
        System.out.println("sql语句为: " + sqlcommand);

        DBManager manager = DBManager.createInstance();
        manager.connectDB();
        //使用对象进行查询
        UsersALL usersALL = insertProccess(sqlcommand, manager);
        return usersALL;
    }

    public UsersALL updatePallet(String lastUpdateDate, String lastUpdatedBy, String palletId) {
        String sqlcommand = MessageFormat.format("update WMS_BA_PALLET_MAPPING set LOCK_FLAG = 1,LAST_UPDATE_DATE = '%s', LAST_UPDATED_BY ='%s' where PALLET_ID = '%s'", lastUpdateDate, lastUpdatedBy, palletId);

        System.out.println("sql语句为: " + sqlcommand);

        DBManager manager = DBManager.createInstance();
        manager.connectDB();
        //使用对象进行查询
        UsersALL usersALL = insertProccess(sqlcommand, manager);
        return usersALL;
    }


    public UsersALL updateStock(String lastUpdateBy, String stockOid, String fullFlag) {
        String sqlcommand = String.format("update WMS_STOCK set LAST_UPDATE_DATE ='%S', LAST_UPDATED_BY = '%s', FULL_FLAG = '%s' where STOCK_OID = %s",new Date().toString(),lastUpdateBy,fullFlag,stockOid);
        System.out.println("sql语句为: " + sqlcommand);
        DBManager manager = DBManager.createInstance();
        manager.connectDB();
        //使用对象进行查询
        UsersALL usersALL = insertProccess(sqlcommand, manager);
        return usersALL;

    }
}
