package com.example.codenameeh.classes;

public class BookSearch {
    private User user;

    public BookSearch(User user) {
        this.user = user;
    }

    public Booklist searchDatabase(String keyword, Booklist booklist) {
        //TODO: Have a booklist of books related to keyword by accessing a database
        Booklist originalList = booklist;
        Booklist filteredBookList = new Booklist();
        // Add books to this booklist depending on keyword specified
        for (Book book: originalList.getBookList()) {
            if (book.getTitle().contains(keyword)) {
                filteredBookList.add(book);
            }
        }

        return filteredBookList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
