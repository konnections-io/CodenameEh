package com.example.codenameeh.classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class KeywordTracker implements Parcelable {
    private ArrayList<String> keywords;

    public KeywordTracker() {
        this.keywords = new ArrayList<String>();
    }

    public void saveToDatabase(User user) {
        //TODO
    }

    public void add(String newKeyword){
        this.keywords.add(newKeyword);
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    protected KeywordTracker(Parcel in) {
        if (in.readByte() == 0x01) {
            keywords = new ArrayList<String>();
            in.readList(keywords, String.class.getClassLoader());
        } else {
            keywords = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (keywords == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(keywords);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<KeywordTracker> CREATOR = new Parcelable.Creator<KeywordTracker>() {
        @Override
        public KeywordTracker createFromParcel(Parcel in) {
            return new KeywordTracker(in);
        }

        @Override
        public KeywordTracker[] newArray(int size) {
            return new KeywordTracker[size];
        }
    };
}