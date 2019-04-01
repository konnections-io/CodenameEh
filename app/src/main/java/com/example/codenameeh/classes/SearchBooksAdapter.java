package com.example.codenameeh.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.codenameeh.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Custom adapter for the SearchBooksActivity ListView
 * @author Daniel Shim
 * @version 1.0
 */
public class SearchBooksAdapter extends ArrayAdapter<Book> {

    private Context context;
    private int resource;

    /**
     * Sets up the internal fields to create the custom adapter.
     * @param context The activity and the state of the app currently
     * @param resource The number of objects displayed
     * @param objects The objects to be added to the adapter
     */
    public SearchBooksAdapter(Context context, int resource, ArrayList<Book> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    /**
     * Sets up the contents of each individual item on the listview
     * @param position the index of the item starting from the top of the list
     * @param convertView The view of the adapter which is being manipulated
     * @param parent The group which the view belongs to
     * @return the manipulated view
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      
        Book book = getItem(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvOwner = (TextView) convertView.findViewById(R.id.textView3);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.textView4);

        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        tvOwner.setText(book.getOwner());
        if (book.getAcceptedStatus() || book.isBorrowed()) {
            tvStatus.setText(context.getString(R.string.notAvail));
        } else {
            tvStatus.setText(context.getString(R.string.avail));
        }

        return convertView;
    }
}
