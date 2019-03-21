package com.example.codenameeh;

import org.junit.Test;

import com.example.codenameeh.classes.User;
import com.example.codenameeh.classes.UserSearch;
import com.example.codenameeh.classes.Userlist;

import static org.junit.Assert.assertEquals;

public class userSearchUnitTest {
    @Test
    public void testUserSearch() {
        // Create a new Userlist that contains one generic user
        Userlist users = new Userlist();
        User newUser = new User("Generic Name", "1234567890", "generic@email.com", "Generically");
        users.add(newUser);

        // Using the search method to find all users with the username "Generically"
        String keyword = "Generically";
        UserSearch search = new UserSearch();
        Userlist results = search.searchUser(keyword, users);

        // Retrieves the keyword
        String retrievedKeyword = search.getKeyword();

        // compares the keyword and the retrieved keyword
        assertEquals(keyword, retrievedKeyword);

        // users and results should be the same Userlist
        assertEquals(users, results);
    }
}
