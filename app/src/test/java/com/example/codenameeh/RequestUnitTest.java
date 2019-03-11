package com.example.codenameeh;

import com.example.codenameeh.classes.Request;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class RequestUnitTest {

    @Test
    public void test(){
        Request request = new Request ("user", "book");

        //test accepting and declining
        request.accept();
        assertTrue(request.getStatus());
        request.decline();
        assertFalse(request.getStatus());

        //test getting user and book
        assertEquals(request.getUser(), "user");
        assertEquals(request.getBook(), "book");
    }
}