package com.example.codenameeh.classes;

public class CurrentUser extends User {

    private static CurrentUser instance;

    public CurrentUser(String name, int phone, String email, String username, String password) {
        super(name, phone, email, username, password);
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            return null;
        }
        return instance;
    }
}
