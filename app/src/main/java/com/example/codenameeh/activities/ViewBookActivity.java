package com.example.codenameeh.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import static com.example.codenameeh.activities.BookListActivity.EXTRA_MESSAGE_DELETE;

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
    Button requestButton;
    TextView userView;
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_view_book, frameLayout);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Intent intent = getIntent();
        book = intent.getParcelableExtra("book");
        currentUser = CurrentUser.getInstance();
        requestButton = findViewById(R.id.requestBookButton);
        Button deleteButton = findViewById(R.id.deleteBookButton);
        Button editButton = findViewById(R.id.editBookButton);
        userView = findViewById(R.id.book_owner_view);
        if (currentUser.getUsername().equals(book.getOwner())) {
            // We are the owner of the book, so show the owning buttons
            deleteButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
            requestButton.setVisibility(View.INVISIBLE);
            // We don't need to know the owner here
            userView.setVisibility(View.INVISIBLE);
        } else if (currentUser.getRequesting().contains(book)) {
            // We are currently requesting the book, allow cancelling requests
            deleteButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            requestButton.setVisibility(View.VISIBLE);
            requestButton.setText("Cancel Request");
        } else if (book.isBorrowed()) {
            // Disable requesting
            deleteButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            requestButton.setVisibility(View.INVISIBLE);
        } else {
            // enable requesting
            deleteButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            requestButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView txtView = findViewById(R.id.book_title_view);
        txtView.setText("Title: "+book.getTitle());

        TextView txtView2 = findViewById(R.id.book_author_view);
        txtView2.setText("Author: "+book.getAuthor());

        TextView txtView3 = findViewById(R.id.book_ISBN_view);
        txtView3.setText("ISBN: "+book.getISBN());

        TextView txtView4 = findViewById(R.id.book_description);
        txtView4.setText("Description: "+book.getDescription());

        TextView txtView5 = findViewById(R.id.book_availability_view);
        String availabilityText = "Availability: ";
        if(book.isBorrowed()){
            availabilityText = availabilityText+ "Borrowed";
        } else if(currentUser.getRequesting().contains(book)){
            availabilityText = availabilityText+"Requested";
        } else{
            availabilityText = availabilityText+"Available";
        }
        txtView5.setText(availabilityText);

        userView.setText("Owner: "+book.getOwner());
        ImageView photo = findViewById(R.id.book_photo);
        if(book.getPhotograph()==null){
            // no photograph
            photo.setVisibility(View.INVISIBLE);
        } else{
            photo.setVisibility(View.VISIBLE);
            Bitmap image = null;
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), book.getPhotograph());
            } catch (IOException e) {
                e.printStackTrace();
            }
            photo.setImageBitmap(image);
        }
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

    /**
     * on the button press, request (or cancel the request) for the book
     * @param v
     */
    public void changeRequestStatus(View v){
        if(currentUser.getRequesting().contains(book)){
            currentUser.getRequesting().remove(book);
            book.removeRequest(currentUser.getUsername());
            requestButton.setText("Request");
        } else{
            currentUser.getRequesting().add(book);
            book.addRequest(currentUser.getUsername());
            requestButton.setText("Cancel Request");
        }
    }

    /**
     * on the button press, open a new activity to edit the details of the book
     * @param v
     */
    public void editBook(View v){
        Intent intent = new Intent(this, EditBookActivity.class);
        intent.putExtra("book", book);
        startActivityForResult(intent, 1);
    }
    /**
     * Update the data we could have changed in the Edit Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && !(data==null)) {
            Book newBook = data.getParcelableExtra("book");
            if (newBook != null) {
                book = newBook;
            }
        }
    }
}
