package com.example.codenameeh.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        CurrentUser.setInstance("Tester", "(780) 123-4567", "test@test.com", "TestAccount");

    }

}
