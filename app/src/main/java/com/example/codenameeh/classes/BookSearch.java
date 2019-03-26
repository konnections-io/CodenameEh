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
    private DocumentSnapshot lastQueriedAuthor;
    private DocumentSnapshot lastQueriedTitle;

    public BookSearch(User user) {
        this.user = user;
    }

    public ArrayList<Book> searchDatabase(String keyword) {
        final ArrayList<Book> filteredBooks = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference allBooks = db.collection("All Books");

        Query booksQuery = null;
        if (lastQueriedAuthor != null) {
            booksQuery = allBooks.whereEqualTo("acceptedStatus", false)
                    .whereEqualTo("borrowed", false).whereEqualTo("author", keyword)
                    .orderBy("title", Query.Direction.ASCENDING)
                    .startAfter(lastQueriedAuthor);
        } else {
            booksQuery = allBooks.whereEqualTo("acceptedStatus", false)
                    .whereEqualTo("borrowed", false).whereEqualTo("author", keyword)
                    .orderBy("title", Query.Direction.ASCENDING);
        }

        booksQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document: task.getResult()) {
                        Book book = document.toObject(Book.class);
                        filteredBooks.add(book);
                    }
                    if (task.getResult().size() != 0) {
                        lastQueriedAuthor = task.getResult().getDocuments()
                                .get(task.getResult().size() - 1);
                    }
                }
            }
        });

        booksQuery = null;
        if (lastQueriedTitle != null) {
            booksQuery = allBooks.whereEqualTo("acceptedStatus", false)
                    .whereEqualTo("borrowed", false).whereEqualTo("title", keyword)
                    .orderBy("title", Query.Direction.ASCENDING)
                    .startAfter(lastQueriedTitle);
        } else {
            booksQuery = allBooks.whereEqualTo("acceptedStatus", false)
                    .whereEqualTo("borrowed", false).whereEqualTo("title", keyword)
                    .orderBy("title", Query.Direction.ASCENDING);
        }

        booksQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Book book = document.toObject(Book.class);
                        filteredBooks.add(book);
                    }
                    if (task.getResult().size() != 0) {
                        lastQueriedTitle = task.getResult().getDocuments()
                                .get(task.getResult().size() - 1);
                    }
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
