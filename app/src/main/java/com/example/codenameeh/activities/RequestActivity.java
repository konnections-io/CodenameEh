package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Request;

/**
 * @author Dan Sune
 * @version 1.0
 * Request activity extends BaseActivity is used to view a specific request for a book.
 * The user can then choose to accept or decline the request to borrow one of their books.
 * Pass the Request you would like to view in the intent which calls this activity.
 */
public class RequestActivity extends BaseActivity {

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
        final Request request = (Request)getIntent().getParcelableExtra("data");

        String user = request.getUser();
        String book = request.getBook();
        String date = request.getDateRequested();
        Boolean status = request.getStatus();

        TextView tView = findViewById(R.id.textView2);
        tView.setText(user);

        Button acceptButton = findViewById(R.id.accept);
        Button declineButton = findViewById(R.id.decline);

        acceptButton.setOnClickListener(new View.OnClickListener(){

            /**
             * onClick handles the actions to be taken when the accept button is clicked
             * @param v
             */
            @Override
            public void onClick(View v){
                request.accept();
            }
        });

        declineButton.setOnClickListener(new View.OnClickListener(){

            /**
             * onClick handles the actions to be taken when the decline button is clicked
             * @param v
             */
            @Override
            public void onClick(View v){
                request.decline();
            }
        });
        }
}
