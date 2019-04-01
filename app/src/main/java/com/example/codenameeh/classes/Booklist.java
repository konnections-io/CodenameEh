package com.example.codenameeh.classes;


import java.util.ArrayList;

/**
 * A Singleton to contain all books. Has many functions which are similar to an ArrayList.
 * @author Ryan Jensen
 */
public class Booklist {
    private ArrayList<Book> bookList;
    private static Booklist instance;

    private Booklist() {
        this.bookList = new ArrayList<Book>();
    }

    /**
     * Obtain the size of the booklist
     * @return
     */
    public int size() {
        return bookList.size();
    }

    /**
     * Add a Book to the Booklist
     * @param book
     */
    public void add(Book book) {
        bookList.add(book);
    }

    /**
     * Remove a Book from the Booklist
     * @param book
     */
    public void remove(Book book) {
        bookList.remove(book);
    }

    /**
     * Check whether a given book is contained in the Booklist
     * @param book
     * @return
     */
    public boolean contains(Book book) {
        return bookList.contains(book);
    }

    /**
     * Get the book at the given index
     * @param index
     * @return
     */
    public Book get(int index){return bookList.get(index);}

    /**
     * Returns an ArrayList of Books, for usages which require an ArrayList
     * @return
     */
    public ArrayList<Book> getBookList() {
        return (ArrayList<Book>) bookList.clone();
    }

    /**
     * Obtains the first index of the given book in the Booklist. Returns -1 if not found.
     * @param book
     * @return
     */
    public int indexOf(Book book){
        return bookList.indexOf(book);
    }

    /**
     * Replaces the entry at the given index with the passed element.
     * @param index
     * @param element
     * @return
     */
    public Book set(int index, Book element){
        return bookList.set(index, element);
    }

    /**
     * Finds the index of the Book with a uuid matching that of the given uuid. Returns -1 if none found.
     * @param uuid
     * @return
     */
    public int findIndex(String uuid){
        for(int i = 0; i<bookList.size();i++){
            if(bookList.get(i).getUuid().equals(uuid)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Obtain the instance of the Booklist
     * @return
     */
    public static Booklist getInstance() {
        return instance;
    }

    /**
     * Initializes the Booklist with the given ArrayList
     * @param newBookList
     */
    public static void setInstance(ArrayList<Book> newBookList) {
        if(instance == null){
            instance = new Booklist();
        }
        instance.bookList = newBookList;
    }
}