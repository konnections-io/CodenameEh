package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.BookSearch;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.SearchBooksAdapter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.Nullable;

/**
 * This activity allows the user to search for books through our entire book database. Books are
 * removed/filtered out if they're accepted or borrowed.
 * @author Daniel Shim, Brian Qi
 * @version 1.0
 */
public class SearchBooksActivity extends BaseActivity {

    private ListView search_book;
    private SearchBooksAdapter bookAdapter;
    private BookSearch newSearch;
    private Booklist allBooks = Booklist.getInstance();
    private ArrayList<Book> arrayBook;

    /** 
     * Called when activity is created. Gets all the books recorded on Firestore, sets the adapter,
     * and displays the books on the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_book_search, frameLayout);

        arrayBook = allBooks.getBookList();

        search_book = findViewById(R.id.search_books);

        newSearch = new BookSearch(CurrentUser.getInstance(), arrayBook);

        bookAdapter = new SearchBooksAdapter(this, R.layout.book_search_adapter_view, arrayBook);

        search_book.setAdapter(bookAdapter);
        search_book.setClickable(true);
        search_book.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchBooksActivity.this, ViewBookActivity.class);
                intent.putExtra("book", arrayBook.get(position));
                startActivityForResult(intent, 2);
            }
        });
    }

    /**
     * Called when the user enters this activity. Removes all books that are borrowed or accepted.
     * Adds all books that are available
     */
    @Override
    protected void onStart(){
        super.onStart();
        arrayBook = allBooks.getBookList();
        Iterator<Book> iter = arrayBook.iterator();
        while(iter.hasNext()){
            Book book = iter.next();
            if(book.isBorrowed()||book.getAcceptedStatus() || book.getOwner().equals(CurrentUser.getInstance().getUsername())) {
                iter.remove();
            }
        }
        bookAdapter = new SearchBooksAdapter(this, R.layout.book_search_adapter_view, arrayBook);
        search_book.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("All Books").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                arrayBook.clear();
                bookAdapter.notifyDataSetChanged();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
                    if (doc.exists()) {
                        Book book = doc.toObject(Book.class);
                        if (!book.isBorrowed() && !book.getAcceptedStatus()&&!book.getOwner().equals(CurrentUser.getInstance().getUsername())) {
                            arrayBook.add(book);
                            bookAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });

    }
  
    /**
     * Inflates the custom menu interface adding the SearchView into the menu.
     * @param menu The menu of the current activity
     * @return runs onCreateOptionsMenu for the original functionalities it provides
     */
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
                arrayBook = newSearch.searchDatabase(newText);
                bookAdapter.notifyDataSetChanged();
                bookAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
