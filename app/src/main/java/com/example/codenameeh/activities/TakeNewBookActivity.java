package com.example.codenameeh.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.codenameeh.R;

import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_AUTHOR;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_DESCRIPTION;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_ISBN;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_TITLE;
/**
 * @author Daniel Dick
 * @version 1.0
 * TakeNewBookActivity extends the BaseActivity class used
 * in this app.  This allows for all of the new books to be input
 * into the application in the Android operating
 * environment.  All actions and operations of the "new book"
 * data input screen of the app are contained here.
 * There are no outstanding issues here that I
 * have detected or am aware of.
 */
public class TakeNewBookActivity extends BaseActivity {
    /**
     * onCreate here initializes the layout of the activity screen
     * and calls the superclass constructor.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_new_book);
    }

    /**
     * newData is called when the button is pressed.  It will check
     * the input data for consistency, then return the newly entered
     * book to the booklist activity to be displayed for the user.
     * If the data is inconsistent the screen will not change and the user
     * will need to edit their data to be consistent before proceeding.
     * @param view
     */
    public void newData (View view) {
        EditText title = findViewById(R.id.editText); // title
        String message = title.getText().toString();

        EditText author = findViewById(R.id.editText2); // author
        String message1 = author.getText().toString();

        EditText isbn = findViewById(R.id.editText3); // ISBN
        String message2 = isbn.getText().toString();

        EditText description = findViewById(R.id.editText4); // Diastolic
        String message3 = description.getText().toString();

        if (true) { // If case added so that any input checks we want can be added here
            Intent intent = new Intent();
            intent.putExtra(EXTRA_MESSAGE_TITLE, message);
            intent.putExtra(EXTRA_MESSAGE_AUTHOR, message1);
            intent.putExtra(EXTRA_MESSAGE_ISBN, message2);
            intent.putExtra(EXTRA_MESSAGE_DESCRIPTION, message3);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
}
