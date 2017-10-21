package com.servlet;

import com.google.gson.Gson;
import com.model.UsersALL;
import com.sqlService.SqlOperator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertStackingItem extends HttpServlet {
    // // "insert into WMS_STACKING_ITEM (ITEM_ID,STACK_ID,LIST_NO,QTY,PROD_DATE,CREATION_DATE,CREATED_BY,LAST_UPDATE_DATE,LAST_UPDATED_BY)
    // values(@item_id,@stack_id,@list_no,@qty,@prod_date,@creation_date,@created_by,@last_update_date,@last_updated_by)";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String item_id = request.getParameter("ITEM_ID");
        String stack_id = request.getParameter("STACK_ID");
        String list_no = request.getParameter("LIST_NO");
        String qty = request.getParameter("QTY");
        String prod_date = request.getParameter("PROD_DATE");
        String creation_date = request.getParameter("CREATION_DATE");
        String createdBy = request.getParameter("CREATED_BY");
        String lastUpdateDate = request.getParameter("LAST_UPDATE_DATE");
        String lastUpdatedBy = request.getParameter("LAST_UPDATED_BY");
        request.setCharacterEncoding("UTF-8");
        //新建服务对象
        SqlOperator sqlOperator = new SqlOperator();
        UsersALL havaCount = sqlOperator.insertStackItem(item_id, stack_id, list_no, qty, prod_date, creation_date, createdBy, lastUpdateDate, lastUpdatedBy);
        System.out.println("havaCount :"+havaCount.toString());
        Gson gson = new Gson();
        String json = gson.toJson(havaCount);
        UsersALL.makeJson(response, havaCount, json);
    }
}
