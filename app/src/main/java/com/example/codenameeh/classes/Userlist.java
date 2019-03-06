package com.example.codenameeh.classes;

import java.util.ArrayList;

public class Userlist {
    private ArrayList<User> userList;

    public Userlist() {
        this.userList = new ArrayList<User>();
    }

    public int size() {
        return userList.size();
    }

    public void add(User user) {
        userList.add(user);
    }

    public void remove(User user) {
        userList.remove(user);
    }

    public boolean contains(User user) { return userList.contains(user); }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }
}
