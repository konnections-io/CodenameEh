package com.example.codenameeh.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.BookSearch;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.BooklistAdapter;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.SearchBooksAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * This activity allows the user to search for books through our entire book database.
 * @author Daniel Shim
 * @version 1.0
 */
public class SearchBooksActivity extends BaseActivity {

    private ListView search_book;
    private SearchBooksAdapter bookAdapter;
    private BookSearch newSearch = new BookSearch(CurrentUser.getInstance());
    private Booklist EveryBook = Booklist.getInstance();
    private ArrayList<Book> arrayBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_book_search, frameLayout);

        search_book = (ListView) findViewById(R.id.search_books);

        arrayBook = EveryBook.getBookList();
        //arrayBook.addAll(Arrays.asList(getResources().getStringArray(R.array.my_books)));

        bookAdapter = new SearchBooksAdapter(this, R.layout.book_search_adapter_view, arrayBook);

        search_book.setAdapter(bookAdapter);
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
                bookAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
