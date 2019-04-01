package com.example.codenameeh.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.Notification;
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * @author Dan Sune, Brian Qi
 * @version 1.0
 * Request activity extends BaseActivity is used to view a specific request for a book.
 * The user can then choose to accept or decline the request to borrow one of their books.
 * Pass the Request you would like to view in the intent which calls this activity.
 */
public class RequestActivity extends BaseActivity {
    private static String NOTIFICATION_REQUEST = "NOTIFICATION REQUEST";
    private static int GEOLOCATION_REQUEST_CODE = 1;

    Book book;
    String other_username,notificationUUID;
    /**
     * onCreate displays the specific request information when it is clicked
     * also initiates the accept/decline buttons
     * When accept button is clicked, take user to GeoLocation Activity to specify a meet up location.
     * When decline button is clicked, return to Notification Activity and remove the notification.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Button acceptButton = findViewById(R.id.accept);
        Button declineButton = findViewById(R.id.decline);

        Intent receiveIntent = getIntent();

        book = receiveIntent.getParcelableExtra("Book");
        other_username = receiveIntent.getParcelableExtra("Other Username");
        notificationUUID = receiveIntent.getParcelableExtra("UUID");

        acceptButton.setOnClickListener(new View.OnClickListener(){

            /**
             * onClick handles the actions to be taken when the accept button is clicked
             * @param v
             */
            @Override
            public void onClick(View v){
                boolean permissionGranted= ActivityCompat.checkSelfPermission(RequestActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
                if(!permissionGranted) {
                    ActivityCompat.requestPermissions(RequestActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
                    Toast.makeText(RequestActivity.this,"Sorry, you need to enable Locations to accept books.",Toast.LENGTH_LONG).show();

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
                final Intent intentToNotification = new Intent();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference ref = db.collection("users").document(CurrentUser.getInstance().getUsername());

                //Add relevant code here when user declines
                db.collection("users").document(other_username).update("requesting",
                                                                            FieldValue.arrayRemove(book.getUuid()));

                //Remove the notification to current user from FireStore
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                User user = document.toObject(User.class);
                                ArrayList<Notification> nList = user.getNotifications();
                                for(Notification n: nList){
                                    if(n.getUuid().equals(notificationUUID)){
                                        ref.update("notifications",FieldValue.arrayRemove(n));

                                    }
                                }

                            }
                        }
                    }
                });
                finish();
            }
        });
        }

    /**
     * Retrieves book object, username, notification UUID, and displays appropriate data on textview
     */
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent.getStringExtra("Sender").equals(NOTIFICATION_REQUEST)) {
            book = intent.getParcelableExtra("Book");
            other_username = intent.getStringExtra("Other Username");
            notificationUUID = intent.getStringExtra("UUID");
            TextView userField = findViewById(R.id.textView2);
            userField.setText(other_username);
        }

    }

    /**
     * If coming from Geolocation Activity, retrieve longitude, latitude, address, and send
     * notification to the corresponding user. Removes the notification from current user.
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                //Add relevant code when user clicks accept here instead of in the onClick method

                //can get status from book object
                book.setAcceptedStatus(true);
                book.removeAllRequests();
                book.setBorrower(other_username);
                intentToNotifications.putExtra("Book", book);

                //Add the notification to that user
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("All Books").document(book.getUuid()).set(book);
                final DocumentReference ref = db.collection("users").document(other_username);
                Notification newNotification = new Notification(CurrentUser.getInstance().getUsername()
                ,address,book,latitude,longitude);
                ref.update("notifications", FieldValue.arrayUnion(newNotification));
                final DocumentReference removeRef = db.collection("users").document(CurrentUser.getInstance().getUsername());
                removeRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                User user = document.toObject(User.class);
                                ArrayList<Notification> nList = user.getNotifications();
                                for(Notification n: nList){
                                    if(n.getTypeNotification().equals("Borrow Request")
                                            && n.BookRef().equals(book)){
                                        db.collection("users").document(n.getOtherUser())
                                                .update("requesting", FieldValue.arrayRemove(book.getUuid()));
                                        removeRef.update("notifications",FieldValue.arrayRemove(n));

                                    }
                                }

                            }
                        }
                    }
                });
                finish();
            }
        }
    }

    /**
     * Called when user has had to accept permission to access Locations. If user accepted, move
     * to Geolocation activity. Else display error message and cannot borrow.
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getApplicationContext(),GeolocationActivity.class);
                    startActivityForResult(intent,GEOLOCATION_REQUEST_CODE);
                } else {
                    Toast.makeText(RequestActivity.this,"Sorry, you need to enable Locations to accept books.",Toast.LENGTH_LONG).show();
                }
                return;
    }
}
