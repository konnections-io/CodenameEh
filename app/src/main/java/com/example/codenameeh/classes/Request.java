package com.example.codenameeh.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Request implements Parcelable {

    // accepted request = true
    // declined request = false
    private boolean status;

    private String user;
    private Date date;
    private String formatDate;
    private String book;

    public Request(){

    }

    public Request(String requester, String requestBook){
        this.user = requester;
        this.date = new Date();
        this.book = requestBook;
    }
    public void accept(){
        this.status = true;
    }

    public void decline(){
        this.status = false;
    }

    public boolean getStatus(){
        return this.status;
    }

    public String getDateRequested(){
        SimpleDateFormat strDate = new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm:ss a");

        this.formatDate = "Requested on: " + strDate.format(date);

        return formatDate;
    }

    public String getUser(){
        return user;
    }

    public String getBook(){
        return book;
    }

    protected Request(Parcel in){
        user = in.readString();
        book = in.readString();
        formatDate = in.readString();
        status = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(user);
        dest.writeString(book);
        dest.writeString(formatDate);
        dest.writeByte((byte) (status ? 1 : 0));
    }

    public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>(){
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
