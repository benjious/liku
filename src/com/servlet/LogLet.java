package com.servlet;

import com.google.gson.Gson;
import com.model.User;
import com.sqlService.SqlOperator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogLet extends HttpServlet {
    private static final long serialVersionUID = 369840050351775312L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        request.setCharacterEncoding("UTF-8");
        username = charSetConvert(username);
        String password = request.getParameter("password");
        System.out.println(username + "-----" + password);


        //新建服务对象
        SqlOperator sqlOperator = new SqlOperator();


        //验证处理
//        boolean isLogin = sqlOperator.login(username, password);
//        System.out.println("验证处理的结果是：    " + isLogin);
//        if (isLogin) {
//            System.out.println("Success");
//            request.getSession().setAttribute("username", username);
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html");
//            System.out.println("");
//            PrintWriter out = response.getWriter();
//            out.print(username);
//            out.flush();
//            out.close();
//        } else {
//            System.out.println("Failed");
//            //System.out.println("");
//        }

        User havaCount = sqlOperator.login_name(username, password);

        Gson gson = new Gson();
        String json = gson.toJson(havaCount);

        //System.out.println("验证处理的结果是：    " + havaCount.toString());
        if (havaCount!=null) {

            System.out.println("Success");
//            request.getSession().setAttribute("username", username);
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html");
//            System.out.println("");
//            PrintWriter out = response.getWriter();
//            out.print(username);
//            out.flush();
//            out.close();


            System.out.println(json);
            response.setContentType("text/plain");
            response.setCharacterEncoding("gb2312");
            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.print(json);
            out.flush();
        } else {
            System.out.println("Failed");
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    public String charSetConvert(String request) {
        String charSet = geteEncoding(request);
        System.out.println("传过来的编码是   ： " + charSet);
        try {
            byte[] b = request.getBytes(charSet);
            request = new String(b, charSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;

    }

    public static String geteEncoding(String str) {
        String[] charSet = {"UTF-8","GBK", "GB2312", "ISO-8859-1"};
        try {
            for (int i = 0; i < charSet.length; i++) {
                if (str.equals(new String(str.getBytes(charSet[i]), charSet[i]))) {
                    return charSet[i];

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}



