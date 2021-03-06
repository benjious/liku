package com.sqlService;

import com.db.DBManager;
import com.model.Binsta;
import com.model.Inventory;
import com.model.Picking;
import com.model.StockDetail;
import com.model.User;
import com.model.UsersALL;
import com.util.ConvertTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    private UsersALL mBinsta;

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

//        //初始化连接数据库对象
//        DBManager manager = DBManager.createInstance();
//        manager.connectDB_cursor_sroll();
//        //使用对象进行查询
//
//        int count = 0;
//        try {
//            ResultSet rs = manager.executeQuery(commandString);
//            while (rs.next()) {
//                count = count + 1;
//            }
//            for (int i = 1; i <= count; i++) {
//                rs.absolute(i);
//                str1 = rs.getString("STACK_ID").substring(3);
//            }
//            if (stack_id.compareTo(str1) <= 0) {
//                String str2 = str1.substring(6);
//                stack_id = strKind + str + autoGenericCode(str2, 3);
//                System.out.println("看一下是什么 "+stack_id);
//            } else {
//                stack_id = strKind + stack_id;
//            }
//            UsersALL usersALL = new UsersALL();
//            usersALL.setData(stack_id);
//            return usersALL;
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("这里出错？");
//        }
//        manager.closeDB();
//        return null;


        UsersALL usersALL;
        ArrayList<User> users = new ArrayList<>();
        //初始化连接数据库对象
        DBManager manager = DBManager.createInstance();
        manager.connectDB_cursor_sroll();
        //使用对象进行查询

        int count = 0;
        //获取sql查询语句
        String logSql = "select * from sys_User where U_LoginName='" + username + "' and U_Password='"+password+"'";
        System.out.println("  xyz 登录的语句为：" + logSql);
        //初始化连接数据库对象

        //使用对象进行查询
        try {
            ResultSet rs = manager.executeQuery(logSql);
            while (rs.next()) {
                count = count + 1;
            }
            for (int i = 1; i <= count; i++) {
                rs.absolute(i);
                User user = new User();
                user.set_userID(Integer.parseInt(rs.getString("UserID")));
                user.set_roleName(rs.getString("U_LoginName"));
                user.set_userCnName(rs.getString("U_CName"));
                users.add(user);
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
            if (rs!=null) {
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
            }
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
    public UsersALL checkProNo(String pro_no) {
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
                stack_id = strKind + str + autoGenericCode(str2, 3);
                System.out.println("看一下是什么 "+stack_id);
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

    private String autoGenericCode(String code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d", Integer.parseInt(code) + 1);
        return result;
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
    public UsersALL insertStacking(String stack_id, String wh_no, String pallet_id, String p_code, String kind, String bin_no, String full_flag, String status, String creation_date, String created_by, String last_update_date, String last_updated_by) {
        DBManager manager = DBManager.createInstance();
        try {
            String str = "insert into WMS_STACKING (STACK_ID,WH_NO,PALLET_ID,P_CODE,KIND,BIN_NO,FULL_FLAG,STATUS,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY)VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            System.out.println(stack_id + "," + wh_no + "," + pallet_id + "," + p_code + "," + kind + "," + bin_no + "," + full_flag + "," + status + "," + creation_date + "," + created_by + "," + last_update_date + "," + last_updated_by);
            PreparedStatement preparedStatement = manager.getConnection().prepareStatement(str);
            preparedStatement.setString(1, stack_id);
            preparedStatement.setString(2, wh_no);
            preparedStatement.setString(3, pallet_id);
            preparedStatement.setString(4, p_code);
            preparedStatement.setInt(5, Integer.decode(kind));
            preparedStatement.setString(6, bin_no);
            preparedStatement.setInt(7, Integer.decode(full_flag));
            preparedStatement.setInt(8,Integer.parseInt(status));
            preparedStatement.setTimestamp(9, ConvertTime.convertToTimestamp(creation_date));
            preparedStatement.setString(10, created_by);
            preparedStatement.setTimestamp(11, ConvertTime.convertToTimestamp(last_update_date));
            preparedStatement.setInt(12,Integer.decode(last_updated_by));
            int updatefinish = preparedStatement.executeUpdate();
            UsersALL usersALL = new UsersALL();
            usersALL.setNumber(updatefinish);
            return usersALL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        manager.closeDB_PRE();
        return null;


    }


    // "insert into WMS_STACKING_ITEM (ITEM_ID,STACK_ID,LIST_NO,QTY,PROD_DATE,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY)
    // values(@item_id,@stack_id,@list_no,@qty,@prod_date,@creation_date,@created_by,@last_update_date,@last_updated_by)";
    public UsersALL insertStackItem(String item_id, String stack_id, String list_no, String qty, String prod_date, String creation_date, String createdBy, String lastUpdateDate, String lastUpdatedBy) {

        DBManager manager = DBManager.createInstance();
        try {
            PreparedStatement preparedStatement = manager.getConnection().prepareStatement("INSERT INTO WMS_STACKING_ITEM (ITEM_ID,STACK_ID,LIST_NO,QTY,PROD_DATE,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY) VALUES(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, item_id);
            preparedStatement.setString(2, stack_id);
            preparedStatement.setString(3, list_no);
            preparedStatement.setInt(4, Integer.decode(qty));
            preparedStatement.setTimestamp(5, ConvertTime.convertToTimestamp(prod_date));
            preparedStatement.setTimestamp(6, ConvertTime.convertToTimestamp(creation_date));
            preparedStatement.setString(7, createdBy);
            preparedStatement.setTimestamp(8, ConvertTime.convertToTimestamp(lastUpdateDate));
            preparedStatement.setInt(9, Integer.decode(lastUpdatedBy));
            preparedStatement.executeUpdate();
            int updatefinish = preparedStatement.executeUpdate();
            UsersALL usersALL = new UsersALL();
            usersALL.setNumber(updatefinish);
            return usersALL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        manager.closeDB_PRE();
        return null;

    }

    public UsersALL updatePallet(String lastUpdateDate, String lastUpdatedBy, String palletId) {

        DBManager manager = DBManager.createInstance();
        try {
            PreparedStatement preparedStatement = manager.getConnection().prepareStatement("UPDATE WMS_BA_PALLET_MAPPING SET LOCK_FLAG = 1,LAST_UPDATE_DATE =?, LAST_UPDATED_BY =? WHERE PALLET_ID =?");
            preparedStatement.setTimestamp(1, ConvertTime.convertToTimestamp(lastUpdateDate));
            preparedStatement.setInt(2, Integer.decode(lastUpdatedBy));
            preparedStatement.setString(3, palletId);
            int updatefinish = preparedStatement.executeUpdate();
            UsersALL usersALL = new UsersALL();
            usersALL.setNumber(updatefinish);
            return usersALL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        manager.closeDB_PRE();
        return null;
    }


    public UsersALL updateStock(String lastUpdateBy, String stockOid, String fullFlag) {
        DBManager manager = DBManager.createInstance();
        try {
            PreparedStatement preparedStatement = manager.getConnection().prepareStatement("UPDATE WMS_STOCK SET LAST_UPDATE_DATE =?, LAST_UPDATED_BY =?, FULL_FLAG =? WHERE STOCK_OID =?");
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, Integer.decode(lastUpdateBy));
            preparedStatement.setInt(3, Integer.decode(fullFlag));
            preparedStatement.setInt(4, Integer.decode(stockOid));
            int updatefinish = preparedStatement.executeUpdate();
            UsersALL usersALL = new UsersALL();
            usersALL.setNumber(updatefinish);
            return usersALL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        manager.closeDB_PRE();
        return null;
    }


    public UsersALL insertStockDetail(String stock_oid, String item_id, String prod_date, String expire_date, String batch_no, String qty, String out_qty, String stock_qty, String creation_date, String created_by, String last_update_date, String last_updated_by) {

        DBManager manager = DBManager.createInstance();
        try {
            PreparedStatement preparedStatement = manager.getConnection().prepareStatement("INSERT INTO WMS_STOCK_DETAIL (STOCK_OID, ITEM_ID, PROD_DATE, EXPIRE_DATE, BATCH_NO,  QTY,  OUT_QTY, STOCK_QTY, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATED_BY) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1,Integer.decode(stock_oid));
            preparedStatement.setString(2,item_id);
            preparedStatement.setTimestamp(3,ConvertTime.convertToTimestamp(prod_date));
            preparedStatement.setTimestamp(4,ConvertTime.convertToTimestamp(expire_date));
            preparedStatement.setString(5, batch_no);
            preparedStatement.setInt(6, Integer.parseInt(qty));
            preparedStatement.setInt(7, Integer.parseInt(out_qty));
            preparedStatement.setInt(8, Integer.parseInt(stock_qty));
            preparedStatement.setTimestamp(9, ConvertTime.convertToTimestamp(creation_date));
            preparedStatement.setString(10, created_by);
            preparedStatement.setTimestamp(11, ConvertTime.convertToTimestamp(last_update_date));
            preparedStatement.setString(12, last_updated_by);
            preparedStatement.executeUpdate();
            int updatefinish = preparedStatement.executeUpdate();
            UsersALL usersALL = new UsersALL();
            usersALL.setNumber(updatefinish);
            System.out.println("打印结果" + updatefinish);
            return usersALL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        manager.closeDB_PRE();
        return null;
    }

    public UsersALL getInventorys(String pallet_id) {
        List<Inventory> inventories = new ArrayList<>();
        String commString = "select WMS_BA_TRK.PALLET_ID,PRODUCT_ID,PRODUCT_NAME,OLD_STOCK_QTY,WMS_INVENTORY_LIST.CHECK_LIST_NO as CHECK_LIST_NO,WMS_INVENTORY_LIST_DETAIL.OID as OID from WMS_STOCK_DETAIL ,WMS_BA_PRODUCT,WMS_INVENTORY_LIST,WMS_BA_TRK,WMS_INVENTORY_LIST_DETAIL where WMS_INVENTORY_LIST_DETAIL.STOCK_OID = WMS_BA_TRK.STOCK_OID and WMS_INVENTORY_LIST_DETAIL.CHECK_LIST_NO = WMS_INVENTORY_LIST.CHECK_LIST_NO and WMS_STOCK_DETAIL.OID = WMS_INVENTORY_LIST_DETAIL.STOCK_DETAIL_OID and WMS_BA_PRODUCT.PRODUCT_ID = WMS_STOCK_DETAIL.ITEM_ID and OPN = 8 and WMS_INVENTORY_LIST_DETAIL.STATUS = 2 and WMS_BA_TRK.PALLET_ID = '" + pallet_id + "'";
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
                    Inventory sd = new Inventory();
                    sd.set_pALLET_ID(rs.getString("PALLET_ID"));
                    sd.set_pRODUCT_ID(rs.getString("PRODUCT_ID"));
                    sd.set_pRODUCT_NAME(rs.getString("PRODUCT_NAME"));
                    sd.set_oLD_STOCK_QTY(rs.getDouble("OLD_STOCK_QTY"));
                    sd.set_cHECK_LIST_NO(rs.getString("CHECK_LIST_NO"));
                    sd.set_oID(rs.getInt("OID"));
                    inventories.add(sd);
                }
            }

            UsersALL usersALL = new UsersALL();
            usersALL.setInventories(inventories);
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;
    }

    public UsersALL updateInventorys(String last_updated_by, String check_list_no, String oid, String qty) {
        DBManager manager = DBManager.createInstance();
        try {
            PreparedStatement preparedStatement = manager.getConnection().prepareStatement("update WMS_INVENTORY_LIST set LAST_UPDATE_DATE =?, LAST_UPDATED_BY =? where CHECK_LIST_NO =?");
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, Integer.decode(last_updated_by));
            preparedStatement.setInt(3, Integer.decode(check_list_no));
            int updatefinish = preparedStatement.executeUpdate();
            UsersALL usersALL = new UsersALL();
            if (updatefinish > 0) {
                PreparedStatement nextPreStatement = manager.getConnection().prepareStatement("UPDATE WMS_INVENTORY_LIST_DETAIL SET NEW_STOCK_QTY = @qty WHERE OID = ?");
                preparedStatement.setInt(1, Integer.decode(oid));
                int nextupdate = nextPreStatement.executeUpdate();
                if (nextupdate > 0) {
                    usersALL.setYesNo(true);
                    System.out.println("打印结果" + updatefinish);
                    return usersALL;
                } else {
                    usersALL.setYesNo(false);
                    System.out.println("打印结果" + updatefinish);
                    return usersALL;
                }
            }else {
                usersALL.setYesNo(false);
                System.out.println("打印结果" + updatefinish);
                return usersALL;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        manager.closeDB_PRE();
        return null;

    }

    public UsersALL getPickings(String p_code, String pallet_id) {
        List<Picking> pickingList = new ArrayList<>();
        String commString ="SELECT OID,WMS_STOCK_DETAIL.STOCK_OID,PRODUCT_ID,PRODUCT_NAME,OUT_QTY,UOM FROM WMS_BA_TRK,WMS_STOCK_DETAIL,WMS_BA_PRODUCT WHERE WMS_BA_TRK.STOCK_OID = WMS_STOCK_DETAIL.STOCK_OID AND WMS_STOCK_DETAIL.ITEM_ID = WMS_BA_PRODUCT.PRODUCT_ID and P_CODE = '"+ p_code + "' and PALLET_ID = '" + pallet_id + "' and OPN = 6";
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
                    Picking picking = new Picking();
                    picking.set_oID(rs.getInt("OID"));
                    picking.set_sTOCK_OID(rs.getInt("STOCK_OID"));
                    picking.set_pRODUCT_ID(rs.getString("PRODUCT_ID"));
                    picking.set_pRODUCT_NAME(rs.getString("PRODUCT_NAME"));
                    picking.set_oUT_QTY(rs.getDouble("OUT_QTY"));
                    picking.set_uOM(rs.getString("UOM"));
                    pickingList.add(picking);
                }
            }
            UsersALL usersALL = new UsersALL();
            usersALL.setPickings(pickingList);
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;

    }

    public UsersALL updateStockDetail(String lastUpdateBy, String oid, String qty) {
        DBManager manager = DBManager.createInstance();
        try {
            PreparedStatement preparedStatement = manager.getConnection().prepareStatement("update WMS_STOCK_DETAIL set OUT_QTY = ?,LAST_UPDATE_DATE = ?, LAST_UPDATED_BY = ? where OID = ?");
            preparedStatement.setDouble(1,Double.valueOf(qty));
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(3, lastUpdateBy);
            preparedStatement.setString(4, oid);
            int updatefinish = preparedStatement.executeUpdate();
            UsersALL usersALL = new UsersALL();
            if (updatefinish > 0) {
                usersALL.setYesNo(true);

            }else {
                usersALL.setYesNo(false);
            }
            System.out.println("打印结果" + updatefinish);
            return usersALL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        manager.closeDB_PRE();
        return null;
    }

    public UsersALL getBinsta() {

        List<Binsta> binstas = new ArrayList<>();
        String commString = "select BIN_NO from WMS_BA_BINSTA where BIN_STA = '_' and IO_DISA = 'N' and BIN_NO not in(select WMS_BA_TRK.BIN_NO from  WMS_BA_TRK) ";
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
                    Binsta binsta = new Binsta();
                    binsta.set_bIN_NO(rs.getString("BIN_NO"));
                    binstas.add(binsta);
                }
            }

            UsersALL usersALL = new UsersALL();
            usersALL.setBinstas(binstas);
            return usersALL;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("这里出错？");
        }
        manager.closeDB();
        return null;

    }

    public UsersALL insertSort(String barcode, String dev_no, String kind, String status1, String created_by, String creation_date) {
        DBManager manager = DBManager.createInstance();
        try {
            PreparedStatement preparedStatement = manager.getConnection().prepareStatement("insert into WMS_SORT_PORT_INFO (Barcode,Dev_No,Kind,Status,Created_By,Creation_Date) values (?,?,?,?,?,?)");
            preparedStatement.setString(1, barcode);
            preparedStatement.setString(2, dev_no);
            preparedStatement.setInt(3, Integer.decode(kind));
            preparedStatement.setInt(4, Integer.decode(status1));
            preparedStatement.setInt(5, Integer.decode(created_by));
            preparedStatement.setTimestamp(6, ConvertTime.convertToTimestamp(creation_date));
            int updatefinish = preparedStatement.executeUpdate();
            UsersALL usersALL = new UsersALL();
            usersALL.setNumber(updatefinish);
            return usersALL;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        manager.closeDB_PRE();
        return null;

    }
}
