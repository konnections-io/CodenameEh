package com.example.codenameeh.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;

/**
 * @author Ryan Jensen
 *
 * Allows a passed in book (from Intent, with label book) to be edited, including photograph
 * Does not implement saving the changed book to Firebase, since pulling the Books appears to not
 * be functional.
 */
public class EditBookActivity extends AppCompatActivity {
    Button photoButton;
    Book book;
    EditText title;
    EditText author;
    EditText ISBN;
    EditText desc;

    /**
     * Create the layout of the page, and put the appropriate labels on the EditViews, button, and get the book from the Intent
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_edit_book);
        book = intent.getParcelableExtra("book");
        photoButton = findViewById(R.id.PhotoButton);
        title = findViewById(R.id.book_title_edit);
        author =  findViewById(R.id.book_author_edit);
        ISBN = findViewById(R.id.book_ISBN_edit);
        desc = findViewById(R.id.book_desc_edit);

    }

    /**
     * Define the default values for the EditTexts and the PhotoButton
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Set defaults in the EditTexts
        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        ISBN.setText(book.getISBN());
        desc.setText(book.getDescription());
        // Set photo button text to represent the actions available
        if (book.getPhotograph()==null){
            photoButton.setText("Click to add a photo");
        } else{
            photoButton.setText("Remove photo");
        }
    }

    /**
     * Add or delete the photograph path, depending on the status of a photograph being there.
     * Change button text if not going to another activity (say, to find the photograph
     * @param v
     */
    public void photoClick(View v){
        if (book.getPhotograph()==null){
            Intent chooseFile;
            Intent intent;
            chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("image/*");
            intent = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(intent, 1);
        } else{
            book.setPhotograph(null);
            photoButton.setText("Click to add a photo");
        }
    }

    /**
     * obtain the path of the selected file, put it as the book's Photograph. Changes Button text
     * for the photograph button
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                book.setPhotograph(uri);
                photoButton.setText("Remove photo");
            }
        }
    }
    /**
     * Return the edited book to the previous activity. Called on
     * @param v
     */
    public void saveClick(View v){
        Intent intent = new Intent();
        // Extract text from the EditTexts
        book.setTitle(title.getText().toString());
        book.setISBN(ISBN.getText().toString());
        book.setAuthor(author.getText().toString());
        book.setDescription(desc.getText().toString());

        intent.putExtra("book", book);
        setResult(RESULT_OK, intent);
        finish();
    }
}
