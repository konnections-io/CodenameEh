package com.example.codenameeh.classes;

public class CurrentUser extends User {

    private static CurrentUser instance;

    private CurrentUser(String name, String phone, String email, String username) {
        super(name, phone, email, username);
    }

    public static void setInstance(User user) {
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();
        String username = user.getUsername();
        instance = new CurrentUser(name, phone, email, username);
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            return null;
        }
        return instance;
    }
}
