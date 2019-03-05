package com.example.codenameeh;

import org.junit.Test;

import com.codenameeh.classes.User;
import com.codenameeh.classes.Userlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class userlistUnitTest {
    @Test
    public void testUserlist() {
        // Creates the Userlist and adds two users
        Userlist users = new Userlist();
        User newUser = new User("Generic Name", 1234567890, "generic@email.com", "Generically", "password123");
        User newUser2 = new User("Example Person", 1111111011, "example@email.com", "exampleUser", "examplePass");
        users.add(newUser);
        users.add(newUser2);

        // Tests the add and size functions
        int size1 = users.size();
        assertEquals(size1, 2);  // expected: size1 == 2

        // Tests the remove and size functions
        users.remove(newUser2);
        int size2 = users.size();
        assertEquals(size2, 1);  // expected: size2 == 1

        // Tests the contains function
        assertTrue(users.contains(newUser));
        assertFalse(users.contains(newUser2));
    }
}
