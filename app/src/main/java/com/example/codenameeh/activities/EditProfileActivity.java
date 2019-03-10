package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * @author Cole Boytinck
 * @version 1.0
 * The EditProfileActivity allows a user to edit their contact information.
 * When the contact information has been changed, the CurrentUser singleton is updated, and
 * information is uploaded to firebase
 */
public class EditProfileActivity extends AppCompatActivity {

    private EditText viewName;
    private EditText viewEmail;
    private EditText viewPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        viewName = findViewById(R.id.name);
        viewEmail = findViewById(R.id.email);
        viewPhone = findViewById(R.id.phone);

        viewName.setText(CurrentUser.getInstance().getName());
        viewPhone.setText(CurrentUser.getInstance().getPhone());
        viewEmail.setText(CurrentUser.getInstance().getEmail());

        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentUser.getInstance().setName(viewName.getText().toString());
                CurrentUser.getInstance().setEmail(viewEmail.getText().toString());
                CurrentUser.getInstance().setPhone(viewPhone.getText().toString());

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(CurrentUser.getInstance().getUsername())
                        .set(CurrentUser.getInstance());

                finish();
            }
        });
    }
}
