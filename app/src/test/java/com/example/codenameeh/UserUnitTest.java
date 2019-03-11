package com.example.codenameeh;

import org.junit.Test;

import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class UserUnitTest {
    String testName = "name";
    String testPhone = "123";
    String testEmail = "r";
    String testUsername = "diodone";
    String testPassword = "password";

    @Test
    public void testBasicFunctionality(){
        User test = new User(testName, testPhone, testEmail, testUsername);
        assertEquals(testEmail, test.getEmail());
        assertEquals(testName, test.getName());
        //assertEquals(testPassword, test.getPassword());
        assertEquals(testUsername, test.getUsername());
        assertEquals(testPhone, test.getPhone());
        // random increases, so that we didn't stumble upon a default which just happens to be right
        User test2 = new User(testName + 1, testPhone+3, testEmail+5, testUsername +7);
        assertEquals(testEmail+5, test2.getEmail());
        assertEquals(testName+1, test2.getName());
        //assertEquals(testPassword+9, test2.getPassword());
        assertEquals(testUsername+7, test2.getUsername());
        assertEquals(testPhone+3, test2.getPhone());
    }

    @Test
    public void testNewOwn(){
        // Change to ensure all values are changeable that should be
        User test = new User(testName, testPhone, testEmail, testUsername);
        Book newBook = new Book("1", "2", "3", "4", "5");
        Book alternateBook = new Book("6", "7", "8", "9", "10");
        assertEquals(0, test.getOwning().size());
        test.newOwn(newBook);
        // something is in there now
        assertEquals(1, test.getOwning().size());
        // that something is right
        assertTrue(test.getOwning().contains(newBook));
        // multiple
        test.newOwn(newBook);
        assertEquals(2, test.getOwning().size());
        assertTrue(test.getOwning().contains(newBook));
        // different book

        test.newOwn(alternateBook);
        assertEquals(3, test.getOwning().size());
        assertTrue(test.getOwning().contains(alternateBook));

        // null is ignored
        test.newOwn(null);
        assertEquals(3, test.getOwning().size());
        // adding to owning does not add to Borrowing
        assertEquals(0, test.getBorrowing().size());
    }

    @Test
    public void testRemoveOwn(){
        User test = new User(testName, testPhone, testEmail, testUsername);
        Book newBook = new Book("1", "2", "3", "4", "5");
        Book alternateBook = new Book("6", "7", "8", "9", "10");

        // assuming that newOwn works as expected
        test.newOwn(newBook);
        test.newOwn(alternateBook);
        test.removeOwn(alternateBook);
        // only drop expected book
        assertTrue(test.getOwning().contains(newBook));
        assertFalse(test.getOwning().contains(alternateBook));
        // do not drop last book, but the requested book
        test.removeOwn(alternateBook);
        assertTrue(test.getOwning().contains(newBook));
        try{
            test.removeOwn(null);
        } catch(Error e){
            // no error for null
            fail();
        }
        test.removeOwn(newBook);
        assertFalse(test.getOwning().contains(newBook));
        // on empty, should do nothing, no errors
        try {
            test.removeOwn(newBook);
        } catch(Error e){
            fail();
        }
    }

    @Test
    public void testNewBorrow(){
        User test = new User(testName, testPhone, testEmail, testUsername);
        Book newBook = new Book("1", "2", "3", "4", "5");
        Book alternateBook = new Book("6", "7", "8", "9", "10");
        assertEquals(0, test.getBorrowing().size());
        test.newBorrow(newBook);
        // something is in there now
        assertEquals(1, test.getBorrowing().size());
        // that something is right
        assertTrue(test.getBorrowing().contains(newBook));
        // multiple
        test.newBorrow(newBook);
        assertEquals(2, test.getBorrowing().size());
        assertTrue(test.getBorrowing().contains(newBook));
        // different book

        test.newBorrow(alternateBook);
        assertEquals(3, test.getBorrowing().size());
        assertTrue(test.getBorrowing().contains(alternateBook));

        // null is ignored
        test.newBorrow(null);
        assertEquals(3, test.getBorrowing().size());
        // adding to borrow does not add to owning
        assertEquals(0, test.getOwning().size());
    }

    @Test
    public void testRemoveBorrow(){
        User test = new User(testName, testPhone, testEmail, testUsername);
        Book newBook = new Book("1", "2", "3", "4", "5");
        Book alternateBook = new Book("6", "7", "8", "9", "10");

        // assuming that newOwn works as expected
        test.newBorrow(newBook);
        test.newBorrow(alternateBook);
        test.removeBorrow(alternateBook);
        // only drop expected book
        assertTrue(test.getBorrowing().contains(newBook));
        assertFalse(test.getBorrowing().contains(alternateBook));
        // do not drop last book, but the requested book
        test.removeBorrow(alternateBook);
        assertTrue(test.getBorrowing().contains(newBook));
        try{
            test.removeBorrow(null);
        } catch(Error e){
            // no error for null
            fail();
        }
        test.removeBorrow(newBook);
        assertFalse(test.getBorrowing().contains(newBook));
        // on empty, should do nothing, no errors
        try {
            test.removeBorrow(newBook);
        } catch(Error e){
            fail();
        }
    }
}
