package com.example.codenameeh.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class NotificationActivity extends BaseActivity {

    private ListView bookAcceptedList;
    private ListView requestedList;
    private ArrayList<String> booksAccepted = new ArrayList<String>();
    private ArrayList<String> requested = new ArrayList<String>();
    private ArrayAdapter<String> bookAcceptedAdapter;
    private ArrayAdapter<String> requestedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_notification, frameLayout);

        /**
         * We add each book accepted notification from the user to the listview
          */
        for(String bookAcceptedNotification: CurrentUser.getInstance().getRequestAcceptedNotifications().getNotificationsList()){
            booksAccepted.add(bookAcceptedNotification);
        }
        /**
         * This is for adding requests with accept or decline for request notifications
         */
        for(int a = 0; a < 5; a++){
            requested.add("NOT IMPLEMENTED");
        }
        bookAcceptedList = (ListView)findViewById(R.id.bookAcceptedList);
        requestedList = (ListView) findViewById(R.id.requestBookList);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        bookAcceptedAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,booksAccepted);
        requestedAdapter = new ArrayAdapter<String>(NotificationActivity.this,android.R.layout.simple_list_item_1,requested);
        bookAcceptedList.setAdapter(bookAcceptedAdapter);
        requestedList.setAdapter(requestedAdapter);
    }
    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
    }
}
