package rainvagel.healthreporter;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class GroupClientActivity extends AppCompatActivity {

    final ArrayList<Category> categories = new ArrayList<>();
    final ArrayList<String> clientnames = new ArrayList<>();
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
        String [] clientColumns = {DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME,DBContract.Clients.KEY_GROUP_ID};
        Cursor res = mydb.getReadableDatabase().query(DBContract.Clients.TABLE_NAME,clientColumns,null,null,null,null,null);
        int clientGroupID = res.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int name = res.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int famname = res.getColumnIndex(DBContract.Clients.KEY_LASTNAME);


        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            if(res.getString(clientGroupID).equals(intentData[0])){
                clientnames.add(res.getString(name)+ " "+ res.getString(famname));
            }
        }

        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                clientnames
        );


        lv.setAdapter(arrayAdapter);







    }
}
