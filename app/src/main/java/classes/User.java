package classes;

import java.util.ArrayList;

public class User {
    private String name;
    private int phone;
    private String email;
    private String username;
    private String password;
    private ArrayList<String> keywords;
    private ArrayList<String> searchWords;
    private Booklist owning;
    private Booklist borrowing;
    private Booklist borrowedHistory;
    private Booklist requesting;

    public User(String name, int phone, String email, String username, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.owning = new Booklist();
        this.borrowing = new Booklist();
        this.borrowedHistory = new Booklist();
        this.requesting = new Booklist();
    }

    public void newOwn(Book book) {
        //TODO
    }

    public void removeOwn(Book book) {
        //TODO
    }

    public void newBorrow(Book book) {
        //TODO
    }

    public void removeBorrow(Book book) {
        //TODO
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<String> getSearchWords() {
        return searchWords;
    }

    public void setSearchWords(ArrayList<String> searchWords) {
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
}
