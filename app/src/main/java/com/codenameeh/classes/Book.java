package com.codenameeh.classes;

public class Book {
    private String title;
    private String author;
    private String ISBN;
    private String description;
    private String photograph; //filename of the image
    private boolean borrowed;

    public Book(String title, String author, String ISBN, String description, String photograph) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.description = description;
        this.photograph = photograph;
        this.borrowed = false;
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
}
