package com.example.codenameeh.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Booklist implements Parcelable {
    private ArrayList<Book> bookList;

    public Booklist() {
        this.bookList = new ArrayList<Book>();
    }

    public int size() {
        return bookList.size();
    }

    public void add(Book book) {
        bookList.add(book);
    }

    public void remove(Book book) {
        bookList.remove(book);
    }

    public boolean contains(Book book) {
        return bookList.contains(book);
    }

    public Book get(int index){return bookList.get(index);}

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    protected Booklist(Parcel in) {
        if (in.readByte() == 0x01) {
            bookList = new ArrayList<Book>();
            in.readList(bookList, Book.class.getClassLoader());
        } else {
            bookList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (bookList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(bookList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Booklist> CREATOR = new Parcelable.Creator<Booklist>() {
        @Override
        public Booklist createFromParcel(Parcel in) {
            return new Booklist(in);
        }

        @Override
        public Booklist[] newArray(int size) {
            return new Booklist[size];
        }
    };
}