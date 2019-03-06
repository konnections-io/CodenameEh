package com.example.codenameeh;

import org.junit.Test;

import com.example.codenameeh.classes.UserValidifier;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class UserValidifyUnitTest {

    @Test
    public void test() {
        boolean valid;
        String user, pass;
        //test true account
        user = "TestUser";
        pass = "TestPass123!";
        UserValidifier val1 = new UserValidifier(user, pass);
        valid = val1.validify();
        assertTrue(valid);

        //test wrong password
        pass = "FakePass123!";
        UserValidifier val2 = new UserValidifier(user, pass);
        valid = val2.validify();
        assertFalse(valid);

        //test false account
        user = "FakeUser";
        pass = "FakePass123!";
        UserValidifier val3 = new UserValidifier(user, pass);
        valid = val3.validify();
        assertFalse(valid);
    }
}
