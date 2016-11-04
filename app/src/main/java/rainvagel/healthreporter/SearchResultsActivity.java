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

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";

    final  ArrayList<Integer> searchClientIDs = new ArrayList<>();
    final  ArrayList<String> asd = new ArrayList<>();
    final  ArrayList<Integer> searchGroupIDs = new ArrayList<>();
    final Map<Integer, String> searchGroupNames = new HashMap<>();

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

        DBHelper mydb = new DBHelper(this);

        String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_GROUP_ID};
        Cursor cursor = mydb.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns, null,null,null,null,null);

        int rowIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int groupIndex  = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);







        // Log.v("ClientActivity", String.valueOf(cursor.getCount()));


        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            //Log.v("ClientActivity", "Made it here");
            searchClientIDs.add(Integer.valueOf(cursor.getString(rowIndex)));
            searchGroupIDs.add(Integer.valueOf(cursor.getString(groupIndex)));
            asd.add(cursor.getString(firstNameIndex)+ " " + cursor.getString(lastNameIndex));

        }

        cursor.close();

        String[] columns1 = {DBContract.Groups.KEY_ID,DBContract.Groups.KEY_NAME};

        cursor = mydb.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, columns1,null,null,null,null,null );
        int idIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);


        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            //Log.v("ClientActivity", "Made it here");
            int groupId = Integer.parseInt(cursor.getString(idIndex));
            if(searchGroupIDs.contains(groupId))
                searchGroupNames.put(groupId,cursor.getString(nameIndex));

        }
        Log.v("groupids size", String.valueOf(searchGroupIDs.size()));

        mydb.close();



        final ArrayList<String> SearchResults = new ArrayList<>();
        final ArrayList<String> SearchResultsGroups = new ArrayList<>();

        for(int i = 0; i< asd.size(); i++){
            if(asd.get(i).toLowerCase().contains(query.toLowerCase())){
                SearchResults.add(asd.get(i));
                Log.v("searchgroupsname", String.valueOf(searchGroupNames.size()));

                //GROUP NAMES DONT HAVE AS MANY ENTRIES AS ASD
                SearchResultsGroups.add(searchGroupNames.get(searchGroupIDs.get(i)));
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
                int clientId= searchClientIDs.get(position);
                Intent toCategories = new Intent(SearchResultsActivity.this, CategoriesActivity.class);
                // we will pass on client's name,group and id in a string, all separated with a comma.
                String passedData =(String.valueOf(clientId)+","+ asd.get(position)+","+ searchGroupNames.get(position));
                Log.v("client intet", passedData);
                toCategories.putExtra("ClientId", passedData);// pass on the data

                startActivity(toCategories);
            }
        });

    }

}
