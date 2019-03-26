package com.example.codenameeh.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.cortical.retina.client.LiteClient;

/**
 * @author Daniel Dick
 * @version 1.0
 * BookListActivity extends the BaseActivity class used
 * in this app.  This allows for all of the new books to be
 * displayed in the application in the Android operating
 * environment. Books can be selected to view the details,
 * delete, or edit them.
 * There are no outstanding issues here that I
 * have detected or am aware of.
 */
public class BookListActivity extends BaseActivity {
    private ListView bookView;
    private int positionclicked;
    private ArrayList<Book> booksOwnedList;
    private ArrayAdapter<Book> adapter;
    private User currentUser;

    public static final String EXTRA_MESSAGE_TITLE = "com.example.codenameeh.title";
    public static final String EXTRA_MESSAGE_AUTHOR = "com.example.codenameeh.author";
    public static final String EXTRA_MESSAGE_ISBN = "com.example.codenameeh.isbn";
    public static final String EXTRA_MESSAGE_DESCRIPTION = "com.example.codenameeh.description";
    public static final String EXTRA_MESSAGE_DELETE = "com.example.codenameeh.delete";

    /**
     * The onCreate method sets up the ListView with the listener
     * so that any selected books can be handled properly.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_book_list, frameLayout);

        bookView = findViewById(R.id.BooksOwnedView);


        bookView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                positionclicked = position;
                viewBook(position);
            }
        });

        Intent Iintent = getIntent();
        String isbn = Iintent.getStringExtra("isbn");
        Log.e("TestScan", "Received Intent");
        if(isbn != null) {
            Intent intent = new Intent(this, TakeNewBookActivity.class);
            intent.putExtra("isbn", isbn);
            startActivityForResult(intent, 1);
        }
    }

    /**
     * The onActivityResult method handled return intents and codes
     * from any activities that were started.  If necessary, additional
     * activity calls can have their return handling added here as development
     * continues.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            //new book returned

            try {
                String title = data.getStringExtra(EXTRA_MESSAGE_TITLE);
                String author = data.getStringExtra(EXTRA_MESSAGE_AUTHOR);
                String isbn = data.getStringExtra(EXTRA_MESSAGE_ISBN);
                String description = data.getStringExtra(EXTRA_MESSAGE_DESCRIPTION);
                String photograph = data.getStringExtra("photo");
                ArrayList<String> keywords = new ArrayList<>();

                try {
                    keywords = new GetKeywords().execute(description).get();
                } catch (Exception e) {
                    Log.e("Keywords", e.toString());
                }


                Book newBook = new Book(title, author, isbn, description, photograph, currentUser.getUsername(), keywords);
                currentUser.newOwn(newBook);
                booksOwnedList.add(newBook);
                Booklist.getInstance().add(newBook);
                FirebaseFirestore.getInstance().collection("users").document(currentUser.getUsername()).set(currentUser);
                FirebaseFirestore.getInstance().collection("All Books").document(newBook.getUuid()).set(newBook)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Returned", e.toString());
            }

        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            //view details of book returned
            if ((data.getStringExtra(EXTRA_MESSAGE_DELETE)).equals("TRUE")) {
                FirebaseFirestore.getInstance().collection("All Books").document(booksOwnedList.get(positionclicked).getUuid())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            }
                        });
                currentUser.removeOwn(booksOwnedList.get(positionclicked));
                booksOwnedList.remove(booksOwnedList.get(positionclicked));

            }
            adapter.notifyDataSetChanged();
            FirebaseFirestore.getInstance().collection("users").document(currentUser.getUsername()).set(currentUser);


        }

    }

    /**
     * The onStart method sets up the adapter and the
     * initialization of the booksOwned BookList and the
     * list of Books owned.
     */
    @Override
    protected void onStart() {
        super.onStart();
        currentUser = CurrentUser.getInstance();
        booksOwnedList = currentUser.BooksOwned();

        adapter = new ArrayAdapter<Book>(this, R.layout.list_item, booksOwnedList);
        bookView.setAdapter(adapter);
    }

    /**
     * viewBook is called when a book is selected.  The book's details are
     * passed to another activity to be displayed, deleted, or any other action
     * that is added during development.
     * @param i
     */
    private void viewBook(int i) {
        Intent intent = new Intent(this, ViewBookActivity.class);
        intent.putExtra("book", booksOwnedList.get(i));
        startActivityForResult(intent, 2);
    }

    public void OwnedBooks(View view) {
        TextView title = findViewById(R.id.textView2);
        title.setText("Books Owned");
        booksOwnedList = currentUser.BooksOwned();
        adapter = new ArrayAdapter<Book>(this, R.layout.list_item, booksOwnedList);
        bookView.setAdapter(adapter);
    }
    public void BeingBorrowedBooks(View view) {
        TextView title = findViewById(R.id.textView2);
        title.setText("Books Being Borrowed");
        booksOwnedList = currentUser.BooksOwnedBorrowed();
        adapter = new ArrayAdapter<Book>(this, R.layout.list_item, booksOwnedList);
        bookView.setAdapter(adapter);
    }
    public void AvailableBooks(View view) {
        TextView title = findViewById(R.id.textView2);
        title.setText("Available Books");
        booksOwnedList = currentUser.BooksOwnedAvailable();
        adapter = new ArrayAdapter<Book>(this, R.layout.list_item, booksOwnedList);
        bookView.setAdapter(adapter);
    }
    public void RequestedBooks(View view) {
        TextView title = findViewById(R.id.textView2);
        title.setText("Requested Books");
        booksOwnedList = currentUser.BooksOwnedRequested();
        adapter = new ArrayAdapter<Book>(this, R.layout.list_item, booksOwnedList);
        bookView.setAdapter(adapter);
    }


    /**
     * newBook calls the activity to add a new book.  When that
     * activity returns with the book's details, the above method for
     * returning activities will instantiate and add the book to the
     * necessary lists, and update the display.
     * @param view
     */
    public void newBook (View view) {
        Intent intent = new Intent(this, TakeNewBookActivity.class);
        startActivityForResult(intent, 1);
    }


    class GetKeywords extends AsyncTask<String, Void, ArrayList<String>> {

        protected ArrayList<String> doInBackground(String... descs) {
            List<String> keywords = new ArrayList<>();
            try {
                String desc = descs[0];
                LiteClient lite = new io.cortical.retina.client.LiteClient("1e1d18d0-4f42-11e9-8f72-af685da1b20e");
                keywords = lite.getKeywords(desc);
            } catch (Exception e) {
                Log.e("Keywords", e.toString());
            }
            return new ArrayList<>(keywords);
        }


    }
}
