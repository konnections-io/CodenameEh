package com.example.codenameeh.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);

        String username, name, phone, email;

        Intent intent = getIntent();
        String message = intent.getStringExtra("username");

        if(message.equals(CurrentUser.getInstance().getUsername())) {
            //use CurrentUser info
            username = CurrentUser.getInstance().getUsername();
            name = CurrentUser.getInstance().getName();
            phone = CurrentUser.getInstance().getPhone();
            email = CurrentUser.getInstance().getEmail();
        } else {
            //get user info from firebase
            //TODO
            username = "";
            name = "";
            phone = "";
            email = "";
        }

        TextView viewUsername = findViewById(R.id.username);
        viewUsername.setText(username);
        TextView viewName = findViewById(R.id.name);
        viewName.setText(name);
        TextView viewPhone = findViewById(R.id.phone);
        viewPhone.setText(phone);
        TextView viewEmail = findViewById(R.id.email);
        viewEmail.setText(email);
    }
}
