package com.example.codenameeh.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.example.codenameeh.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchBooksActivity extends BaseActivity {

    ListView search_book;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_book_search, frameLayout);

        search_book = (ListView) findViewById(R.id.search_books);

        ArrayList<String> arrayBook = new ArrayList<>();
        arrayBook.addAll(Arrays.asList(getResources().getStringArray(R.array.my_books)));

        adapter = new ArrayAdapter<String>(SearchBooksActivity.this, android.R.layout.simple_list_item_1, arrayBook);

        search_book.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search_book_items);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}
