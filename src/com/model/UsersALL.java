package com.model;

import java.util.List;

public class UsersALL {
    private List<User> Users;
    private int number;
    private String data;

    public boolean isYesNo() {
        return isYesNo;
    }

    public void setYesNo(boolean yesNo) {
        isYesNo = yesNo;
    }

    private boolean isYesNo;

    public UsersALL(List<User> users, int number, String data, boolean isYesNo) {
        Users = users;
        this.number = number;
        this.data = data;
        this.isYesNo = isYesNo;
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
                "Users=" + Users +
                '}';
    }
}
