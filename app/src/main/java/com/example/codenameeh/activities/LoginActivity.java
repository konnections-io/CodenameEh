package com.example.codenameeh.activities;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * @author Cole Boytinck
 * @version 1.0
 * Login activity is the activity that get started on launch.
 * This activity allows the user to login to their accounr
 * The credentials are checked against the firebase auth
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText viewUsername;
    private EditText viewPassword;
    private FirebaseAuth mAuth;

    /**
     * onStart can give the functionality in the future to keep
     * people logged in even after they close the app
     */
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            //updateUI(currentUser);
            Log.d("TEST", currentUser.getDisplayName());
        }
    }

    /**
     * onCrease set the login form, and the login and register buttons
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Set up the login form.
        viewUsername = findViewById(R.id.username);
        viewPassword = findViewById(R.id.password);

        Button signIn = findViewById(R.id.sign_in);
        signIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button register = findViewById(R.id.register);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Attempt login checks the login credentials against the firebase auth
     */
    private void attemptLogin() {
        final String username = viewUsername.getText().toString() + "@codenameeh.ca";
        final String password = viewPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(viewUsername.getText().toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * updateUI sends the current user to the main screen of the app
     * @param username User that was validated
     */
    private void updateUI(String username) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
}

