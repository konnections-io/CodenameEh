package com.example.codenameeh;

import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.Request;
import com.example.codenameeh.classes.User;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class RequestUnitTest {

    @Test
    public void test(){
        ArrayList<String> testWords = new ArrayList<String>();
        testWords.add("test1");
        testWords.add("test2");
        testWords.add("test3");

        User testUser = new User("Dan", "7801234567", "email@email.com", "username");
        Book testBook = new Book("Title", "Author", "ISBN", "Description", "Owner", testWords);
        Request request = new Request (testUser, testBook);

        //test changing status
        request.accept();
        assertTrue(request.getStatus());
        request.decline();
        assertFalse(request.getStatus());

        //test getting user and book
        assertEquals(request.getUser(), testUser);
        assertEquals(request.getBook(), testBook);
    }
}