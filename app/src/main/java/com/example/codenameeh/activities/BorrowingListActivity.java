package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.BooklistAdapter;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;

import java.util.ArrayList;

/**
 * @author Ryan Jensen
 * Views what the user is borrowing, and allows clicks for more details on the specific book
 *
 */
public class BorrowingListActivity extends BaseActivity {
    ArrayList<Book> ourBookList;
    BooklistAdapter adapter;
    User currentUser;
    ListView dataList;

    /**
     * Sets the layout of the BorrowingList, adds the onClick Listener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_borrowing_list, frameLayout);

        dataList = findViewById(R.id.BorrowingList);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BorrowingListActivity.this, ViewBookActivity.class);
                intent.putExtra("book", ourBookList.get(position));
                startActivity(intent);
            }
        });

    }

    /**
     * Obtain the data for the list, and input it into the adapter.
     */
    @Override
    protected void onStart() {
        super.onStart();
        currentUser = CurrentUser.getInstance();
        ourBookList = currentUser.BorrowedBooks();
        adapter = new BooklistAdapter(ourBookList);
        dataList.setAdapter(adapter);


    }
}
