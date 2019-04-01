package com.example.codenameeh.classes;

/**
 * Tracks the current user logging in rather than a generic user. Used to retrieve
 * the current user's fields and values.
 */
public class CurrentUser extends User {

    private static CurrentUser instance;

    /**
     * All that is needed to create the CurrentUser is the name, phone, email, username
     * as all String objects
     * @param name
     * @param phone
     * @param email
     * @param username
     */
    private CurrentUser(String name, String phone, String email, String username) {
        super(name, phone, email, username);
    }

    /**
     * Used when logging in; Current user becomes whatever user logs in.
     * @param user
     */
    public static void setInstance(User user) {
        String name = user.getName();
        String phone = user.getPhone();
        String email = user.getEmail();
        String username = user.getUsername();
        instance = new CurrentUser(name, phone, email, username);
    }

    /**
     * Returns a CurrentUser depending on if initialized or not.
     * @return
     */
    public static CurrentUser getInstance() {
        if (instance == null) {
            return null;
        }
        return instance;
    }
}
