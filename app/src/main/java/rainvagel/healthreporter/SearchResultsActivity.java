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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.DBClasses.DBClientsTransporter;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTransporter;

import static rainvagel.healthreporter.ClientClasses.ClientActivity.host;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";

    ArrayList<String> searchClientIDs = new ArrayList<>();
    ArrayList<String> asd = new ArrayList<>();
    ArrayList<String> searchGroupIDs = new ArrayList<>();
    Map<String, String> searchGroupNames = new HashMap<>();
    Map<String, String> finalClientId= new HashMap<String,String>();

    Map<String, String> searchGroupNamesindex = new HashMap<>();
    Map<String, String> searchGroupNamesindexReversed = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.v("VAAAAAAAAAATA SEDA", String.valueOf(host.getCurrentTab()));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.search_results_toolbar);
        setSupportActionBar(my_toolbar);

        if (host.getCurrentTab() == 0) {
            String query = new String();
            Intent searchintent = getIntent();
            if (Intent.ACTION_SEARCH.equals(searchintent.getAction())) {
                query = searchintent.getStringExtra(SearchManager.QUERY);
                getSupportActionBar().setTitle(query);
            }

            ListView lv = (ListView) findViewById(R.id.main);

            DBQueries dbQueries = new DBQueries();
            DBClientsTransporter dbClientsTransporter = dbQueries.getClientsFromDB(this);
            searchClientIDs = dbClientsTransporter.getClientID();
            searchGroupIDs.addAll(dbClientsTransporter.getClientIdToGroupId().values());
            Log.v(TAG, "SeachGroupIDs: " + searchGroupIDs.toString());
            for (String clientId : searchClientIDs) {
                asd.add(dbClientsTransporter.getClientIdToFirstName().get(clientId) + " " +
                dbClientsTransporter.getClientIdToLastName().get(clientId));
                finalClientId.put(dbClientsTransporter.getClientIdToFirstName().get(clientId) + " " +
                        dbClientsTransporter.getClientIdToLastName().get(clientId), clientId);
            }

            DBTransporter dbTransporter = dbQueries.getGroupsFromDB(this);
            Map<String, String> groups = dbTransporter.getIdToName();
            ArrayList<String> groupIDs = dbTransporter.getGroupID();
            Log.v(TAG, "groups:" + groups.toString());
            Log.v(TAG, "groupIDs: " + groupIDs.toString());
            for (String id : groupIDs) {
                Log.v(TAG, "GroupIDs kuulub see iD: " + id);
                if (searchGroupIDs.contains(id)) {
                    Log.v(TAG, "SearchGroupNamesi liitmise meetod");
                    searchGroupNames.put(id, groups.get(id));
                }
            }

            Log.v("groupids size", String.valueOf(searchGroupIDs.size()));

            final ArrayList<String> SearchResults = new ArrayList<>();
            final ArrayList<String> SearchResultsGroups = new ArrayList<>();

            for (int i = 0; i < asd.size(); i++) {
                if (asd.get(i).toLowerCase().contains(query.toLowerCase())) {
                    SearchResults.add(asd.get(i));
                    Log.v("searchgroupsname", String.valueOf(searchGroupNames.size()));
                    Log.v(TAG, "SearchResults: " + SearchResults.toString());

                    //GROUP NAMES DONT HAVE AS MANY ENTRIES AS ASD
                    SearchResultsGroups.add(searchGroupNames.get(searchGroupIDs.get(i)));
                }
            }

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, SearchResults) {
                public View getView(int position, View convertView, ViewGroup parent) {

                    View view = super.getView(position, convertView, parent);
                    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                    TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                    text1.setText(SearchResults.get(position));
                    text2.setText(SearchResultsGroups.get(position));
                    text2.setTextSize(text1.getTextSize() / 4);

                    return view;
                }
            };
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String clientId = searchClientIDs.get(position);

                    Intent toCategories = new Intent(SearchResultsActivity.this, CategoriesActivity.class);
                    // we will pass on client's name,group and id in a string, all separated with a comma.
                    String passedData = (finalClientId.get(SearchResults.get(position)) + ","
                            + SearchResults.get(position) + "," + SearchResultsGroups.get(position));

                    Log.v("client intet", passedData);
                    toCategories.putExtra("ClientId", passedData);// pass on the data

                    startActivity(toCategories);
                }
            });

        }
        else {
            String query = new String();
            Intent searchintent = getIntent();
            if (Intent.ACTION_SEARCH.equals(searchintent.getAction())) {
                query = searchintent.getStringExtra(SearchManager.QUERY);
                getSupportActionBar().setTitle(query);
            }

            ListView lv = (ListView) findViewById(R.id.main);

            DBQueries dbQueries = new DBQueries();
            DBTransporter dbTransporter = dbQueries.getGroupsFromDB(this);

            searchGroupIDs = dbTransporter.getGroupID();
            searchGroupNamesindex = dbTransporter.getIdToName();
            searchGroupNamesindexReversed = dbTransporter.getNameToId();
            asd = dbTransporter.getNames();

            final ArrayList<String> SearchResults = new ArrayList<>();
            final ArrayList<String> SearchResultsGroups = new ArrayList<>();

            for (int i = 0; i < asd.size(); i++) {
                if (asd.get(i).toLowerCase().contains(query.toLowerCase())) {
                    SearchResults.add(asd.get(i));
                    Log.v("searchgroupsname", String.valueOf(searchGroupNames.size()));
                    //GROUP NAMES DONT HAVE AS MANY ENTRIES AS ASD
                }
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    SearchResults);
            registerForContextMenu(lv);
            ListView elv = (ListView) findViewById(R.id.main);
            elv.setAdapter(arrayAdapter);

            elv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent toClients = new Intent(SearchResultsActivity.this, GroupClientActivity.class);
                    String passedData = String.valueOf(searchGroupNamesindexReversed.get(SearchResults.get(position))+","+SearchResults.get(position));
                    toClients.putExtra("GroupID",passedData);
                    startActivity(toClients);
                }
            });

        }

        }
    }


