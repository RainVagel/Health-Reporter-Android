package rainvagel.healthreporter;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import static android.R.attr.packageNames;
import static android.R.attr.tag;

public class SearchResultsActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = "SearchResultsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.search_results_toolbar);
        setSupportActionBar(my_toolbar);

        String query = new String();
        Intent searchintent = getIntent();
        if( Intent.ACTION_SEARCH.equals(searchintent.getAction())){
          query = searchintent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(query);
                    }

        ListView lv = (ListView) findViewById(R.id.main);



        final ArrayList<String> SearchResults = new ArrayList<>();
        final ArrayList<String> SearchResultsGroups = new ArrayList<>();

        for(int i =0; i<ClientActivity.names.size();i++){
            if(ClientActivity.names.get(i).toLowerCase().contains(query.toLowerCase())){
                SearchResults.add(ClientActivity.names.get(i));
                SearchResultsGroups.add(ClientActivity.groupNames.get(i));
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2,android.R.id.text1,SearchResults){
            public View getView(int position, View convertView, ViewGroup parent){

                View view = super.getView(position,convertView,parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(SearchResults.get(position));
                text2.setText(SearchResultsGroups.get(position));
                text2.setTextSize(text1.getTextSize()/4);

                return view;
            }
        };
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clientId= ClientActivity.clientIDs.get(position);
                Intent toCategories = new Intent(SearchResultsActivity.this, CategoriesActivity.class);
                // we will pass on client's name,group and id in a string, all separated with a comma.
                String passedData =(String.valueOf(clientId)+","+ClientActivity.names.get(position)+","+ ClientActivity.groupNames.get(position));
                Log.v("client intet", passedData);
                toCategories.putExtra("ClientId", passedData);// pass on the data

                startActivity(toCategories);
            }
        });

    }

}
