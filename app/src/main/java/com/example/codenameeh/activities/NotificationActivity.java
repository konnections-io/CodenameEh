package com.example.codenameeh.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.Notification;
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.ArrayList;
import java.util.Locale;
import javax.annotation.Nullable;

/**
 * @author Brian Qi
 * @version 1.0
 * NotificationActivity extends the BaseActivity class
 * The user can come here after clicking on the Notifications tab
 * on the base activity menu, or by clicking the popup notification
 * Notifications for requests and accepted user requests are here
 * and are formatted accordingly in a listview
 * When a notification is clicked, user is taken to a map to view the geolocation, or to accept/decline
 * a request depending on the type of notification. Types are accepted requests and borrow requests.
 */
public class NotificationActivity extends BaseActivity {
    private static String NOTIFICATION_REQUEST = "NOTIFICATION REQUEST";
    private ListView bookAcceptedListView;
    private ListView requestedListView;
    private ArrayList<Notification> booksAccepted = new ArrayList<Notification>();
    private ArrayList<Notification> requested = new ArrayList<Notification>();
    private ArrayAdapter<Notification> bookAcceptedAdapter;
    private ArrayAdapter<Notification> requestedAdapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference ref = db.collection("users").document(CurrentUser.getInstance().getUsername());

    /**
     * Method is called when user first enters this activity.
     * Listviews are identified and created here. Each listview contains a different type of
     * notification to be displayed. A snapshot listener is added for any changes to Firestore.
     * If such one occurs, update corresponding adapters. On click listeners are initialized here
     * for each listview, taking user to different activities. A clear button clears the accepted
     * requests page. Each notification has a date when it was received.
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
                    User user = documentSnapshot.toObject(User.class);
                    ArrayList<Notification> nList = user.getNotifications();
                    for(Notification notification: nList){

                        if(notification.getTypeNotification().equals("Borrow Request")){
                            requested.add(notification);
                        }
                        else if(notification.getTypeNotification().equals("Accepted Request")){
                            booksAccepted.add(notification);
                        }
                    }

                    bookAcceptedListView.setAdapter(bookAcceptedAdapter);
                    requestedListView.setAdapter(requestedAdapter);

                }
            }
        });
        requestedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NotificationActivity.this,RequestActivity.class);
                Notification notificationClicked = (Notification)requestedListView.getItemAtPosition(position);
                intent.putExtra("Sender",NOTIFICATION_REQUEST);
                intent.putExtra("Book",notificationClicked.getBook());
                intent.putExtra("UUID",notificationClicked.getUuid());
                intent.putExtra("Other Username",notificationClicked.getOtherUser());
                startActivity(intent);

            }
        });
        bookAcceptedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notification notificationClicked = (Notification)bookAcceptedListView.getItemAtPosition(position);
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", notificationClicked.getLatitude(), notificationClicked.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        Button clearAcceptedButton = findViewById(R.id.ClearAcceptedNotf);
        clearAcceptedButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                User user = document.toObject(User.class);
                                ArrayList<Notification> nList = user.getNotifications();
                                for(Notification n: nList){
                                    if(n.getTypeNotification().equals("Accepted Request")){
                                        ref.update("notifications",FieldValue.arrayRemove(n));

                                    }
                                }

                            }
                        }
                    }
                });
            }
        });
    }

    /**
     * Displays each visual notification via the user, sets up the 2 adapters
     * Populates each listview with the current user's 2 different notifications
     */
    @Override
    protected void onStart() {
        super.onStart();
        bookAcceptedAdapter = new ArrayAdapter<Notification>(this,android.R.layout.simple_list_item_1,booksAccepted);
        requestedAdapter = new ArrayAdapter<Notification>(NotificationActivity.this,android.R.layout.simple_list_item_1,requested);

    }

}
