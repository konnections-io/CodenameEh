package com.example.codenameeh.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

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

        Intent intent = getIntent();
        String isbn = intent.getStringExtra("isbn");

        if(isbn != null) {
            inputDataFromScan(isbn);
        }
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

        EditText description = findViewById(R.id.editText4); // Description
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

    public void inputDataFromScan(String isbn) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;

        EditText isbnText = findViewById(R.id.editText3); // ISBN
        isbnText.setText(isbn);

        try {
            JSONObject json = new JSONObject(readUrl(url));
            String title = (String)json.getJSONObject("volumeInfo").get("title");
            String[] authors = (String[])json.getJSONObject("volumeInfo").get("authors");
            String desc = (String)json.get("description");


            EditText titleText = findViewById(R.id.editText); // title
            titleText.setText(title);

            EditText authorText = findViewById(R.id.editText2); // author
            authorText.setText(authors[0]);

            EditText descriptionText = findViewById(R.id.editText4); // Description
            descriptionText.setText(desc);

        } catch (Exception e) {
            Toast.makeText(TakeNewBookActivity.this, "Cannot find info online", Toast.LENGTH_SHORT)
                    .show();
            Log.e("readUrl", e.toString());
        }
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}
