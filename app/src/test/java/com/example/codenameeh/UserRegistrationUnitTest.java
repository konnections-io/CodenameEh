package com.example.codenameeh;

import org.junit.Test;

import com.example.codenameeh.classes.UserRegistration;
import com.example.codenameeh.classes.User;

import static junit.framework.TestCase.assertTrue;

public class UserRegistrationUnitTest{
      @Test
      public void test(){
      
        User testUser = new User("name", "1234567890", "email", "username");
      
        UserRegistration userReg = new UserRegistration(testUser);
        
        //Test saveToDatabase
        //userReg.saveToDataBase(); //cannot test as we have not implemented a database yet
        
      }
}
