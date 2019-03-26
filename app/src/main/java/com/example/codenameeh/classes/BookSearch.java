package com.example.codenameeh.classes;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class BookSearch {
    private User user;

    public BookSearch(User user) {
        this.user = user;
    }

    public Booklist searchDatabase(String keyword) {
        final Booklist filteredBooks = new Booklist();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference allBooks = db.collection("All Books");

        Query booksQuery = allBooks.whereEqualTo("acceptedStatus", false)
                .whereEqualTo("borrowed", false).whereEqualTo("author", keyword);
        booksQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        Book book = document.toObject(Book.class);
                        filteredBooks.add(book);
                    }
                } else {
                                        
                }
            }
        });

        booksQuery = allBooks.whereEqualTo("acceptedStatus", false)
                .whereEqualTo("borrowed", false).whereEqualTo("title", keyword);
        booksQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        Book book = document.toObject(Book.class);
                        filteredBooks.add(book);
                    }
                } else {

                }
            }
        });

        return filteredBooks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
