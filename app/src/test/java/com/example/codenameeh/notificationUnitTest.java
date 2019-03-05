package com.example.codenameeh;

import org.junit.Test;

import java.util.ArrayList;

import com.codenameeh.classes.Notification;
import com.codenameeh.classes.User;

import static org.junit.Assert.assertEquals;

public class notificationUnitTest {
    @Test
    public void testCount() {
        // Initializations
        ArrayList<String> al1 = new ArrayList<String>();
        String name = "John";
        int phone = 587;
        String email = "ddick1";
        String username = "ddick2";
        String password = "ddick3";
        // Create the user and notifications
        User u = new User(name, phone, email, username, password);
        Notification n = new Notification(u);

        String newNotification = "Apples";
        String newNotification2 = "Oranges";
        // Add the strings to both the class and a test array
        n.add(newNotification);
        n.add(newNotification2);
        al1.add(newNotification);
        al1.add(newNotification2);
        // Check if the notifications list is correctly stored
        // Checks if add() works correctly
        // Checks if getNotifications() works correctly
        assertEquals(n.getNotifications(), al1);
        n.remove(newNotification);
        al1.remove(newNotification);
        // Checks if remove() works correctly
        assertEquals(n.getNotifications(), al1);
        // Check if user is stored correctly and can be retrieved
        assertEquals(n.getUser(), u);
        al1.add(newNotification);
        n.setNotifications(al1);
        // Checking if notifications list is correctly updated
        assertEquals(n.getNotifications(), al1);
        //Another test case with adding notifications
        n.add("Jacobs");
        al1.add("Jacobs");
        assertEquals(n.getNotifications(), al1);
        //Lastly, confirm the new user functionality works, although this will
        // likely be trivial and not necessarily used in the final implementation
        User u2 = new User("Jack", phone, email, username, password);
        n.setUser(u2);
        assertEquals(n.getUser(), u2);

    }
}
