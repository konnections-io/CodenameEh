package classes;

import java.util.ArrayList;

public class Booklist {
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

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    public void setBookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }
}
