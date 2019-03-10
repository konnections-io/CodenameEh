package com.example.codenameeh.classes;

import java.util.ArrayList;

public class Notification {

    private User user;

    public Notification(User user) {
        this.user = user;
    }

    public void add(String userWanting, String bookWanted) {
        //Use user to access and add specified notification
    }

    public void add(String userGiving, String bookGiven, String geolocation) {
        //Use user to access and add specified notification
    }

    public void remove(String userWanting, String bookWanted) {
        //Use user to access and remove specific notification
    }

    public void remove(String userGiving, String bookGiven, String geolocation) {
        //Use user to access and remove specific notification
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void getRequestNotifications() {
        //Call database for retrieval of tuple of 2 strings, user wanting, and book wanted

        }

    public void getAcceptedBookNotifications() {
        //Call database for retrieval of tuple of 3 strings, user giving, book being given, geolocation

        }
}
