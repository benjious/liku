package com.servlet;

import com.google.gson.Gson;
import com.model.UsersALL;
import com.sqlService.SqlOperator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertStockDetail extends HttpServlet {
    //STOCK_OID, ITEM_ID, PROD_DATE, EXPIRE_DATE, BATCH_NO,  QTY,  OUT_QTY, STOCK_QTY, CREATION_DATE, CREATED_BY, LAST_UPDATE_DATE, LAST_UPDATED_BY
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stock_oid = request.getParameter("STOCK_OID");
        String item_id = request.getParameter("ITEM_ID");
        String prod_date = request.getParameter("PROD_DATE");
        String expire_date = request.getParameter("EXPIRE_DATE");
        String batch_no = request.getParameter("BATCH_NO");
        String qty = request.getParameter("QTY");
        String out_qty = request.getParameter("OUT_QTY");
        String stock_qty = request.getParameter("STOCK_QTY");
        String creation_date = request.getParameter("CREATION_DATE");

        String created_by = request.getParameter("CREATED_BY");
        String last_update_date = request.getParameter("LAST_UPDATE_DATE");
        String last_updated_by = request.getParameter("LAST_UPDATED_BY");
        request.setCharacterEncoding("UTF-8");
        //新建服务对象
        SqlOperator sqlOperator = new SqlOperator();
        UsersALL havaCount = sqlOperator.insertStockDetail(stock_oid,item_id,prod_date,expire_date,
                batch_no,qty,out_qty,stock_qty,creation_date,created_by,last_update_date,last_updated_by);
        System.out.println("havaCount :"+havaCount.toString());
        Gson gson = new Gson();
        String json = gson.toJson(havaCount);
        UsersALL.makeJson(response, havaCount, json);

    }
}
