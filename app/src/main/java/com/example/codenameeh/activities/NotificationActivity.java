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
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

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
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference ref = db.collection("users").document(CurrentUser.getInstance().getUsername());

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
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot != null && documentSnapshot.exists()){
                    requested.clear();
                    booksAccepted.clear();
                    for(Notification notification: CurrentUser.getInstance().getNotifications()){
                        if(notification.getTypeNotification().equals("Borrow Request")){
                            requested.add(notification.toString());
                        }
                        else if(notification.getTypeNotification().equals("Accepted Request")){
                            booksAccepted.add(notification.toString());
                        }
                    }
                    bookAcceptedListView.setAdapter(bookAcceptedAdapter);
                    requestedListView.setAdapter(requestedAdapter);

                }
            }
        });
//        ArrayList<Notification> nlist = new ArrayList<Notification>();
//        Notification n1 = new Notification("Alex","Boring","There");
//        Notification n2 = new Notification("Brian","Cooking");
//        nlist.add(n1);
//        nlist.add(n2);
//        ref.update("notifications",nlist);
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

    }

}
