package com.servlet;

import com.google.gson.Gson;
import com.model.UsersALL;
import com.sqlService.SqlOperator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateStockDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastUpdateBy = request.getParameter("Last_update_by");
        String oid = request.getParameter("oid");
        String qty = request.getParameter("qty");
        request.setCharacterEncoding("UTF-8");
        //新建服务对象
        SqlOperator sqlOperator = new SqlOperator();
        UsersALL havaCount = sqlOperator.updateStockDetail(lastUpdateBy, oid, qty);
        System.out.println("havaCount :"+havaCount.toString());
        Gson gson = new Gson();
        String json = gson.toJson(havaCount);
        UsersALL.makeJson(response, havaCount, json);
    }
}
