package com.example.codenameeh.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.Notification;
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends BaseActivity {
    public static final String CHANNEL_1_ID = "channel1";
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        //Get user information from firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()) {
                    User user = documentSnapshot.toObject(User.class);
                    //Set current user singleton
                    CurrentUser.setInstance(user);
                    Toast.makeText(MainActivity.this, "User Loaded.",
                            Toast.LENGTH_SHORT).show();
                    createNotificationChannels();
                    CurrentUser.getInstance().setNotifications(user.getNotifications());
                    addNotificationListener();
                } // Might need an OnFailure, since I keep sometimes having user = null
            }
        });

    }

    /**
     * Added by Brian Qi
     * Enables the app to have popup notifications for each new notification created via
     * borrow requests or requests accepted
     */
    public void addNotificationListener(){
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        final PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
        notificationManager = NotificationManagerCompat.from(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(CurrentUser.getInstance().getUsername());
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot != null && documentSnapshot.exists()){
                    User user = documentSnapshot.toObject(User.class);
                    ArrayList<String> comparingNotificationList = new ArrayList<String>();
                    for (Notification notification: CurrentUser.getInstance().getNotifications()){
                        comparingNotificationList.add(notification.toString());
                    }


                    for(Notification notification: user.getNotifications()){
                        if(!comparingNotificationList.contains(notification.toString())){
                            android.app.Notification notificationToSend = new NotificationCompat.Builder(MainActivity.this,CHANNEL_1_ID)
                                    .setSmallIcon(R.drawable.ic_test)
                                    .setContentTitle("New "+notification.getTypeNotification())
                                    .setContentText(notification.toString())
                                    .setPriority(NotificationCompat.PRIORITY_LOW)
                                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                                    .build();
                            notificationManager.notify(1,notificationToSend);

                        }
                    }
                    CurrentUser.getInstance().setNotifications(user.getNotifications());
                }
            }
        });
    }

    /**
     * Created by Brian Qi
     * Creates a single notification channel for requests and requests accepted
     */
    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("DESCRIPTION");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

}
