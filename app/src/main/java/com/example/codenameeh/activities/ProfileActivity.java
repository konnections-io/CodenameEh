package com.example.codenameeh.activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * @author Cole Boytinck
 * @version 1.0
 * Profile Activity is used to view either your own profile information,
 * or can be used to view others profile information.
 * To pick the information shown, pass the username you want to view in
 * the intent passed to this activity.
 * If the username passed is the name as the the current user, a button
 * to edit your information is also shown.
 */
public class ProfileActivity extends BaseActivity {

    TextView viewUsername;
    TextView viewName;
    TextView viewPhone;
    TextView viewEmail;
    String username, name, phone, email;
    Button edit;

    /**
     * onCreate gets the intent, gets the users information that needs to be displayed,
     * and displays the data. It also sets a button in the user is the current user.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);

        edit  = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * onRestart resets the data shown, this will only get called when a user
     * goes from the EditProfileActivity back to this activity.
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        String name = CurrentUser.getInstance().getName();
        String phone = CurrentUser.getInstance().getPhone();
        String email = CurrentUser.getInstance().getEmail();

        viewName.setText(name);
        viewPhone.setText(phone);
        viewEmail.setText(email);
    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        String message = intent.getStringExtra("username");
        edit  = findViewById(R.id.edit);
        if(message.equals(CurrentUser.getInstance().getUsername())) {
            edit.setVisibility(View.VISIBLE);
            //use CurrentUser info
            username = CurrentUser.getInstance().getUsername();
            name = CurrentUser.getInstance().getName();
            phone = CurrentUser.getInstance().getPhone();
            email = CurrentUser.getInstance().getEmail();
            displayInformation(username,name,phone,email);
        } else {
            edit.setVisibility(View.GONE);

            //Get user information from firebase. Asynchronous, so cannot set values immediately
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("users").document(message);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        displayInformation(user.getUsername(),user.getName(),user.getPhone(),user.getEmail());
                    }
                }
            });
        }



    }
    private void displayInformation(String username, String name, String phone, String email){
        viewUsername = findViewById(R.id.username);
        viewName = findViewById(R.id.name);
        viewPhone = findViewById(R.id.phone);
        viewEmail = findViewById(R.id.email);

        viewUsername.setText(username);
        viewName.setText(name);
        viewPhone.setText(phone);
        viewEmail.setText(email);
    }

}
