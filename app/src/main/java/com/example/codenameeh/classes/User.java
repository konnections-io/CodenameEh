package com.example.codenameeh.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {
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

    protected User(Parcel in) {
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        username = in.readString();
        if (in.readByte() == 0x01) {
            keywords = new ArrayList<String>();
            in.readList(keywords, String.class.getClassLoader());
        } else {
            keywords = null;
        }
        searchWords = (KeywordTracker) in.readValue(KeywordTracker.class.getClassLoader());
        owning = (Booklist) in.readValue(Booklist.class.getClassLoader());
        borrowing = (Booklist) in.readValue(Booklist.class.getClassLoader());
        borrowedHistory = (Booklist) in.readValue(Booklist.class.getClassLoader());
        requesting = (Booklist) in.readValue(Booklist.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(username);
        if (keywords == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(keywords);
        }
        dest.writeValue(searchWords);
        dest.writeValue(owning);
        dest.writeValue(borrowing);
        dest.writeValue(borrowedHistory);
        dest.writeValue(requesting);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}