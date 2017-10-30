package com.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class UsersALL {
    private List<User> Users;
    private List<StockDetail> mStockDetails;
    private List<Inventory>  mInventories;
    private List<Picking> mPickings;

    public List<Picking> getPickings() {
        return mPickings;
    }

    public void setPickings(List<Picking> pickings) {
        mPickings = pickings;
    }

    private int number;
    private String data = new String("");
    private boolean yesNo;


    public List<Inventory> getInventories() {
        return mInventories;
    }

    public void setInventories(List<Inventory> inventories) {
        mInventories = inventories;
    }

    public UsersALL() {
        Users = new ArrayList<>();
        mStockDetails = new ArrayList<>();
        mInventories = new ArrayList<>();

    }

    public List<StockDetail> getStockDetails() {
        return mStockDetails;
    }

    public void setStockDetails(List<StockDetail> stockDetails) {
        mStockDetails = stockDetails;
    }

    public boolean isYesNo() {
        return yesNo;
    }

    public void setYesNo(boolean yesNo) {
        this.yesNo = yesNo;
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
                "Users=" + Users + '}';
    }

    public static void makeJson(HttpServletResponse response, UsersALL havaCount, String json) throws IOException {
        if (havaCount != null) {
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
