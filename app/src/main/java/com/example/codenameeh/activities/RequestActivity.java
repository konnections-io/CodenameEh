package com.example.codenameeh.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.Notification;
import com.example.codenameeh.classes.Request;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * @author Dan Sune
 * @version 1.0
 * Request activity extends BaseActivity is used to view a specific request for a book.
 * The user can then choose to accept or decline the request to borrow one of their books.
 * Pass the Request you would like to view in the intent which calls this activity.
 */
public class RequestActivity extends BaseActivity {
    private static String NOTIFICATION_REQUEST = "NOTIFICATION REQUEST";
    private static int GEOLOCATION_REQUEST_CODE = 1;

    Book book;
    String other_username;
    /**
     * onCreate displays the specific request information when it is clicked
     * also initiates the accept/decline buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Button acceptButton = findViewById(R.id.accept);
        Button declineButton = findViewById(R.id.decline);

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
                boolean permissionGranted= ActivityCompat.checkSelfPermission(RequestActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                if(!permissionGranted) {
                    Toast.makeText(RequestActivity.this,"Sorry, you need to enable Locations to accept books.",Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(RequestActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                }else{
                    Intent intent = new Intent(getApplicationContext(),GeolocationActivity.class);
                    startActivityForResult(intent,GEOLOCATION_REQUEST_CODE);
                }

            }
        });

        declineButton.setOnClickListener(new View.OnClickListener(){

            /**
             * onClick handles the actions to be taken when the decline button is clicked
             * @param v
             */
            @Override
            public void onClick(View v){
                //request.decline();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && !(data==null)) {
            if (requestCode == GEOLOCATION_REQUEST_CODE) {
                double latitude = data.getDoubleExtra("Latitude",0.0);
                double longitude = data.getDoubleExtra("Longitude",0.0);
                String address = data.getStringExtra("Address");
                Intent intentToNotifications = new Intent();
                //After accept button is requested, goes to geolocation.
                //After user selects a geolocation, lat/lon and address is sent here
                //in an Intent object
                //Code must now go back to Notifications Activity carrying back the info
                //that the user clicked Accept
                //This code is accessed only if the user clicked accept



                //Add the notification to that user
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference ref = db.collection("users").document(other_username);
                Notification newNotification = new Notification(CurrentUser.getInstance().getUsername()
                ,address,book);
                ref.update("notifications", FieldValue.arrayUnion(newNotification));
                setResult(Activity.RESULT_OK,intentToNotifications);
                finish();
            }
        }
    }
}
