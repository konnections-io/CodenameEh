package com.example.codenameeh.classes;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Notification {
    private String geolocation = "NA";
    private String otherUser = "NA";
    private String bookTitle = "NA";
    private String typeNotification = "NA";
    private String bookInQuestion;
    private String uuid;
    private double latitude;
    private double longitude;
    private String date;


    public Notification(){
    }
    public Notification(String otherUser, Book book){
        this.bookInQuestion = book.getUuid();
        this.bookTitle = book.getTitle();
        this.otherUser = otherUser;
        this.uuid = UUID.randomUUID().toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
        date = dateFormat.format(c);
        this.typeNotification = "Borrow Request";
    }
    public Notification(String otherUser, String geolocation, Book book, double latitude, double longitude){
        this.bookInQuestion = book.getUuid();
        this.otherUser = otherUser;
        this.bookTitle = book.getTitle();
        this.geolocation = geolocation;
        this.uuid = UUID.randomUUID().toString();
        this.latitude = latitude;
        this.longitude = longitude;
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
        date = dateFormat.format(c);
        this.typeNotification = "Accepted Request";

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUuid(){
        return this.uuid;
    }
    public void setUuid(String uuid){
        this.uuid = uuid;
    }
    public Book BookRef(){
        int index = Booklist.getInstance().findIndex(bookInQuestion);
        if(index != -1){
            return Booklist.getInstance().get(index);
        } else{
            return null;
        }
    }
    public void setBookRef(Book book){
        this.bookInQuestion = book.getUuid();
    }
    public void setBook(String bookRef){
        this.bookInQuestion = bookRef;
    }
    public String getBook(){
        return this.bookInQuestion;
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
            return "User "+this.otherUser+"\nwould like to borrow book:\n"+this.bookTitle+
                    "\n"+this.date;
        }
        else{
            return "User "+this.otherUser+"\nhas accepted your request for book:\n"+this.bookTitle+
                    "\nGeolocation to receive is:\n"+this.geolocation+
                    "\n"+this.date;
        }
    }
}
