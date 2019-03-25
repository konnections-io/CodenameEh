package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;

import java.util.ArrayList;

/**
 * @author Brian Qi
 * @version 1.0
 * ViewRequestsActivity extends the BaseActivity class
 * Comes from the base activity by tapping the Requests tab
 * Views two lists of books: Both are from requested books,
 * but one is for pending and one is for already accepted.
 * Have not yet implemented bringing in the lists from the current user
 * Involves having requested books having a status boolean for accepted that's
 * different from other books to separate them into 2 lists.
 */

public class ViewRequestsActivity extends BaseActivity {
    private ListView acceptedView;
    private Booklist booksAccepted;
    private ArrayList<Book> booksAcceptedList;
    private ArrayAdapter<Book> booksAcceptedAdapter;

    private ListView pendingView;
    private Booklist booksPending;
    private ArrayList<Book> booksPendingList;
    private ArrayAdapter<Book> booksPendingAdapter;

    private int positionclicked;
    private User currentUser;

    /**
     * The onCreate method sets up the ListView with the listener
     * so that any selected books can be handled properly.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_requests, frameLayout);
        booksAcceptedList = new ArrayList<Book>();
        booksPendingList = new ArrayList<Book>();
        currentUser = CurrentUser.getInstance();
        acceptedView = findViewById(R.id.requestedAcceptedListView);
        acceptedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book selItem = (Book) parent.getItemAtPosition(position);
                positionclicked = position;
                Intent intent = new Intent(ViewRequestsActivity.this, ViewBookActivity.class);
                intent.putExtra("book", booksAcceptedList.get(position));
                startActivity(intent);
            }
        });

        pendingView = findViewById(R.id.requestedPendingListView);
        pendingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book selItem = (Book) parent.getItemAtPosition(position);
                positionclicked = position;
                Intent intent = new Intent (ViewRequestsActivity.this, ViewBookActivity.class);
                intent.putExtra("book",booksPendingList.get(position));
                startActivity(intent);
            }
        });

    }
    /**
     * The onStart method sets up the adapter and retrieves from
     * the current user the requested book list. The method then
     * separates each book into two separate book lists to be used by two
     * different adapters in two different listviews
     */
    @Override
    protected void onStart() {
        super.onStart();


            for (Book requestedBook : currentUser.RequestedBooks()) {
                if (requestedBook.getAcceptedStatus()) {
                    booksAccepted.add(requestedBook);
                } else {
                    booksPending.add(requestedBook);
                }
            }

            booksAcceptedAdapter = new ArrayAdapter<Book>(this, R.layout.list_item, booksAcceptedList);
            acceptedView.setAdapter(booksAcceptedAdapter);

            booksPendingAdapter = new ArrayAdapter<Book>(this, R.layout.list_item, booksPendingList);
            pendingView.setAdapter(booksPendingAdapter);

        }



}

