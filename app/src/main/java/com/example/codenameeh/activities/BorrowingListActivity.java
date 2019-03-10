package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;

public class BorrowingListActivity extends BaseActivity {
    Booklist ourBookList;
    // BooklistAdapter adapter;
    User currentUser;
    ListView dataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowing_list);

         dataList = findViewById(R.id.BorrowingList);

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = CurrentUser.getInstance();
        ourBookList = currentUser.getBorrowing();
        // adapter = new        Depends on how I get this presented, might need an alternate adapter
        // either way, needs a small book view activity, for the items
        // dataList.setAdapter(adapter);
        /* relies on how to view books, this variation requires book be parcelable
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BorrowingListActivity.this, BookView.class);
                intent.putExtra("book", ourBookList.getBookList().get(position));
                startActivity(intent);
            }
        });
        */
    }
}
