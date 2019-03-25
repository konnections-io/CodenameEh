package com.example.codenameeh.classes;


import java.util.ArrayList;

public class Booklist {
    private ArrayList<Book> bookList;
    private static Booklist instance;

    private Booklist() {
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

    public int indexOf(Book book){
        return bookList.indexOf(book);
    }

    public Book set(int index, Book element){
        return bookList.set(index, element);
    }

    public int findIndex(String uuid){
        for(int i = 0; i<bookList.size();i++){
            if(bookList.get(i).getUuid().equals(uuid)){
                return i;
            }
        }
        return -1;
    }
    public static Booklist getInstance() {
        return instance;
    }

    public static void setInstance(ArrayList<Book> newBookList) {
        if(instance == null){
            instance = new Booklist();
        }
        instance.bookList = newBookList;
    }
}