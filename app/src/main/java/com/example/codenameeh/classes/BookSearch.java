package com.example.codenameeh.classes;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class BookSearch {
    private User user;
    private Booklist allBooks = Booklist.getInstance();

    public BookSearch(User user) {
        this.user = user;
    }

    public ArrayList<Book> searchDatabase(String keyword) {
        ArrayList<Book> books = allBooks.getBookList();
        ArrayList<Book> filteredBooks = new ArrayList<>();

        for (Book book: books) {
            if (book.getTitle().contains(keyword) || book.getAuthor().contains(keyword)) {
                filteredBooks.add(book);
            }
        }

        return filteredBooks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
