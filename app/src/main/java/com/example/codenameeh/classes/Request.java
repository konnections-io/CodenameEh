package com.example.codenameeh.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The request class is used to represent & store
 * book requests made between users.
 */
public class Request implements Parcelable {

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
    }

    /**
     * this method sets the current request status to accepted
     */
    public void accept(){
        this.status = true;
    }

    /**
     * this method sets the current request status to declined
     */
    public void decline(){
        this.status = false;
    }

    /**
     * returns the current status of request
     * @return Boolean which represents if request is accepted (true) or declined (false)
     */
    public Boolean getStatus(){
        return this.status;
    }


    /**
     * Displays the date of the request in a clean format
     * @return the String of the formatted date
     */
    public String getDateRequested(){
        SimpleDateFormat strDate = new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm:ss a");

        this.formatDate = "Requested on: " + strDate.format(date);

        return formatDate;
    }

    /**
     * returns the User object that is attached to the request
     * @return User
     */
    public User getUser(){
        return user;
    }

    /**
     * returns the Book object that is attached to the request
     * @return
     */
    public Book getBook(){
        return book;
    }

    /**
     * All code below this comment uses parcelabler.com to allow use of Parcel for this class.
     * Needed when request object is passed to other activities
     * @param in
     */
    protected Request(Parcel in) {
        byte statusVal = in.readByte();
        status = statusVal == 0x02 ? null : statusVal != 0x00;
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
        if (status == null) {
            dest.writeByte((byte) (0x02));
        } else {
            dest.writeByte((byte) (status ? 0x01 : 0x00));
        }
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