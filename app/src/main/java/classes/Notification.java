package classes;

import java.util.ArrayList;

public class Notification {
    private User user;
    private ArrayList<String> notifications;

    public Notification(User user) {
        this.user = user;
        this.notifications = new ArrayList<String>();
    }

    public void add(String notification) {
       notifications.add(notification);
    }

    public void remove(String notification) {
        notifications.remove(notification);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<String> notifications) {
        this.notifications = notifications;
    }
}
