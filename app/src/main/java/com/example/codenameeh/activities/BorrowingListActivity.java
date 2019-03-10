package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.BooklistAdapter;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;

public class BorrowingListActivity extends BaseActivity {
    Booklist ourBookList;
    BooklistAdapter adapter;
    User currentUser;
    ListView dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_borrowing_list, frameLayout);

         dataList = findViewById(R.id.BorrowingList);

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = CurrentUser.getInstance();
        ourBookList = currentUser.getBorrowing();
        adapter = new BooklistAdapter(ourBookList);
        dataList.setAdapter(adapter);
        /* relies on how to view books, this variation requires book be parcelable
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BorrowingListActivity.this, BookView.class);
                intent.putExtra("book", ourBookList.get(position));
                startActivity(intent);

            }
        });*/

    }
}
