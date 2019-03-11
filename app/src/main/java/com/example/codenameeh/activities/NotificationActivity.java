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
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Qi
 * @version 1.0
 * NotificationActivity extends the BaseActivity class
 * The user can come here after clicking on the Notifications tab
 * on the base activity menu
 * Notifications for requests and accepted user requests are here
 * and are formatted accordingly
 * Currently to be implemented with buttons for requests
 */
public class NotificationActivity extends BaseActivity {

    private ListView bookAcceptedListView;
    private ListView requestedListView;
    private ArrayList<String> booksAccepted = new ArrayList<String>();
    private ArrayList<String> requested = new ArrayList<String>();
    private ArrayAdapter<String> bookAcceptedAdapter;
    private ArrayAdapter<String> requestedAdapter;


    /**
     * Listviews are identified here. Each listview contains a different type of notification
     * Currently must implement way to access Firebase and retrieve and fill listviews
     * with database notifications, test cases are used instead
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_notification, frameLayout);
        bookAcceptedListView = (ListView) findViewById(R.id.bookAcceptedList);
        requestedListView = (ListView) findViewById(R.id.requestBookList);
        
        //TESTS
        for(int a = 0; a < 2; a++) {
            CurrentUser.getInstance().getRequestAcceptedNotifications().add("arthur123", "Dictionary", "my house");
            CurrentUser.getInstance().getRequestAcceptedNotifications().add("barney", "Cook book", "Museum");
            CurrentUser.getInstance().getRequestAcceptedNotifications().add("charlie", "Dinosaurs", "23 Street");
            CurrentUser.getInstance().getOtherRequestNotifications().add("amanda", "knowledge");
            CurrentUser.getInstance().getOtherRequestNotifications().add("brett", "map");
            CurrentUser.getInstance().getOtherRequestNotifications().add("carl", "zoology");
        }

    }

    /**
     * Displays each visual notification via the user, sets up the 2 adapters
     * Populates each listview with the current user's 2 different notifications
     */
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        bookAcceptedAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,booksAccepted);
        requestedAdapter = new ArrayAdapter<String>(NotificationActivity.this,android.R.layout.simple_list_item_1,requested);


        /**
         * Visually display all notifications for when a request is accepted
         */
        for(String bookAcceptedNotification: CurrentUser.getInstance().getRequestAcceptedNotifications().getNotificationsList()){
            booksAccepted.add(bookAcceptedNotification);
        }
        for(String requestNotification: CurrentUser.getInstance().getOtherRequestNotifications().getNotificationsList()){
            requested.add(requestNotification);
        }
        bookAcceptedListView.setAdapter(bookAcceptedAdapter);
        requestedListView.setAdapter(requestedAdapter);
    }
}
