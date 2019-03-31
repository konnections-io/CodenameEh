package com.example.codenameeh;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.Notification;
import com.example.codenameeh.classes.User;

import static org.junit.Assert.assertEquals;

public class notificationUnitTest {
    @Test
    public void testNotificationClass() {
        // Initializations
        ArrayList<String> al1 = new ArrayList<String>();
        ArrayList<String> keywords = new ArrayList<String>();
        String otherName = "bqi1";
        keywords.add("Magic");
        keywords.add("Adventure");
        String bookTitle = "BookTitle";
        String author = "Author";
        String ISBN = "123";
        String description = "A book's description";
        String owner = "Owner";
        String geolocation = "Nowhere";
        Book book = new Book(bookTitle,author,ISBN,description,owner,keywords);
        // Create the user and notifications
        Notification acceptedNotification = new Notification(otherName,geolocation,book,12.0,-23.3);
        Notification requestNotification = new Notification(otherName,book);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
        String comparingDate = dateFormat.format(c);


        assertEquals("User "+otherName+"\nwould like to borrow book:\n"+bookTitle+
                "\n"+comparingDate,requestNotification.toString());
        assertEquals("User "+otherName+"\nhas accepted your request for book:\n"+bookTitle+
                "\nGeolocation to receive is:\n"+geolocation+"\n"+comparingDate,acceptedNotification.toString());

    }
}
