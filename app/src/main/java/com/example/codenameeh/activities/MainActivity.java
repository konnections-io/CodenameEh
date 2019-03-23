package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends BaseActivity {
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
                } // Might need an OnFailure, since I keep sometimes having user = null
            }
        });

    }

}
