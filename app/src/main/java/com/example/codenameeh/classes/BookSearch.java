package com.example.codenameeh.classes;

import java.util.ArrayList;

/**
 * Searches the booklist for the user's queries
 * @author Daniel Shim
 * @version 1.0
 */
public class BookSearch {
    private User user;
    private ArrayList<Book> allBooks;

    /**
     * @param user The current user of the session
     * @param booklist The master booklist containing all the books
     */
    public BookSearch(User user, ArrayList<Book> booklist) {
        this.user = user;
        allBooks = booklist;
    }

    /**
     * Returns a book list on all related books based on single keyword given
     * @param keyword The constraining word used to filter the books
     * @return A collection of books that contains books with the keyword in their title, author, or description
     */
    public ArrayList<Book> searchDatabase(String keyword) {
        keyword = keyword.toUpperCase();
        ArrayList<Book> books = allBooks;
        ArrayList<Book> filteredBooks = new ArrayList<>();

        for (Book book: books) {
            if (book.getTitle().toUpperCase().contains(keyword) || book.getAuthor().toUpperCase().contains(keyword)
                    || book.getDescription().toUpperCase().contains(keyword)) {
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
