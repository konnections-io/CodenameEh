package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;

import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_AUTHOR;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_DELETE;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_DESCRIPTION;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_ISBN;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_STATUS;
import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_TITLE;

/**
 * @author Daniel Dick, Ryan Jensen
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
     * Changes edit, delete and request buttons depending on the user accessing
     * @param savedInstanceState
     */
    Book book;
    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_view_book, frameLayout);

        Intent intent = getIntent();
        book = intent.getParcelableExtra("book");
        currentUser = CurrentUser.getInstance();
        Button requestButton= findViewById(R.id.requestBookButton);
        Button deleteButton = findViewById(R.id.deleteBookButton);
        Button editButton = findViewById(R.id.editBookButton);
        if(currentUser.getUsername().equals(book.getOwner())) {
            // We are the owner of the book, so show the owning buttons
            deleteButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
            requestButton.setVisibility(View.INVISIBLE);
        } else if(currentUser.getRequesting().contains(book)){
            // We are currently requesting the book, allow cancelling requests
            deleteButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            requestButton.setVisibility(View.VISIBLE);
            requestButton.setText("Cancel Request");
        } else if(book.isBorrowed()){
            // Disable requesting
            deleteButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            requestButton.setVisibility(View.INVISIBLE);
        } else{
            // enable requesting
            deleteButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            requestButton.setVisibility(View.VISIBLE);
        }
        TextView txtView = findViewById(R.id.textView4);
        txtView.setText(book.getTitle());

        TextView txtView2 = findViewById(R.id.textView5);
        txtView2.setText(book.getAuthor());

        TextView txtView3 = findViewById(R.id.textView6);
        txtView3.setText(book.getISBN());

        TextView txtView4 = findViewById(R.id.textView7);
        txtView4.setText(book.getDescription());

        TextView txtView5 = findViewById(R.id.textView3);
        String availabilityText;
        if(book.isBorrowed()){
            availabilityText = "Borrowed";
        } else if(currentUser.getRequesting().contains(book)){
            availabilityText = "Requested";
        } else{
            availabilityText = "Available";
        }
        txtView5.setText(availabilityText);
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

    public void changeRequestStatus(View v){
        if(currentUser.getRequesting().contains(book)){
            currentUser.getRequesting().remove(book);
            book.removeRequest(currentUser.getUsername());
        } else{
            currentUser.getRequesting().add(book);
            book.addRequest(currentUser.getUsername());
        }
    }
}
