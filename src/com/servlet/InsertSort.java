package com.servlet;

import com.google.gson.Gson;
import com.model.UsersALL;
import com.sqlService.SqlOperator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertSort extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Barcode,Dev_No,Kind,Status,Created_By,Creation_Date
        String barcode = request.getParameter("Barcode");
        String dev_no = request.getParameter("Dev_No");
        String kind = request.getParameter("Kind");
        System.out.println("输出kind : "+kind);
        String status1 = request.getParameter("Status");
        String created_by = request.getParameter("Created_By");
        String creation_date = request.getParameter("Creation_Date");
        request.setCharacterEncoding("UTF-8");
        //新建服务对象
        SqlOperator sqlOperator = new SqlOperator();
        UsersALL havaCount = sqlOperator.insertSort(barcode,dev_no,kind,status1,created_by,creation_date);
        System.out.println("这里有执行");
        Gson gson = new Gson();
        String json = gson.toJson(havaCount);
        UsersALL.makeJson(response, havaCount, json);
    }
}
