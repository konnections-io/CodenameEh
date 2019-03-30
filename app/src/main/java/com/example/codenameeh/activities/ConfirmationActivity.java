package com.example.codenameeh.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.codenameeh.R;
import com.example.codenameeh.classes.Book;
import com.example.codenameeh.classes.Booklist;
import com.example.codenameeh.classes.BooklistAdapter;
import com.example.codenameeh.classes.CurrentUser;
import com.example.codenameeh.classes.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * Provides the user with an option to choose a book, in the event that there are multiple possible
 * for the given ISBN.
 * @author Ryan Jensen
 */
public class ConfirmationActivity extends BaseActivity {
    private User currentUser;
    private ArrayList<Book> potentialBooks;
    private String isbn;
    private BooklistAdapter adapter;
    private ListView dataList;
    @Override
    /**
     * Obtain the ISBN, and inflate the interface. Add an onclick Listener for item clicks
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_confirmation, frameLayout);
        dataList = findViewById(R.id.confirmation_list);
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Select the book at this position, and finish.
                Book selected = potentialBooks.get(position);
                if(currentUser.getUsername().equals(selected.getOwner())){
                    selected.setOwnerConfirmation(true);
                    FirebaseFirestore.getInstance().collection("All Books").document(selected.getUuid())
                            .update("ownerConfirmation", selected.getOwnerConfirmation())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            });
                } else{
                    selected.setBorrowerConfirmation(true);
                    FirebaseFirestore.getInstance().collection("All Books").document(selected.getUuid())
                            .update("borrowerConfirmation", selected.getBorrowerConfirmation())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                }
                            });
                }
                finish();
            }
        });
        Intent intent = getIntent();
        isbn = intent.getStringExtra("isbn");
    }

    /**
     * Initialize the Arraylist, and fill the arraylist with the potential candidates.
     * Place candidates into an BooklistAdapter for viewing
     */
    @Override
    protected void onStart() {
        super.onStart();
        potentialBooks = new ArrayList<>();
        currentUser = CurrentUser.getInstance();
        findPotentialOwnedBorrowedBooks();
        findPotentialOtherBooks();
        adapter = new BooklistAdapter(potentialBooks);
        dataList.setAdapter(adapter);
    }

    /**
     * Find those books which are owned by this person, are accepted and match the ISBN,
     * and add them to the potentialBooks
     */
    private void findPotentialOwnedBorrowedBooks(){
        for(Book book: currentUser.BooksOwned()){
            if(book.getISBN().equals(isbn)&&(book.getAcceptedStatus()||book.isBorrowed())){
                potentialBooks.add(book);
            }
        }
        for(Book book:currentUser.BorrowedBooks()){
            if(book.getISBN().equals(isbn)){
                potentialBooks.add(book);
            }
        }
    }

    /**
     * Find the books we have been accepted for that match this ISBN, add to potentialBooks
     */
    private void findPotentialOtherBooks(){
        ArrayList<Book> tempNewBooks = Booklist.getInstance().getBookList();
        for(Book book:tempNewBooks){
            if(!book.getOwner().equals(currentUser.getUsername())){
                if(book.getAcceptedStatus()){
                    if(book.getISBN().equals(isbn)){
                        // TODO additional check for being the one who it was accepted for
                        potentialBooks.add(book);
                    }
                }
            }
        }
    }

}
