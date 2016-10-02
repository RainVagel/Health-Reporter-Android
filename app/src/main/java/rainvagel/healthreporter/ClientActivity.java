package rainvagel.healthreporter;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import java.util.ArrayList;



public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);


        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();
        //CLIENTS TAB
        TabHost.TabSpec spec = host.newTabSpec("Clients");
        spec.setContent(R.id.Clients);
        spec.setIndicator("Clients");
        host.addTab(spec);
        // GROUPS TAB
        spec = host.newTabSpec("Groups");
        spec.setContent(R.id.Groups);
        spec.setIndicator("Groups");
        host.addTab(spec);

        DBHelper mydb = new DBHelper(this);

        String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME};
        Cursor cursor = mydb.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns, null,null,null,null,null);

        int rowIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);

        ArrayList<Integer> clientIDs = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
       // Log.v("ClientActivity", String.valueOf(cursor.getCount()));


        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            Log.v("ClientActivity", "Made it here");
            clientIDs.add(Integer.valueOf(cursor.getString(rowIndex)));
            names.add(cursor.getString(firstNameIndex)+ " " + cursor.getString(lastNameIndex));

        }

        mydb.close();
        ListView lv = (ListView) findViewById(R.id.listViewClients);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                names);

        lv.setAdapter(arrayAdapter);

        ListView elv = (ListView) findViewById(R.id.listViewGroups);
        elv.setAdapter(arrayAdapter);


    }





    public void addNewClient(View v){
        Intent intent = new Intent(this, NewClientActivity.class);
        startActivity(intent);
    }
}
