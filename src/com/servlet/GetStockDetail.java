package com.servlet;

import com.google.gson.Gson;
import com.model.UsersALL;
import com.sqlService.SqlOperator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetStockDetail extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int stock_oid = Integer.decode(request.getParameter("stock_oid"));
        request.setCharacterEncoding("UTF-8");
        System.out.println(stock_oid + "-----" );

        //新建服务对象
        SqlOperator sqlOperator = new SqlOperator();
        UsersALL havaCount = sqlOperator.GetStockDetail(stock_oid);
        System.out.println("havaCount :"+havaCount.toString());
        Gson gson = new Gson();
        String json = gson.toJson(havaCount);
        UsersALL.makeJson(response, havaCount, json);
    }
}
