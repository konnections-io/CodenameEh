package com.example.codenameeh.classes;

import java.util.ArrayList;

/**
 * Searches the booklist for the user's queries
 * @author Daniel Shim
 * @version 1.0
 */
public class BookSearch {
    private User user;
    private Booklist allBooks = Booklist.getInstance();
    public BookSearch(User user) {
        this.user = user;
    }

    /**
     * Returns a book list on all related books based on single keyword given
     * @param keyword
     * @return
     */
    public ArrayList<Book> searchDatabase(String keyword) {
        keyword = keyword.toUpperCase();
        ArrayList<Book> books = allBooks.getBookList();
        ArrayList<Book> filteredBooks = new ArrayList<>();

        for (Book book: books) {
            if (book.getTitle().toUpperCase().indexOf(keyword) != -1 || book.getAuthor().toUpperCase().indexOf(keyword) != -1
                    || book.getDescription().toUpperCase().indexOf(keyword) != -1) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
