package com.example.codenameeh.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.codenameeh.R;

/**
 * A simple Adapter for showing a Booklist, showing more detail than an ArrayAdapter would
 */
public class BooklistAdapter extends BaseAdapter {
    Booklist booklist;
    public BooklistAdapter(Booklist dataset){
        this.booklist = dataset;
    }
    public int getCount(){
        return booklist.size();
    }

    public Object getItem(int position){
        return booklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent);
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
