package com.example.codenameeh.classes;


import java.util.ArrayList;


public class Notification {
    private ArrayList<String> notifications;
    public Notification() {
        this.notifications = new ArrayList<String>();
    }

    public void add(String userWanting, String bookWanted) {
        //Use user to access and add specified notification, this one for requests
        notifications.add("Requester "+userWanting+" would like to borrow: "+bookWanted);
    }

    public void add(String userGiving, String bookGiven, String geolocation) {
        //Use user to access and add specified notification, this one for accepted requests
        notifications.add(userGiving+" has accepted your request for: "+bookGiven+"! Geolocation is: "+geolocation);
    }

    public void remove(String userWanting, String bookWanted) {
        //Use user to access and remove specific notification, this one is for requests
        notifications.remove("Requester "+userWanting+" would like to borrow: "+bookWanted);
    }

    public void remove(String userGiving, String bookGiven, String geolocation) {
        //Use user to access and remove specific notification, this one is for accepted requests
        notifications.remove(userGiving+" has accepted your request for: "+bookGiven+"! Geolocation is: "+geolocation);
    }

    public ArrayList<String> getNotificationsList() {
            return notifications;
    }

}
