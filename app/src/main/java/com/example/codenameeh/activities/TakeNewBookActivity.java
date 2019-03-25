package com.example.codenameeh.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.CurrentUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.json.JSONArray;
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
    String photograph = null;
    FirebaseStorage storage;
    StorageReference storageRef;
    Button photoButton;
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
        Log.e("TestScan", "Recieved Intent");
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        photoButton = findViewById(R.id.new_book_photo);
        if(isbn != null) {
            EditText isbnText = findViewById(R.id.editText3); // ISBN
            isbnText.setText(isbn);

            Log.e("TestScan", "Inside If");
            new DownloadBookDetails().execute(isbn);
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
            intent.putExtra("photo", photograph);
            setResult(RESULT_OK, intent);
            finish();
        }

    }
    /**
     * Add or delete the photograph path, depending on the status of a photograph being there.
     * Change button text if not going to another activity (say, to find the photograph
     * @param v
     */
    public void photoClick(View v){
        if (photograph==null){
            Intent chooseFile;
            Intent intent;
            chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
            chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
            chooseFile.setType("image/*");
            intent = Intent.createChooser(chooseFile, "Choose a file");
            startActivityForResult(intent, 1);
        } else{
            StorageReference photoRef = storageRef.child(CurrentUser.getInstance().getUsername() +"/"+photograph);
            photoRef.delete().addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // An error occurred. Log it
                    Log.e("TakeNewBookActivity", e.getStackTrace().toString());
                }
            });
            photograph = null;
            photoButton.setText("Click to add a photo");
        }
    }

    /**
     * Uploads and saves the photograph
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                StorageReference photoRef = storageRef.child(CurrentUser.getInstance().getUsername() +"/"+uri.getLastPathSegment());
                UploadTask uploadTask = photoRef.putFile(uri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(TakeNewBookActivity.this, "Upload Failure.",
                                Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        Toast.makeText(TakeNewBookActivity.this, "Upload Success.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                // wait until upload is finished
                while(uploadTask.isInProgress()){}
                // If successful, change accordingly
                if(uploadTask.isSuccessful()) {
                    photograph = uri.getLastPathSegment();
                    photoButton.setText("Remove photo");
                }
            }
        }
    }

    /**
     * @author Cole Boytinck
     * @version 1.0
     *
     * This is an Async class for getting boo information via a google books api.
     * It attempt to get some information from the isbn provided. It information is avaliable,
     * it fills it in, if no information is found, it exits and only ISBN will be filled.
     */
    private class DownloadBookDetails extends AsyncTask<String, Void, Void> {

        String title;
        String author;
        String desc;

        /**
         * Main part of the Async function, pulls the JSON from the url (by calling readURL),
         * and pulls information from it.
         *
         * @param isbns array of the single isbn passed to this function
         * @return
         */
        protected Void doInBackground(String... isbns) {
            Log.e("TestScan", "in Background");
            String isbn = isbns[0];
            String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;

            try {
                JSONObject bigJson = new JSONObject(readUrl(url));
                JSONArray jsonArray = bigJson.getJSONArray("items");
                JSONObject json = jsonArray.getJSONObject(0);
                JSONObject volumeInfo = json.getJSONObject("volumeInfo");
                JSONArray authors = volumeInfo.getJSONArray("authors");

                title = volumeInfo.getString("title");
                author = authors.getString(0);
                desc = volumeInfo.getString("description");

                Log.e("TestScan", "Title = " + title);
                Log.e("TestScan", "Author = " + author);
                Log.e("TestScan", "Desc = " + desc);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        EditText titleText = findViewById(R.id.editText); // title
                        titleText.setText(title);

                        EditText authorText = findViewById(R.id.editText2); // author
                        authorText.setText(author);

                        EditText descriptionText = findViewById(R.id.editText4); // Description
                        descriptionText.setText(desc);

                    }
                });

            } catch (Exception e) {
                Log.e("readUrl", e.toString());
            }
            return null;
        }

        /**
         * Reads the url and returns a JSON with the data read
         * @param urlString the url to query
         * @return Json
         * @throws Exception
         */
        private String readUrl(String urlString) throws Exception {
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

}
