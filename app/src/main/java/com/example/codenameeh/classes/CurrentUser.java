package com.example.codenameeh.classes;

public class CurrentUser extends User {

    private static CurrentUser instance;

    private CurrentUser(String name, String phone, String email, String username) {
        super(name, phone, email, username);
    }

    public static void setInstance(String name, String phone, String email, String username) {
        instance = new CurrentUser(name, phone, email, username);
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            return null;
        }
        return instance;
    }
}
