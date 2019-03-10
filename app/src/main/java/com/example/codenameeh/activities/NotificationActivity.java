package com.example.codenameeh.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private ListView bookAcceptedList;
    private ListView requestedList;
    private ArrayList<String> booksAccepted = new ArrayList<String>();
    private ArrayList<String> requested = new ArrayList<String>();
    private ArrayAdapter<String> bookAcceptedAdapter;
    private ArrayAdapter<String> requestedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        for(int a = 0; a < 5; a++){
            booksAccepted.add("TEST");
        }
        bookAcceptedList = (ListView)findViewById(R.id.bookAcceptedList);
        requestedList = (ListView) findViewById(R.id.requestBookList);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        bookAcceptedAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,booksAccepted);
        requestedAdapter = new ArrayAdapter<String>(NotificationActivity.this,android.R.layout.simple_list_item_1,booksAccepted);
        bookAcceptedList.setAdapter(bookAcceptedAdapter);
        requestedList.setAdapter(requestedAdapter);
    }
    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
    }
}
