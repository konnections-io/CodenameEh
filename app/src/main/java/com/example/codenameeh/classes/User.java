package com.example.codenameeh.classes;

import java.util.ArrayList;

public class User {
    private String name;
    private String phone;
    private String email;
    private String username;
    private ArrayList<String> keywords;
    private KeywordTracker searchWords;
    private Booklist owning;
    private Booklist borrowing;
    private Booklist borrowedHistory;
    private Booklist requesting;
    private Notification otherRequestNotifications;
    private Notification requestAcceptedNotifications;

    public User() {
        //Empty Constructor
    }

    public User(String name, String phone, String email, String username) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.owning = new Booklist();
        this.borrowing = new Booklist();
        this.borrowedHistory = new Booklist();
        this.requesting = new Booklist();
        this.searchWords = new KeywordTracker();
        this.requestAcceptedNotifications = new Notification();
        this.otherRequestNotifications = new Notification();
    }

    public void newOwn(Book book) {
        this.owning.add(book);
        book.setOwner(this);
    }

    public void removeOwn(Book book) {
        this.owning.remove(book);
        book.setOwner(null);
    }

    public void newBorrow(Book book) {
        this.borrowing.add(book);
        this.borrowedHistory.add(book);
        book.setBorrowed(true);
    }

    public void removeBorrow(Book book) {
        this.borrowing.remove(book);
        book.setBorrowed(false);
        // Don't think we want this to remove the borrow history
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public KeywordTracker getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(KeywordTracker searchWords) {
        this.searchWords = searchWords;
    }

    public Booklist getOwning() {
        return owning;
    }

    public void setOwning(Booklist owning) {
        this.owning = owning;
    }

    public Booklist getBorrowing() {
        return borrowing;
    }

    public void setBorrowing(Booklist borrowing) {
        this.borrowing = borrowing;
    }

    public Booklist getBorrowedHistory() {
        return borrowedHistory;
    }

    public void setBorrowedHistory(Booklist borrowedHistory) {
        this.borrowedHistory = borrowedHistory;
    }

    public Booklist getRequesting() {
        return requesting;
    }

    public void setRequesting(Booklist requesting) {
        this.requesting = requesting;
    }

    public Notification getOtherRequestNotifications() { return otherRequestNotifications;}
    public void setOtherRequestNotifications(Notification notification) {this.otherRequestNotifications = notification;}
    public Notification getRequestAcceptedNotifications() { return requestAcceptedNotifications;}
    public void setRequestAcceptedNotifications(Notification notification) {this.requestAcceptedNotifications = notification;}


}
