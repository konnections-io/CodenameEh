package com.example.codenameeh.classes;

import android.util.Log;

import java.util.ArrayList;

/**
 * A user has an email,name,phone,username,keywords saved,keywordTracker,
 * lists of what book uuids are owned, borrowed,requeste, and notifications
 */
public class User {
    private String name;
    private String phone;
    private String email;
    private String username;
    private ArrayList<String> keywords;
    private KeywordTracker searchWords;
    private ArrayList<String> owning;
    private ArrayList<String> borrowing;
    private ArrayList<String> borrowedHistory;
    private ArrayList<String> requesting;
    private ArrayList<Notification> notifications;

    public User() {
        //Empty Constructor
        owning = new ArrayList<>();
        borrowing = new ArrayList<>();
        borrowedHistory = new ArrayList<>();
        requesting = new ArrayList<>();
        notifications = new ArrayList<>();
        keywords = new ArrayList<>();
    }

    /**
     * All that is needed to initialize a User is name, phone, email, username
     * as String objects
     * @param name
     * @param phone
     * @param email
     * @param username
     */
    public User(String name, String phone, String email, String username) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.owning = new ArrayList<>();
        this.borrowing = new ArrayList<>();
        this.borrowedHistory = new ArrayList<>();
        this.keywords = new ArrayList<>();
        this.requesting = new ArrayList<>();
        this.searchWords = new KeywordTracker();
        this.notifications = new ArrayList<Notification>();
    }

    /**
     * Adds a book to what a user can own
     * @param book
     */
    public void newOwn(Book book) {
        if(book != null) {
            this.owning.add(book.getUuid());
            book.setOwner(this.username);
        }
    }

    /**
     * Remove a book from the user's possession
     * @param book
     */
    public void removeOwn(Book book) {
        if(book != null) {
            this.owning.remove(book.getUuid());
            book.setOwner(null);
        }
    }


    public void newRequested(Book book) {
        this.requesting.add(book.getUuid());
    }

    public void removeRequested(Book book) {
        this.requesting.remove(book.getUuid());
    }

    /**
     * If a notification is an accepted request, add book as a new book to borrow
     * @param book
     */
    public void newBorrow(Book book) {
        if(book != null) {
            this.borrowing.add(book.getUuid());
            this.borrowedHistory.add(book.getUuid());
            int i = 0;
            while(i< this.notifications.size()){
                Notification notification = notifications.get(i);
                if(notification.getTypeNotification().equals("Accepted Request")){
                    if(notification.BookRef().equals(book)){
                        this.notifications.remove(notification);
                        i = i-1;
                    }
                }
                i = i+1;
            }
            this.requesting.remove(book);
            book.borrow(this);
        }
    }

    /**
     * Unborrow book based on UUID
     * @param book
     */
    public void removeBorrow(Book book) {
        if(book != null) {
            this.borrowing.remove(book.getUuid());
            book.unborrow();
        }
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
        if(keywords!= null) {
            this.keywords = keywords;
        } else{
            this.keywords = new ArrayList<>();
        }

    }

    public KeywordTracker getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(KeywordTracker searchWords) {
        this.searchWords = searchWords;
    }

    /**
     * Returns array list of all books objects owned by
     * the user.
     * @return books owned.
     */
    public ArrayList<Book> BooksOwned() {
        Booklist books =  Booklist.getInstance();
        ArrayList<Book> temp = new ArrayList<>();
        for(String own : this.owning){
            int index = books.findIndex(own);
            if(index != -1){
                temp.add(books.get(index));
            }
        }
        return temp;
    }

    /**
     * Returns arraylist of all books owned that are available,
     * i.e. not currently accepted for borrow or borrowed.
     * @return list of available books
     */
    public ArrayList<Book> BooksOwnedAvailable() {
        Booklist books =  Booklist.getInstance();
        ArrayList<Book> temp = new ArrayList<>();
        for(String own : this.owning){
            int index = books.findIndex(own);
            if(index != -1){
                if (!books.get(index).isBorrowed()) {
                    if (books.get(index).getRequestedBy().isEmpty()) {
                        temp.add(books.get(index));
                    }
                }
            }
        }
        return temp;
    }

    /**
     * Returns arraylist of all books owned that are
     * currently borrowed by another user.
     * @return booklist of borrowed books
     */
    public ArrayList<Book> BooksOwnedBorrowed() {
        Booklist books =  Booklist.getInstance();
        ArrayList<Book> temp = new ArrayList<>();
        for(String own : this.owning){
            int index = books.findIndex(own);
            if(index != -1){
                if (books.get(index).isBorrowed()) {
                    temp.add(books.get(index));
                }
            }
        }
        return temp;
    }

    /**
     * Returns arraylist of all books owned that currently
     * have pending requests.
     * @return arraylist of pending requests
     */
    public ArrayList<Book> BooksOwnedRequested() {
        Booklist books =  Booklist.getInstance();
        ArrayList<Book> temp = new ArrayList<>();
        for(String own : this.owning){
            int index = books.findIndex(own);
            if(index != -1){
                if (!books.get(index).getRequestedBy().isEmpty()) {
                    temp.add(books.get(index));
                }
            }
        }
        return temp;
    }

    /**
     * Sets owning to what is provided
     * @param owning
     */
    public void setBooksOwned(ArrayList<Book> owning) {
        if(this.owning!= null) {
            this.owning.clear();
        } else{
            this.owning = new ArrayList<>();
        }
        for(Book own : owning){
            this.owning.add(own.getUuid());
        }
    }

    /**
     * For all the books owned, gets keywords from each book returning as an
     * arraylist of strings
     * @return
     */
    public ArrayList<String> getKeywordsFromBooks() {
        ArrayList<Book> owns = BooksOwned();
        ArrayList<String> keys = new ArrayList<>();
        for (int i = 0; i < owns.size(); i++) {
            Book book = owns.get(i);
            try {
                if (!book.getKeywords().isEmpty()) {
                    for (int j = 0; j < book.getKeywords().size(); j++) {
                        keys.add(book.getKeywords().get(j));
                    }
                }
            }
            catch (Exception e) {
                Log.e("Keywords", e.toString());
            }
        }
        return keys;
    }

    /**
     * Returns all borrowed books as an arraylist
     * @return
     */
    public ArrayList<Book> BorrowedBooks() {
        Booklist books =  Booklist.getInstance();
        ArrayList<Book> temp = new ArrayList<>();
        for(String borrow : this.borrowing){
            int index = books.findIndex(borrow);
            if(index != -1){
                temp.add(books.get(index));
            }
        }
        return temp;
    }
    public void setBorrowing(ArrayList<String> borrowing){
        this.borrowing = borrowing;
    }
    public ArrayList<String> getBorrowing(){
        return this.borrowing;
    }

    public ArrayList<String> getOwning(){
        return this.owning;
    }
    public void setOwning(ArrayList<String> owning){
        this.owning = owning;
    }
    public void setRequesting(ArrayList<String> requesting){
        this.requesting = requesting;
    }
    public ArrayList<String> getRequesting(){
        return this.requesting;
    }

    /**
     * sets borrowing to what is provided
     * @param borrowing
     */
    public void setBooksBorrowed(ArrayList<Book> borrowing) {
        if(this.borrowing!= null){
            this.borrowing.clear();
        } else{
            this.borrowing = new ArrayList<>();
        }
        for(Book borrow : borrowing){
            this.borrowing.add(borrow.getUuid());
        }
    }

    public ArrayList<String> getBorrowedHistory() {
        return borrowedHistory;
    }

    public void setBorrowedHistory(ArrayList<String> borrowedHistory) {
        this.borrowedHistory = borrowedHistory;
    }

    /**
     * Returns all books that are requested
     * @return
     */
    public ArrayList<Book> RequestedBooks() {
        Booklist books =  Booklist.getInstance();
        ArrayList<Book> temp = new ArrayList<>();
        for(String request : this.requesting){
            int index = books.findIndex(request);
            if(index != -1){
                temp.add(books.get(index));
            }
        }
        return temp;
    }

    /**
     * Sets requesting to what is provided
     * @param requesting
     */
    public void setRequestedBooks(ArrayList<Book> requesting) {
        if(this.requesting!=null) {
            this.requesting.clear();
        }
        else{
            this.requesting = new ArrayList<>();
        }
        for(Book b : requesting){
            this.requesting.add(b.getUuid());
        }
    }

    public void setNotifications(ArrayList<Notification> notifications){
        this.notifications = notifications;
    }
    public ArrayList<Notification> getNotifications(){
        return this.notifications;
    }
}
