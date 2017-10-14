package com.model;

import java.util.List;

public class UsersALL {
    private List<User> Users;
    private int number;
    private String data;

    public UsersALL(List<User> users, int number, String data) {
        Users = users;
        this.number = number;
        this.data = data;
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
