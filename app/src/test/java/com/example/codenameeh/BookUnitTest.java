package com.example.codenameeh;

import org.junit.Test;

import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.User;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

public class BookUnitTest{
      @Test
      public void test(){
         //Test get/set title, get/set author, get/set ISBN, get/set description, get/set photograph, isBorrowed, setBorrowed (boolean)
          String title = "Original Title";
          String author = "An Author";
          String description = "A book about animals";
          String owner = "bqi1";
          String ISBN = "123123123";
          ArrayList<String> keywords = new ArrayList<String>();
          keywords.add("Magic");
          keywords.add("Animals");
         Book book = new Book("NA","NA","NA","NA","NA",keywords);

         book.setTitle(title);
         assertEquals(book.getTitle(),title);

         book.setAuthor(author);
         assertEquals(book.getAuthor(),author);

         book.setISBN(ISBN);
         assertEquals(book.getISBN(),ISBN);

         book.setDescription(description);
         assertEquals(book.getDescription(),description);
         // Photograph is not represented with a string anymore, unsure as to how to change this adequately
         // book.setPhotograph("Cat.png");
         assertNull(book.getPhotograph());


         assertFalse(book.isBorrowed());
         book.setBorrowed(true);
         assertTrue(book.isBorrowed());

        String name = "brian";
        String phone = "123123";
        String email = "email@ualberta.ca";
        String username = "bqi1";
          User borrower = new User(name,phone,email,username);
          book.borrow(borrower);
          assertEquals(book.getBorrower(),username);
          assertTrue(book.isBorrowed());
          assertFalse(book.getOwnerConfirmation());
          assertFalse(book.getBorrowerConfirmation());

          book.unborrow();
            assertFalse(book.isBorrowed());
            assertNull(book.getBorrower());
            assertFalse(book.getOwnerConfirmation());
            assertFalse(book.getBorrowerConfirmation());



      }
}
