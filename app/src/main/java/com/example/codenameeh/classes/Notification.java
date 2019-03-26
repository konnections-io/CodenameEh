package com.example.codenameeh.classes;


public class Notification {
    private String geolocation = "NA";
    private String otherUser = "NA";
    private String bookTitle = "NA";
    private String typeNotification = "NA";
    private Book bookInQuestion;

    public Notification(){
        this.bookTitle = "hm";
        this.otherUser = "well";
    }
    public Notification(String otherUser, Book book){
        this.bookInQuestion = book;
        this.bookTitle = book.getTitle();
        this.otherUser = otherUser;
        this.typeNotification = "Borrow Request";
    }
    public Notification(String otherUser, String geolocation, Book book){
        this.bookInQuestion = book;
        this.otherUser = otherUser;
        this.bookTitle = book.getTitle();
        this.geolocation = geolocation;
        this.typeNotification = "Accepted Request";
    }
    public Book getBook(){
        return this.bookInQuestion;
    }
    public void setBook(Book book){
        this.bookInQuestion = book;
    }
    public String getTypeNotification(){
        return this.typeNotification;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setTypeNotification(String typeNotification){
        this.typeNotification = typeNotification;
    }

    @androidx.annotation.NonNull
    @Override
    public String toString() {
        if(this.typeNotification.equals("Borrow Request")){
            return "User "+this.otherUser+"\nwould like to borrow book:\n"+this.bookTitle;
        }
        else{
            return "User "+this.otherUser+"\nhas accepted your request for book:\n"+this.bookTitle+"\nGeolocation to receive is:\n"+this.geolocation;
        }
    }
}
