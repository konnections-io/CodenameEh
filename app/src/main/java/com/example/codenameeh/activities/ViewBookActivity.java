package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.codenameeh.R;

import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_AUTHOR;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_DELETE;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_DESCRIPTION;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_ISBN;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_STATUS;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_TITLE;

/**
 * @author Daniel Dick
 * @version 1.0
 * ViewBookActivity extends the Activity class used
 * in this app.  This allows for any of the user's
 * books to be viewed.
 * The new screen activity will show the Book in its entirety,
 * and give the user access to the delete option.  If the user deletes
 * the Book, it will return to the main book list page with
 * the Book deleted.
 * There are no outstanding issues here that I
 * have detected or am aware of.
 */
public class ViewBookActivity extends BaseActivity {
    /**
     * the onCreate function unpacks the intent and sets
     * the textview fields with the proper values to display
     * the book details.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

        Intent intent = getIntent();
        String title = intent.getStringExtra(EXTRA_MESSAGE_TITLE);
        String author = intent.getStringExtra(EXTRA_MESSAGE_AUTHOR);
        String isbn = intent.getStringExtra(EXTRA_MESSAGE_ISBN);
        String desc = intent.getStringExtra(EXTRA_MESSAGE_DESCRIPTION);
        String status = intent.getStringExtra(EXTRA_MESSAGE_STATUS);
        TextView txtView = findViewById(R.id.textView4);
        txtView.setText(title);

        TextView txtView2 = findViewById(R.id.textView5);
        txtView2.setText(author);

        TextView txtView3 = findViewById(R.id.textView6);
        txtView3.setText(isbn);

        TextView txtView4 = findViewById(R.id.textView7);
        txtView4.setText(desc);

        TextView txtView5 = findViewById(R.id.textView3);
        txtView5.setText(status);
    }

    /**
     * on the button press, deleteBook is called and
     * returns from the activity with an intent to delete
     * the book.
     * @param view
     */
    public void deleteBook(View view) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_MESSAGE_DELETE, "TRUE");
        setResult(RESULT_OK, intent);
        finish();
    }
}
