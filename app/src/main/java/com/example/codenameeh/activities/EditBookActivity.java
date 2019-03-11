package com.example.codenameeh.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;

/**
 * @author Ryan Jensen
 *
 * A beginning in editing the attributes, only deletes a photo if one exists
 */
public class EditBookActivity extends AppCompatActivity {
    Button photoButton;
    Book book;
    @Override
    /**
     * Create the
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_edit_book);
        book = intent.getParcelableExtra("book");
        photoButton = findViewById(R.id.PhotoButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (book.getPhotograph()==null){
            photoButton.setText("Click to add a photo");
        } else{
            photoButton.setText("Remove photo");
        }
        // TODO other edit functionality
    }

    public void photoClick(View v){
        if (book.getPhotograph()==null){
            // 
        } else{
            book.setPhotograph(null);
        }
    }
    public void
}
