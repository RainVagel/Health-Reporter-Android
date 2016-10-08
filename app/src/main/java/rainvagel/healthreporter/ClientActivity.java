package rainvagel.healthreporter;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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

        String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_GROUP_ID};




        Cursor cursor = mydb.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns, null,null,null,null,null);



        int rowIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int groupIndex  = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);


        final ArrayList<Integer> clientIDs = new ArrayList<>();
        final ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> groupIDs = new ArrayList<>();
        final ArrayList<String> groupNames = new ArrayList<>();




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
        Log.v("ats", groupNames.toString());


        ListView lv = (ListView) findViewById(R.id.listViewClients);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2,android.R.id.text1,names){
            public View getView(int position, View convertView,ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(names.get(position));
                text2.setText(groupNames.get(position));
                text2.setTextSize(text1.getTextSize()/4);
            return view;
            }

        };


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                groupNames);

        lv.setAdapter(adapter);

        ListView elv = (ListView) findViewById(R.id.listViewGroups);
        elv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clientId= clientIDs.get(position);
                Intent toCategories = new Intent(ClientActivity.this, CategoriesActivity.class);
                toCategories.putExtra("ClientId", String.valueOf(clientId));// pass on the id of the client

                startActivity(toCategories);

            }
        });


    }





    public void addNewClient(View v){
        Intent intent = new Intent(this, NewClientActivity.class);
        startActivity(intent);
    }
}
