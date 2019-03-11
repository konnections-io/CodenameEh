package com.example.codenameeh.activities;

import android.app.SearchManager;
import android.content.Context;
import android.view.Menu;
import android.widget.SearchView;

import com.example.codenameeh.R;

public class SearchBooksActivity extends BaseActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
