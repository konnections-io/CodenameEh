package com.example.codenameeh.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Book implements Parcelable {
    private String title;
    private String author;
    private String ISBN;
    private String description;
    private String photograph; //filename of the image
    private String owner;
    private ArrayList<String> requestedBy;
    private boolean borrowed;
    // empty constructor for serial reconstruction
    public Book (){

    }
    public Book(String title, String author, String ISBN, String description, String photograph, String Owner) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.photograph = photograph;
        this.borrowed = false;
        this.owner = Owner;
        this.requestedBy = new ArrayList<>();
    }
    // no photograph
    public Book(String title, String author, String ISBN, String description, String Owner) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.photograph = null;
        this.borrowed = false;
        this.owner = Owner;
        this.requestedBy = new ArrayList<>();
    }
    public void addRequest(String user){
        this.requestedBy.add(user);
    }

    public void removeAllRequests(){
        this.requestedBy.clear();
    }

    public ArrayList<String> getRequestedBy(){
        return (ArrayList<String>) this.requestedBy.clone();
    }

    public void removeRequest(String user){
        this.requestedBy.remove(user);
    }

    public void setOwner(String owner){
        this.owner = owner;
    }
    public String getOwner(){
        return this.owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotograph() {
        return photograph;
    }

    public void setPhotograph(String photograph) {
        this.photograph = photograph;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        String output = "Title: "+this.title+ "\t\t\tAuthor: "+ this.author
                + "\t\t\t ISBN: " + this.ISBN;
        if (this.description != "") {
            output = output + "\nDescription: " + this.description;
        }
        if (this.borrowed) {
            output = output + "\nCURRENTLY BORROWED";
        }
        else {
            output = output + "\nAVAILABLE FOR BORROW";
        }
        return (output);
    }

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        ISBN = in.readString();
        description = in.readString();
        photograph = in.readString();
        owner =  in.readString();
        if (in.readByte() == 0x01) {
            requestedBy = new ArrayList<String>();
            in.readList(requestedBy, String.class.getClassLoader());
        } else {
            requestedBy = null;
        }
        borrowed = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(ISBN);
        dest.writeString(description);
        dest.writeString(photograph);
        dest.writeString(owner);
        if (requestedBy == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(requestedBy);
        }
        dest.writeByte((byte) (borrowed ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}