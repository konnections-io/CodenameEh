//package com.example.codenameeh;
//
//import org.junit.Test;
//
//import com.example.codenameeh.classes.Book;
//import com.example.codenameeh.classes.Booklist;
//
//import static junit.framework.TestCase.assertEquals;
//import static junit.framework.TestCase.assertTrue;
//import static org.junit.Assert.assertFalse;
//
//public class BooklistUnitTest {
//
//    @Test
//    public void test() {
//        Booklist booklist = new Booklist();
//
//        Book book1 = new Book("testBook1", "testAuthor1", "12345",
//                "This is test book number 1", "/testlocation/test/png");
//
//        Book book2 = new Book("testBook2", "testAuthor2", "54321",
//                "This is test book number 2", "/testlocation/test/png");
//
//        //Test add first book
//        booklist.add(book1);
//        assertTrue(booklist.contains(book1));
//        assertEquals(booklist.size(), 1);
//
//        //Test add second book
//        booklist.add(book2);
//        assertTrue(booklist.contains(book2));
//        assertEquals(booklist.size(), 2);
//
//        //Test remove first book
//        booklist.remove(book1);
//        assertFalse(booklist.contains(book1));
//        assertTrue(booklist.contains(book2));
//        assertEquals(booklist.size(), 1);
//
//
//
//
//    }
//
//}
