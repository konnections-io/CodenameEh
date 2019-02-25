package com.example.codenameeh;

import org.junit.Test;

import classes.Transaction;
import classes.User;
import classes.Book;
import classes.Geolocation;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TransactionUnitTest{
    @Test
    public void test(){

        Transaction t = new Transaction(null, null, null, null);
        
        User user1 = new User("name1", 123123123, "email1", "username1", "password1");
        User user2 = new User("name2", 456456456, "email2", "username2", "password2");

        Book book = new Book("NA", "NA", "0", "NA", "NA");
        
        Geolocation location = new Geolocation("country", "city", "address");

        //TEST GETTERS and SETTERS
        t.setBorrower(user1);
        assertEquals(t.getBorrower(), user1);

        t.setOwner(user2);
        assertEquals(t.getBorrower(), user2);

        t.setBook(book);
        assertEquals(t.getBook(), book);

        t.setLocation(location);
        assertEquals(t.getLocation(), location);

        //TEST completeTransaction
        t.completeTransaction();
        assertTrue(user1.getBorrowing().contains(book)); //borrower should now have the book in their borrowed list
      }
}