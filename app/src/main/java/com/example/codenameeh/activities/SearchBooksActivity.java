package com.example.codenameeh.activities;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
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
import java.util.Map;

import javax.annotation.Nullable;

public class SearchBooksActivity extends BaseActivity {

    private BookSearch newSearch = new BookSearch(CurrentUser.getInstance());
    private Map<String, Object> data;
    private Booklist bookList;
    private ListView filteredBooks;
    private ArrayAdapter<Book> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        getLayoutInflater().inflate(R.layout.activity_book_search, frameLayout);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        filteredBooks = (ListView) findViewById(R.id.filteredBookListView);

        final DocumentReference docRef = db.collection("Books").document("All");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(SearchBooksActivity.this, "Retrieving books failed.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    data = documentSnapshot.getData();
                    bookList = convertMapToBookList(data);
                } else {
                    Toast.makeText(SearchBooksActivity.this, "There are no books.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter = new ArrayAdapter<>(this, R.layout.list_book, bookList.getBookList());
        filteredBooks.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newSearch.searchDatabase(newText, bookList);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private Booklist convertMapToBookList(Map map) {
        Booklist books = new Booklist();
        // Uri image = Uri.parse("image.png");
        Book book1 = new Book("Book1", "name", "1235121", "Lorem Ipsum" , "user");
        books.add(book1);

        return books;
    }
}
