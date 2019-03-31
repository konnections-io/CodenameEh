package com.example.codenameeh.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.codenameeh.R;

import java.util.ArrayList;

/**
 * A simple Adapter for showing a Booklist, showing more detail than an ArrayAdapter would. Shows
 * title, username of the owner, and the description.
 */
public class BooklistAdapter extends BaseAdapter {
    private ArrayList<Book> booklist;
    public BooklistAdapter(ArrayList<Book> dataset){
        this.booklist = dataset;
    }

    /**
     * returns the size of the list
     * @return
     */
    public int getCount(){
        return booklist.size();
    }

    /**
     * returns the item at the specified position
     * @param position
     * @return
     */
    public Object getItem(int position){
        return booklist.get(position);
    }

    /**
     * returns the ItemId, if applicable. Returns the position in the list
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Inputs the data into a book_list_item layout
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        }
        TextView bookTitleView = convertView.findViewById(R.id.BookTitleList);
        bookTitleView.setText(booklist.get(position).getTitle());
        TextView usernameView = convertView.findViewById(R.id.Username);
        if (booklist.get(position).getOwner() == null){
            usernameView.setText("");
        }
        else{
            usernameView.setText(booklist.get(position).getOwner());
        }
        TextView descriptionView = convertView.findViewById(R.id.Description);
        descriptionView.setText(booklist.get(position).getDescription());
        return convertView;
    }
}
