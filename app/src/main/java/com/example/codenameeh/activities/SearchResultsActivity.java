package com.example.codenameeh.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.codenameeh.R;

import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.BookSearch;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.CurrentUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

public class SearchResultsActivity extends BaseActivity {

    private Booklist filteredBookList;
    private Map<String, Object> data;
    private ListView filteredBookListView;
    private ArrayList<Book> bookList = new ArrayList<>();
    private ArrayAdapter<Book> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_book_search, frameLayout);
        handleIntent(getIntent());

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        filteredBookListView = (ListView) findViewById(R.id.filteredBookListView);

        final DocumentReference docRef = db.collection("Books").document("All");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(SearchResultsActivity.this, "Retrieving books failed.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    data = documentSnapshot.getData();
                    filteredBookList = convertMapToBookList(data);
                } else {
                    Toast.makeText(SearchResultsActivity.this, "There are no books.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter = new ArrayAdapter<Book>(this, R.layout.list_book, (List<Book>) filteredBookList);
        filteredBookListView.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            BookSearch newSearch = new BookSearch(CurrentUser.getInstance());
            newSearch.searchDatabase(query, filteredBookList);
        }
    }

    private Booklist convertMapToBookList(Map map) {
        Booklist mapToBooks = new Booklist();
        Book book1 = new Book("Book1", "name", "12345678702sdf", "Lorem Ipsum", "image.png");
        mapToBooks.add(book1);

        return mapToBooks;
    }
}
