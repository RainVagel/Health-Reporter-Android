package rainvagel.healthreporter.ClientClasses;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.DBContract;
import rainvagel.healthreporter.DBHelper;
import rainvagel.healthreporter.GroupClientActivity;
import rainvagel.healthreporter.R;


public class ClientActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "ClientActivity";

    private Boolean isFabOpen = false;
    private FloatingActionButton fab1;
    private Animation openfab, closefab, initialrotate,finalrotate;

    final  ArrayList<Integer> clientIDs = new ArrayList<>();
    final  ArrayList<String> names = new ArrayList<>();
    final  ArrayList<Integer> groupIDs = new ArrayList<>();
    final  Map<Integer, String> groups = new HashMap<>();
    final Map<String,Integer> groupsreversed = new HashMap<>();
    final ArrayList<String> groupNames = new ArrayList<>();
    ArrayAdapter adapter = null;


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setTitle(R.string.my_tb_title);


        FrameLayout dimbackground = (FrameLayout) findViewById(R.id.main);

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        openfab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.openfab);
        closefab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.closefab);
        initialrotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.initialrotate);
        finalrotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.finalrotate);
        fab1.setOnClickListener(this);

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
        final int groupIndex  = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);

       // Log.v("ClientActivity", String.valueOf(cursor.getCount()));

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            //Log.v("ClientActivity", "Made it here");
            clientIDs.add(Integer.valueOf(cursor.getString(rowIndex)));
            groupIDs.add(Integer.valueOf(cursor.getString(groupIndex)));
            //groupids contains exact amount of groupids as clientids
            names.add(cursor.getString(firstNameIndex)+ " " + cursor.getString(lastNameIndex));
            Log.v("cursor for", String.valueOf(clientIDs.size()));
            Log.v("cursor for", String.valueOf(names.size()));


        }

        cursor.close();

        String[] columns1 = {DBContract.Groups.KEY_ID,DBContract.Groups.KEY_NAME};

        cursor = mydb.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, columns1,null,null,null,null,null );
        int idIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            //Log.v("ClientActivity", "Made it here");
            int groupId = Integer.parseInt(cursor.getString(idIndex));
            if(groupIDs.contains(groupId)) {
                groups.put(groupId, cursor.getString(nameIndex));
                groupsreversed.put(cursor.getString(nameIndex),groupId);
                groupNames.add(cursor.getString(nameIndex));
            }
        }

        mydb.close();
        Log.v("ats", groups.toString());


        ListView lv = (ListView) findViewById(R.id.listViewClients);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2,android.R.id.text1,names){
            public View getView(int position, View convertView,ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                Log.v("arrayadapter", String.valueOf(position));
                Log.v("asd size", String.valueOf(names.size()));
                Log.v("searchGroupNames size", String.valueOf(groups.size()));
                text1.setText(names.get(position));
                text2.setText(groups.get(groupIDs.get(position)));
                text2.setTextSize(text1.getTextSize()/4);
            return view;
            }
        };
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                groupNames);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
        ListView elv = (ListView) findViewById(R.id.listViewGroups);
        elv.setAdapter(arrayAdapter);



        elv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int groupID = groupIDs.get(position);
                Intent toClients = new Intent(ClientActivity.this, GroupClientActivity.class);
                String passedData = (groupsreversed.get(groupNames.get(position))+","+groupNames.get(position));
                Log.v("group intent",passedData);
                toClients.putExtra("GroupID",passedData);
                startActivity(toClients);
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clientId= clientIDs.get(position);
                Intent toCategories = new Intent(ClientActivity.this, CategoriesActivity.class);
                // we will pass on client's name,group and id in a string, all separated with a comma.
                String passedData = (String.valueOf(clientId)+","+names.get(position)+","+ groups.get(groupIDs.get(position)));
                Log.v("client intet", passedData);
                toCategories.putExtra("ClientId", passedData);// pass on the data
                startActivity(toCategories);

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int clientId;
        switch (item.getItemId()) {
            case R.id.cnt_mnu_edit:
                Log.v(TAG, "Made it to context menu edit action");
                clientId = clientIDs.get(info.position);
                Intent toClientEdit = new Intent(this, EditClientActivity.class);
                toClientEdit.putExtra("ClientId", String.valueOf(clientId));
                Log.v(TAG, "ClientID: " + toClientEdit.getStringExtra("ClientId"));
                startActivity(toClientEdit);
                break;
            case R.id.cnt_mnu_delete:
                Log.v(TAG, "Made to context menu delete action");
                clientId = clientIDs.get(info.position);
                DBHelper db = new DBHelper(this);
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                sqLiteDatabase.delete(DBContract.Clients.TABLE_NAME,
                        DBContract.Clients.KEY_ID + "=" + clientId, null);
                sqLiteDatabase.close();
                recreate();
                break;
        }
        return true;
    }

    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab1:
                addNewClient(v);
        }
    }
    
    public void addNewClient(View v){
        Intent intent = new Intent(this, NewClientActivity.class);
        startActivity(intent);
    }
}
