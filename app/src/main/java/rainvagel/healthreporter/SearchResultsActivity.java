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

public class SearchResultsActivity extends AppCompatActivity {
    private static final String TAG = "SearchResultsActivity";

    final  ArrayList<Integer> clientIDs = new ArrayList<>();
    final  ArrayList<String> names = new ArrayList<>();
    final  ArrayList<Integer> groupIDs = new ArrayList<>();
    final  ArrayList<String> groupNames = new ArrayList<>();

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
            clientIDs.add(Integer.valueOf(cursor.getString(rowIndex)));
            groupIDs.add(Integer.valueOf(cursor.getString(groupIndex)));
            names.add(cursor.getString(firstNameIndex)+ " " + cursor.getString(lastNameIndex));

        }

        cursor.close();

        String[] columns1 = {DBContract.Groups.KEY_ID,DBContract.Groups.KEY_NAME};

        cursor = mydb.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, columns1,null,null,null,null,null );
        int idIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);


        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            //Log.v("ClientActivity", "Made it here");
            int groupId = Integer.parseInt(cursor.getString(idIndex));
            if(groupIDs.contains(groupId))
                groupNames.add(cursor.getString(nameIndex));

        }

        mydb.close();



        final ArrayList<String> SearchResults = new ArrayList<>();
        final ArrayList<String> SearchResultsGroups = new ArrayList<>();

        for(int i =0; i<names.size();i++){
            if(names.get(i).toLowerCase().contains(query.toLowerCase())){
                SearchResults.add(names.get(i));
                SearchResultsGroups.add(groupNames.get(i));
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
                int clientId= clientIDs.get(position);
                Intent toCategories = new Intent(SearchResultsActivity.this, CategoriesActivity.class);
                // we will pass on client's name,group and id in a string, all separated with a comma.
                String passedData =(String.valueOf(clientId)+","+names.get(position)+","+ groupNames.get(position));
                Log.v("client intet", passedData);
                toCategories.putExtra("ClientId", passedData);// pass on the data

                startActivity(toCategories);
            }
        });

    }

}
