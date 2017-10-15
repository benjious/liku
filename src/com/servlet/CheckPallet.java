package com.servlet;

import com.google.gson.Gson;
import com.model.UsersALL;
import com.sqlService.SqlOperator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckPallet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        String pallet_id = request.getParameter("pallet_id");
        request.setCharacterEncoding("UTF-8");
        int status =Integer.decode(request.getParameter("status"));
        System.out.println(pallet_id + "-----" + status);


        //新建服务对象
        SqlOperator sqlOperator = new SqlOperator();
        UsersALL havaCount = sqlOperator.CheckPallet(pallet_id,status);
        System.out.println("havaCount :"+havaCount.toString());
        Gson gson = new Gson();
        String json = gson.toJson(havaCount);
        UsersALL.makeJson(response, havaCount, json);
    }


}
