package com.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class UsersALL {
    private List<User> Users;
    private int number;
    private String data=new String("");

    public boolean isYesNo() {
        return isYesNo;
    }

    public void setYesNo(boolean yesNo) {
        isYesNo = yesNo;
    }

    private boolean isYesNo;

    public UsersALL( int number, String data, boolean isYesNo) {
       // Users = new ArrayList<>();
        this.number = number;
        this.data = data;
        this.isYesNo = isYesNo;
    }

    public UsersALL() {
        Users=new ArrayList<>();
    }

    public List<User> getUsers() {
        return Users;
    }

    public void setUsers(List<User> users) {
        Users = users;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UsersALL{" +
                "Users=" + Users +'}';
    }

    public static void makeJson(HttpServletResponse response, UsersALL havaCount, String json) throws IOException {
        if (havaCount!=null) {
            System.out.println("Success");
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
}
