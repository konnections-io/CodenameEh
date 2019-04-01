package com.example.codenameeh;

import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.BookSearch;
import com.example.codenameeh.classes.User;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class BookSearchUnitTest{
      @Test
      public void test(){
          //Test getUser, setUser, searchDatabase
          ArrayList<String> keywords = new ArrayList<>();
          ArrayList<Book> books = new ArrayList<>();
          Book book1 = new Book("test", "author", "ISBN", "description", "image.png", "Owner", keywords);
          Book book2 = new Book("title", "test", "ISBN", "description", "image.png", "Owner", keywords);
          Book book3 = new Book("title", "author", "ISBN", "test", "image.png", "Owner", keywords);
          Book book4 = new Book("title", "author", "ISBN", "description", "image.png", "Owner", keywords);
          Book book5 = new Book("title", "author", "ISBN", "description", "image.png", "Owner", keywords);
          Book book6 = new Book("title", "author", "ISBN", "description", "image.png", "Owner", keywords);
          Book book7 = new Book("title", "author", "ISBN", "description", "image.png", "Owner", keywords);
          books.add(book1);
          books.add(book2);
          books.add(book3);
          books.add(book4);
          books.add(book5);
          books.add(book6);
          books.add(book7);
          User Tester = new User("Paul", "3123123", "myemail@gmail.com", "paul101");
          BookSearch bsearch = new BookSearch(Tester, books);
          User NewTester = new User("Temp", "456456456", "mytest@gmail.com", "test101");
          bsearch.setUser(NewTester);
          assertEquals(bsearch.getUser(),NewTester);

          //Search the booklist for a books matching "test"
          ArrayList<Book> WithKeyword = bsearch.searchDatabase("test");
          assertThat(WithKeyword.size(), is(3));
      }
}
