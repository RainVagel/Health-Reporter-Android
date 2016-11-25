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
import rainvagel.healthreporter.ClientClasses.ClientActivity;

import static rainvagel.healthreporter.ClientClasses.ClientActivity.host;

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";

    final  ArrayList<String> searchClientIDs = new ArrayList<>();
    final  ArrayList<String> asd = new ArrayList<>();
    final  ArrayList<String> searchGroupIDs = new ArrayList<>();
    final Map<String, String> searchGroupNames = new HashMap<>();
    final Map<String, String> finalClientId= new HashMap<>();

    final Map<String, String> searchGroupNamesindex = new HashMap<>();
    final Map<String,String> searchGroupNamesindexReversed = new HashMap<>();









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

            DBHelper mydb = new DBHelper(this);

            String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_GROUP_ID};
            Cursor cursor = mydb.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns, null, null, null, null, null);

            int rowIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
            int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
            int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
            int groupIndex = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);


            // Log.v("ClientActivity", String.valueOf(cursor.getCount()));


            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                //Log.v("ClientActivity", "Made it here");
                searchClientIDs.add(cursor.getString(rowIndex));
                searchGroupIDs.add(cursor.getString(groupIndex));
                asd.add(cursor.getString(firstNameIndex) + " " + cursor.getString(lastNameIndex));


                finalClientId.put(cursor.getString(firstNameIndex) + " " + cursor.getString(lastNameIndex), cursor.getString(rowIndex));

            }

            cursor.close();

            String[] columns1 = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};

            cursor = mydb.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, columns1, null, null, null, null, null);
            int idIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);


            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                //Log.v("ClientActivity", "Made it here");
                String groupId = cursor.getString(idIndex);
                if (searchGroupIDs.contains(groupId))
                    searchGroupNames.put(groupId, cursor.getString(nameIndex));


            }
            Log.v("groupids size", String.valueOf(searchGroupIDs.size()));

            mydb.close();


            final ArrayList<String> SearchResults = new ArrayList<>();
            final ArrayList<String> SearchResultsGroups = new ArrayList<>();

            for (int i = 0; i < asd.size(); i++) {
                if (asd.get(i).toLowerCase().contains(query.toLowerCase())) {
                    SearchResults.add(asd.get(i));
                    Log.v("searchgroupsname", String.valueOf(searchGroupNames.size()));


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
                    String passedData = (finalClientId.get(SearchResults.get(position)) + "," + SearchResults.get(position) + "," + SearchResultsGroups.get(position));


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

            DBHelper mydb = new DBHelper(this);

            String[] columns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
            Cursor cursor = mydb.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, columns, null, null, null, null, null);

            int rowIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
            int groupNameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);



            // Log.v("ClientActivity", String.valueOf(cursor.getCount()));


            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                //Log.v("ClientActivity", "Made it here");
                searchGroupIDs.add(cursor.getString(rowIndex));
                searchGroupNamesindex.put(cursor.getString(rowIndex),cursor.getString(groupNameIndex));
                searchGroupNamesindexReversed.put(cursor.getString(groupNameIndex),cursor.getString(rowIndex));
                asd.add(cursor.getString(groupNameIndex));


            }

            cursor.close();


            mydb.close();


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


