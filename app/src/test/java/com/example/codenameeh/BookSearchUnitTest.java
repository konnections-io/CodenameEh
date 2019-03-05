package com.example.codenameeh;

import org.junit.Test;

import com.codenameeh.classes.BookSearch;
import com.codenameeh.classes.Booklist;
import com.codenameeh.classes.User;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class BookSearchUnitTest{
      @Test
      public void test(){
        //Test getUser, setUser, searchDatabase
        User Tester = new User("Paul", 123123123, "myemail@gmail.com", "paul101", "mypassword");
        BookSearch bsearch = new BookSearch(Tester);
        User NewTester = new User("Temp", 456456456, "mytest@gmail.com", "test101", "newpassword");
        bsearch.setUser(NewTester);
        assertEquals(bsearch.getUser(),NewTester);
        
        
        //Add to data base a book with a keyword as "Keyword"
        Booklist WithKeyword = bsearch.searchDatabase("Keyword");
        assertThat(WithKeyword.size(), is(0));
        
      
      }
}
