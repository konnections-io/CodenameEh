
package com.example.codenameeh;

import org.junit.Test;

import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.User;

import java.util.ArrayList;

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
    Book newBook;
    Book alternateBook;

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
        newBook = new Book("1", "2", "3", "4", "5", null);
        alternateBook = new Book("6", "7", "8", "9", "10", null);
        Booklist.setInstance(new ArrayList<Book>());
        Booklist.getInstance().add(alternateBook);
        Booklist.getInstance().add(newBook);
        assertEquals(0, test.BooksOwned().size());
        test.newOwn(newBook);
        // something is in there now
        assertEquals(1, test.BooksOwned().size());
        // that something is right
        assertTrue(test.BooksOwned().contains(newBook));
        // multiple
        test.newOwn(newBook);
        assertEquals(2, test.BooksOwned().size());
        assertTrue(test.BooksOwned().contains(newBook));
        // different book

        test.newOwn(alternateBook);
        assertEquals(3, test.BooksOwned().size());
        assertTrue(test.BooksOwned().contains(alternateBook));

        // null is ignored
        test.newOwn(null);
        assertEquals(3, test.BooksOwned().size());
        // adding to owning does not add to Borrowing
        assertEquals(0, test.BorrowedBooks().size());
    }

    @Test
    public void testRemoveOwn(){
        User test = new User(testName, testPhone, testEmail, testUsername);
        newBook = new Book("1", "2", "3", "4", "5", null);
        alternateBook = new Book("6", "7", "8", "9", "10", null);
        Booklist.setInstance(new ArrayList<Book>());
        Booklist.getInstance().add(alternateBook);
        Booklist.getInstance().add(newBook);
        // assuming that newOwn works as expected
        test.newOwn(newBook);
        test.newOwn(alternateBook);
        test.removeOwn(alternateBook);
        // only drop expected book
        assertTrue(test.BooksOwned().contains(newBook));
        assertFalse(test.BooksOwned().contains(alternateBook));
        // do not drop last book, but the requested book
        test.removeOwn(alternateBook);
        assertTrue(test.BooksOwned().contains(newBook));
        try{
            test.removeOwn(null);
        } catch(Error e){
            // no error for null
            fail();
        }
        test.removeOwn(newBook);
        assertFalse(test.BooksOwned().contains(newBook));
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
        newBook = new Book("1", "2", "3", "4", "5", null);
        alternateBook = new Book("6", "7", "8", "9", "10", null);
        Booklist.setInstance(new ArrayList<Book>());
        Booklist.getInstance().add(alternateBook);
        Booklist.getInstance().add(newBook);
        assertEquals(0, test.BorrowedBooks().size());
        test.newBorrow(newBook);
        // something is in there now
        assertEquals(1, test.BorrowedBooks().size());
        // that something is right
        assertTrue(test.BorrowedBooks().contains(newBook));
        // multiple
        test.newBorrow(newBook);
        assertEquals(2, test.BorrowedBooks().size());
        assertTrue(test.BorrowedBooks().contains(newBook));
        // different book

        test.newBorrow(alternateBook);
        assertEquals(3, test.BorrowedBooks().size());
        assertTrue(test.BorrowedBooks().contains(alternateBook));

        // null is ignored
        test.newBorrow(null);
        assertEquals(3, test.BorrowedBooks().size());
        // adding to borrow does not add to owning
        assertEquals(0, test.BooksOwned().size());
    }

    @Test
    public void testRemoveBorrow(){
        User test = new User(testName, testPhone, testEmail, testUsername);
        newBook = new Book("1", "2", "3", "4", "5", null);
        alternateBook = new Book("6", "7", "8", "9", "10", null);
        Booklist.setInstance(new ArrayList<Book>());
        Booklist.getInstance().add(alternateBook);
        Booklist.getInstance().add(newBook);
        // assuming that newOwn works as expected
        test.newBorrow(newBook);
        test.newBorrow(alternateBook);
        test.removeBorrow(alternateBook);
        // only drop expected book
        assertTrue(test.BorrowedBooks().contains(newBook));
        assertFalse(test.BorrowedBooks().contains(alternateBook));
        // do not drop last book, but the requested book
        test.removeBorrow(alternateBook);
        assertTrue(test.BorrowedBooks().contains(newBook));
        try{
            test.removeBorrow(null);
        } catch(Error e){
            // no error for null
            fail();
        }
        test.removeBorrow(newBook);
        assertFalse(test.BorrowedBooks().contains(newBook));
        // on empty, should do nothing, no errors
        try {
            test.removeBorrow(newBook);
        } catch(Error e){
            fail();
        }
    }
}

