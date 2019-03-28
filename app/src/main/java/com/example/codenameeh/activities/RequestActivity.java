package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.Request;

/**
 * @author Dan Sune
 * @version 1.0
 * Request activity extends BaseActivity is used to view a specific request for a book.
 * The user can then choose to accept or decline the request to borrow one of their books.
 * Pass the Request you would like to view in the intent which calls this activity.
 */
public class RequestActivity extends BaseActivity {
    private static String NOTIFICATION_REQUEST = "NOTIFICATION REQUEST";
    Book book;
    String other_username;
    String sender;
    /**
     * onCreate displays the specific request information when it is clicked
     * also initiates the accept/decline buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Intent intent = getIntent();
        book = intent.getParcelableExtra("Book");
        sender = intent.getParcelableExtra("Sender");
        other_username = intent.getParcelableExtra("Other Username");

        Button acceptButton = findViewById(R.id.accept);
        Button declineButton = findViewById(R.id.decline);

        TextView username = findViewById(R.id.textView2);

        username.setText(sender);

        /**
         * Temporarily commented out what happens with accept button and decline button by Brian Qi.
         * Notification Activity calls onActivityResult after the click, acting depending on which
         * button was pressed
         */
        acceptButton.setOnClickListener(new View.OnClickListener(){

            /**
             * onClick handles the actions to be taken when the accept button is clicked
             * @param v
             */
            @Override
            public void onClick(View v){
                setResult(1);
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener(){

            /**
             * onClick handles the actions to be taken when the decline button is clicked
             * @param v
             */
            @Override
            public void onClick(View v){
                setResult(2);
            }
        });
        }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent.getStringExtra("Sender").equals(NOTIFICATION_REQUEST)) {
            book = intent.getParcelableExtra("Book");
            other_username = intent.getStringExtra("Other Username");
            TextView userField = findViewById(R.id.textView2);
            userField.setText(other_username);
        }

    }
}
