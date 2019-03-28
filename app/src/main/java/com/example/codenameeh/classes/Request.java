package com.example.codenameeh.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Request implements Parcelable {

    // accepted request = true
    // declined request = false
    private Boolean status;

    private User user;
    private Date date;
    private String formatDate;
    private Book book;

    public Request(){

    }

    public Request(User requester, Book requestBook){
        this.user = requester;
        this.date = new Date();
        this.book = requestBook;
        this.status = null;
    }
    public void accept(){
        this.status = true;
    }

    public void decline(){
        this.status = false;
    }

    public Boolean getStatus(){
        return this.status;
    }

    public String getDateRequested(){
        SimpleDateFormat strDate = new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm:ss a");

        this.formatDate = "Requested on: " + strDate.format(date);

        return formatDate;
    }

    public User getUser(){
        return user;
    }

    public Book getBook(){
        return book;
    }

    public String getBookUuid(){
        return book.getUuid();
    }

    protected Request(Parcel in) {
        status = in.readByte() != 0x00;
        user = (User) in.readValue(User.class.getClassLoader());
        long tmpDate = in.readLong();
        date = tmpDate != -1 ? new Date(tmpDate) : null;
        formatDate = in.readString();
        book = (Book) in.readValue(Book.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 0x01 : 0x00));
        dest.writeValue(user);
        dest.writeLong(date != null ? date.getTime() : -1L);
        dest.writeString(formatDate);
        dest.writeValue(book);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel in) {
            return new Request(in);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
}
