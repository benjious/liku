package com.servlet;

import com.google.gson.Gson;
import com.model.UsersALL;
import com.sqlService.SqlOperator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetPickings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String p_code = request.getParameter("p_code");
        String pallet_id = request.getParameter("pallet_id");

        request.setCharacterEncoding("UTF-8");
        System.out.println(pallet_id + "-----" +p_code);

        //新建服务对象
        SqlOperator sqlOperator = new SqlOperator();
        UsersALL havaCount = sqlOperator.getPickings(p_code, pallet_id);
        System.out.println("havaCount :"+havaCount.toString());
        Gson gson = new Gson();
        String json = gson.toJson(havaCount);
        UsersALL.makeJson(response, havaCount, json);

    }
}
