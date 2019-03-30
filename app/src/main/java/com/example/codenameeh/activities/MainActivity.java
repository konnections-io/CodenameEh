package com.example.codenameeh.activities;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.Notification;
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MainActivity extends BaseActivity {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String GROUP_ID = CurrentUser.getInstance().getUsername();
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);


        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannels();
        addNotificationListener();


    }

    /**
     * Added by Brian Qi
     * Enables the app to have popup notifications for each new notification created via
     * borrow requests or requests accepted
     */
    public void addNotificationListener(){
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
                           if(!CurrentUser.getInstance().getUsername().equals(notification.getOtherUser())) {
                               sendNotification(notification);
                           }
                        }
                    }
                    CurrentUser.getInstance().setNotifications(user.getNotifications());
                }
            }
        });
    }
public void sendNotification(Notification notification){
    Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
    notificationManager = NotificationManagerCompat.from(MainActivity.this);
    final PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
    android.app.Notification notificationToSend = new NotificationCompat.Builder(MainActivity.this,CHANNEL_1_ID)
            .setSmallIcon(R.drawable.ic_test)
            .setContentTitle("New "+notification.getTypeNotification())
            .setContentText("Book: "+notification.getBookTitle())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(notification.toString()))
            .build();
    notificationManager.notify(1,notificationToSend);
}
    /**
     * Created by Brian Qi
     * Creates a single notification channel for requests and requests accepted
     */
    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannelGroup group = new NotificationChannelGroup(
                    GROUP_ID,"Group 1"
            );
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("DESCRIPTION");
            channel1.setGroup(GROUP_ID);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannelGroup(group);
            manager.createNotificationChannel(channel1);
        }
    }

}
