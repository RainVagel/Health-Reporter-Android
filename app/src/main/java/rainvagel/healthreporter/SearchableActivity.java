package rainvagel.healthreporter;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class SearchableActivity extends ListActivity {

    // https://developer.android.com/guide/topics/search/search-dialog.html

    /**
     * If your data is stored in a SQLite database on the device,
     * performing a full-text search (using FTS3, rather than a LIKE query)
     * can provide a more robust search across text data and can produce results significantly faster.
     */

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    public void doMySearch(String query) {
        //TODO
        /*
        getListAdapter() - Get the ListAdapter associated with this activity's ListView.
         */
    }
}
