package com.example.codenameeh.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;

import java.util.ArrayList;

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
    private Booklist booksOwned;
    private ArrayList<Book> booksOwnedList;
    private ArrayAdapter<Book> adapter;
    private User currentUser;

    public static final String EXTRA_MESSAGE_TITLE = "com.example.codenameeh.title";
    public static final String EXTRA_MESSAGE_AUTHOR = "com.example.codenameeh.author";
    public static final String EXTRA_MESSAGE_ISBN = "com.example.codenameeh.isbn";
    public static final String EXTRA_MESSAGE_DESCRIPTION = "com.example.codenameeh.description";
    public static final String EXTRA_MESSAGE_STATUS = "com.example.codenameeh.status";
    public static final String EXTRA_MESSAGE_DELETE = "com.example.codenameeh.delete";

    /**
     * The onCreate method sets up the ListView with the listener
     * so that any selected books can be handled properly.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        bookView = findViewById(R.id.BooksOwnedView);


        bookView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                Book selItem = (Book) parent.getItemAtPosition(position);
                positionclicked = position;
                viewBook(position);
            }
        });
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
            String title = data.getStringExtra(EXTRA_MESSAGE_TITLE);
            String author = data.getStringExtra(EXTRA_MESSAGE_AUTHOR);
            String isbn = data.getStringExtra(EXTRA_MESSAGE_ISBN);
            String description = data.getStringExtra(EXTRA_MESSAGE_DESCRIPTION);
            Book newBook = new Book(title, author, isbn, description, currentUser);
            booksOwned.add(newBook);
            booksOwnedList = booksOwned.getBookList();
            adapter.notifyDataSetChanged();
        }
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            //view details of book returned
            if ((data.getStringExtra(EXTRA_MESSAGE_DELETE)).equals("TRUE")) {
                booksOwned.remove(booksOwnedList.get(positionclicked));
                booksOwnedList.remove(positionclicked);
            }
            adapter.notifyDataSetChanged();
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
        booksOwned = currentUser.getOwning();
        booksOwnedList = booksOwned.getBookList();

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
        intent.putExtra(EXTRA_MESSAGE_TITLE, (booksOwnedList.get(i).getTitle()));
        intent.putExtra(EXTRA_MESSAGE_AUTHOR, (booksOwnedList.get(i).getAuthor()));
        intent.putExtra(EXTRA_MESSAGE_ISBN, (booksOwnedList.get(i).getISBN()));
        intent.putExtra(EXTRA_MESSAGE_DESCRIPTION, (booksOwnedList.get(i).getDescription()));
        if (booksOwnedList.get(i).isBorrowed()) {
            intent.putExtra(EXTRA_MESSAGE_STATUS, ("Borrowed"));
        }
        else {
            intent.putExtra(EXTRA_MESSAGE_STATUS, ("Not Borrowed"));
        }
        startActivityForResult(intent, 2);
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
}
