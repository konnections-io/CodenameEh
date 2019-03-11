package com.example.codenameeh.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;

/**
 * @author Ryan Jensen
 *
 * A beginning in editing the attributes of a Book, currently only works for photographs.
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

    /**
     * Add or delete the photograph path
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
     * obtain the path of the selected file, put it as the book's Photograph
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
     * Return the edited book to the previous activity
     * @param v
     */
    public void saveClick(View v){
        Intent intent = new Intent();
        // Needs to extract data from the EditViews
        intent.putExtra("book", book);
        setResult(RESULT_OK, intent);
        finish();
    }
}
