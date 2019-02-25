package com.example.codenameeh;

import org.junit.Test;

import classes.Book;
import classes.UserValidifier;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class BookUnitTest{
      @Test
      public void test(){
         //Test get/set title, get/set author, get/set ISBN, get/set description, get/set photograph, isBorrowed, setBorrowed (boolean)
         Book book = new Book("NA","NA","0","NA","NA");
         
         book.setTitle("Original Title");
         assertEquals(book.getTitle(),"Original Title");
         
         book.setAuthor("An Author");
         assertEquals(book.getAuthor(),"An Author");
         
         book.setISBN("123123123");
         assertEquals(book.getISBN(),"123123123");
         
         book.setDescription("Animals");
         assertEquals(book.getDescription(),"Animals");
         
         book.setPhotograph("Cat.png");
         assertEquals(book.getPhotograph(),"Cat.png");
         
         assertFalse(book.isBorrowed());
         book.setBorrowed(true);
         assertTrue(book.isBorrowed());
         
      
      
      }
}
