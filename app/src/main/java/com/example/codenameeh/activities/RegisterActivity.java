package com.example.codenameeh.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * @author Cole Boytinck
 * @version 1.0
 * The RegisterActivity is an activity that allows new users to register for the applicaation
 * When the user attempts to register, the activity checks the data to make sure it matches
 * the constraints, then sets up authentication via firebase, then saves the other data via firestore
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText viewUsername;
    private EditText viewPassword;
    private EditText viewPasswordMatch;
    private EditText viewName;
    private EditText viewEmail;
    private EditText viewPhone;
    private FirebaseAuth mAuth;


    /**
     * onCreate sets the data fields, and sets the button to trigger attemptRegister when
     * the data is valid
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        viewUsername = findViewById(R.id.username);
        viewPassword = findViewById(R.id.password);
        viewPasswordMatch = findViewById(R.id.passwordMatch);
        viewName = findViewById(R.id.name);
        viewEmail = findViewById(R.id.email);
        viewPhone = findViewById(R.id.phone);

        Button register = findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()) {
                    attemptRegister();
                }
            }
        });
    }


    /**
     * Attempt register takes the user info, and sets up authentication viw firebase,
     * then registers the user info in firestore aswell
     */
    public void attemptRegister() {
        String email = viewUsername.getText().toString() + "@codenameeh.ca";
        String password = viewPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            String name = viewName.getText().toString();
                            String phone = viewPhone.getText().toString();
                            String email = viewEmail.getText().toString();
                            String username = viewUsername.getText().toString();

                            User user = new User(name, phone, email, username);
                            db.collection("users").document(username).set(user);

                            updateUI(username);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * Changes the activity to the MainActivity
     */
    public void updateUI(String username) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * //TODO
     * @return true if the data is valid, false otherwise
     */
    public boolean validateData() {
        return true;
    }

}

