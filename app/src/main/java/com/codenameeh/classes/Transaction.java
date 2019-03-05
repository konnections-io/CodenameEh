package com.codenameeh.classes;

public class Transaction {
    private User borrower;
    private User owner;
    private Book book;
    private Geolocation location;

    public Transaction(User borrower, User owner, Book book, Geolocation location) {
        this.borrower = borrower;
        this.owner = owner;
        this.book = book;
        this.location = location;
    }

    public void completeTransaction() {
        //TODO
    }

    public User getBorrower() {
        return borrower;
    }

    public void setBorrower(User borrower) {
        this.borrower = borrower;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Geolocation getLocation() {
        return location;
    }

    public void setLocation(Geolocation location) {
        this.location = location;
    }
}
