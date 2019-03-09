package com.example.codenameeh.activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;

public class ProfileActivity extends BaseActivity {

    TextView viewUsername;
    TextView viewName;
    TextView viewPhone;
    TextView viewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile);
        getLayoutInflater().inflate(R.layout.activity_profile, frameLayout);

        String username, name, phone, email;
        Button edit  = findViewById(R.id.edit);

        Intent intent = getIntent();
        String message = intent.getStringExtra("username");

        if(message.equals(CurrentUser.getInstance().getUsername())) {
            edit.setVisibility(View.VISIBLE);
            //use CurrentUser info
            username = CurrentUser.getInstance().getUsername();
            name = CurrentUser.getInstance().getName();
            phone = CurrentUser.getInstance().getPhone();
            email = CurrentUser.getInstance().getEmail();
        } else {
            edit.setVisibility(View.GONE);
            //get user info from firebase
            //TODO
            username = "";
            name = "";
            phone = "";
            email = "";
        }

        viewUsername = findViewById(R.id.username);
        viewName = findViewById(R.id.name);
        viewPhone = findViewById(R.id.phone);
        viewEmail = findViewById(R.id.email);

        viewUsername.setText(username);
        viewName.setText(name);
        viewPhone.setText(phone);
        viewEmail.setText(email);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

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

}
