package com.example.codenameeh.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.codenameeh.R;

import java.util.ArrayList;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchBooksAdapter extends ArrayAdapter<Book> {

    private static final String TAG = "SearchViewAdapter";

    private Context context;
    int resource;

    public SearchBooksAdapter(Context context, int resource, ArrayList<Book> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = getItem(position).getTitle();
        String author = getItem(position).getAuthor();
        String owner = getItem(position).getOwner();
        Boolean status = getItem(position).getAcceptedStatus();
        Boolean borrowed = getItem(position).isBorrowed();
        String uuid = getItem(position).getUuid();

        Book book = new Book(title, author, "Lorem Ipsum", "Lorem Ipsum", "image.png", owner);
        book.setAcceptedStatus(status);
        book.setBorrowed(borrowed);
        book.setUuid(uuid);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvAuthor = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvOwner = (TextView) convertView.findViewById(R.id.textView3);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.textView4);

        tvTitle.setText(title);
        tvAuthor.setText(author);
        tvOwner.setText(owner);
        if (status == true || borrowed == true) {
            tvStatus.setText("Not Available");
        } else {
            tvStatus.setText("Available");
        }

        return convertView;
    }
}
