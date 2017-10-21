package com.servlet;

import com.google.gson.Gson;
import com.model.UsersALL;
import com.sqlService.SqlOperator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertStacking extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stack_id = request.getParameter("STACK_ID");
        String wh_no = request.getParameter("WH_NO");
        String pallet_id = request.getParameter("PALLET_ID");
        String p_code = request.getParameter("P_CODE");
        String kind = request.getParameter("KIND");
        String bin_no = request.getParameter("BIN_NO");
        String full_flag = request.getParameter("FULL_FLAG");
        String status = request.getParameter("STATUS");
        String creation_date = request.getParameter("CREATION_DATE");
        String created_by = request.getParameter("CREATED_BY");
        String last_update_date = request.getParameter("LAST_UPDATE_DATE");
        String last_updated_by = request.getParameter("LAST_UPDATED_BY");
        request.setCharacterEncoding("UTF-8");
        //新建服务对象
        SqlOperator sqlOperator = new SqlOperator();
        UsersALL havaCount = sqlOperator.insertStack(stack_id,wh_no,pallet_id,p_code,kind,bin_no,full_flag,creation_date,status,created_by,last_update_date,last_updated_by);
        System.out.println("havaCount :"+havaCount.toString());
        Gson gson = new Gson();
        String json = gson.toJson(havaCount);
        UsersALL.makeJson(response, havaCount, json);
    }
}
