package rainvagel.healthreporter;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.CategoryClasses.Category;

public class GroupClientActivity extends AppCompatActivity {

    final ArrayList<Category> categories = new ArrayList<>();
    final ArrayList<String> clientnames = new ArrayList<>();
    final  ArrayList<Integer> clientIDs = new ArrayList<>();
    Intent toCategories;
    static String[] intentData;
    Toolbar tb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_client);

       intentData = getIntent().getStringExtra("GroupID").split(",");

        tb = (Toolbar) findViewById(R.id.my_toolbar);
        CharSequence title =  intentData[1];//Group name

        tb.setTitle(title);


        DBHelper mydb = new DBHelper(GroupClientActivity.this);
        String [] clientColumns = {DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME,DBContract.Clients.KEY_GROUP_ID,DBContract.Clients.KEY_ID};
        Cursor res = mydb.getReadableDatabase().query(DBContract.Clients.TABLE_NAME,clientColumns,null,null,null,null,null);
        int clientGroupID = res.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int rowIndex = res.getColumnIndex(DBContract.Clients.KEY_ID);
        int name = res.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int famname = res.getColumnIndex(DBContract.Clients.KEY_LASTNAME);


        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){

            if(res.getString(clientGroupID).equals(intentData[0])){
                Log.v("GROUPCLIENT",res.getString(name)+" "+res.getString(famname)+
                        " "+res.getString(clientGroupID));
                clientnames.add(res.getString(name)+ " "+ res.getString(famname));
                clientIDs.add(Integer.valueOf(res.getString(rowIndex)));
            }
        }

        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                clientnames
        );


        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clientId= clientIDs.get(position);
                Intent toCategories = new Intent(GroupClientActivity.this, CategoriesActivity.class);
                // we will pass on client's name,group and id in a string, all separated with a comma.
                String passedData =(String.valueOf(clientId)+","+clientnames.get(position)+","+ intentData[1]);
                Log.v("client intet", passedData);
                toCategories.putExtra("ClientId", passedData);// pass on the data

                startActivity(toCategories);

            }
        });







    }
}
