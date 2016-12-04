package rainvagel.healthreporter.ClientClasses;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.AppraiserClasses.AppraiserActivity;
import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTransporter;
import rainvagel.healthreporter.GroupClientActivity;
import rainvagel.healthreporter.R;


public class ClientActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "ClientActivity";

    private Boolean isFabOpen = false;
    private FloatingActionButton fab1;
    private Animation openfab, closefab, initialrotate,finalrotate;
    ListView lv;

    ArrayList<String> clientIDs = new ArrayList<>();
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> groupIDs = new ArrayList<>();
    Map<String, String> groups = new HashMap<>();
    Map<String, String> groupsreversed = new HashMap<>();
    ArrayList<String> groupNames = new ArrayList<>();
    String appraiserID = null;

    Map<String, String> clientIdGroupId = new HashMap<>();
    Map<String, String>  namesGroupKeys = new HashMap<>();
    Map<String, String>  namesClientKeys = new HashMap<>();


    public static TabHost host;

    ArrayAdapter adapter = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return super.onCreateOptionsMenu(menu);
    }

    //TOOLBAR SETTINGS BUTTON ACTION HANDLING
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            sortbyfirstname();
            return true;
        }

        if(id == R.id.action_settings1){
            sortbylastname();
            return false;
        }

        if (id == R.id.settings_appraisers) {
            Intent intent = new Intent(this, AppraiserActivity.class);
            startActivityForResult(intent, 100);
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortbyfirstname() {
        Collections.sort(names);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2,android.R.id.text1,names){
            public View getView(int position, View convertView,ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(names.get(position));
                text2.setText(groups.get(namesGroupKeys.get(names.get(position))));
                text2.setTextSize(text1.getTextSize()/4);
                return view;
            }
        };

        lv.setAdapter(adapter);

    }
    private void sortbylastname() {
        sortLast(names);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2,android.R.id.text1,names){
            public View getView(int position, View convertView,ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(names.get(position));
                text2.setText(groups.get(namesGroupKeys.get(names.get(position))));
                text2.setTextSize(text1.getTextSize()/4);
                return view;
            }
        };
        lv.setAdapter(adapter);

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

         host = (TabHost)findViewById(R.id.tabHost);
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

        DBQueries dbQueries = new DBQueries();
        DBTransporter dbTransporter = dbQueries.getGroupsFromDB(this);
        DBTransporter dbTransporter1 = dbQueries.getClientToGroupFromDB(this);

        clientIDs = dbTransporter1.getClientID();
        groupIDs = dbTransporter1.getGroupID();
        names = dbTransporter1.getNames();
        clientIdGroupId = dbTransporter1.getIdToId();
        namesGroupKeys = dbTransporter1.getNameToId();
        namesClientKeys = dbTransporter1.getIdToName();

        groups = dbTransporter.getIdToName();
        groupsreversed = dbTransporter.getNameToId();
        groupNames = dbTransporter.getNames();

//        Log.v(TAG, groups.toString());
//        Log.v(TAG, groupNames.toString());
//        Log.v(TAG, groupsreversed.toString());

        Log.v("ats", groups.toString());

        lv = (ListView) findViewById(R.id.listViewClients);

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2,android.R.id.text1,names){
            public View getView(int position, View convertView,ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
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
                String groupID = groupIDs.get(position);
                Intent toClients = new Intent(ClientActivity.this, GroupClientActivity.class);
                String passedData = (groupsreversed.get(groupNames.get(position))+","+groupNames.get(position));
                toClients.putExtra("GroupID",passedData);
                startActivity(toClients);
            }
        });

        //  groups.get(namesClientKeys.get(names.get(position))
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clientId= clientIDs.get(position);
                Intent toCategories = new Intent(ClientActivity.this, CategoriesActivity.class);
                // we will pass on client's name,group and id in a string, all separated with a comma.
                String passedData = (namesClientKeys.get(names.get(position))+","
                        +names.get(position)+","+
                        groups.get(namesGroupKeys.get(names.get(position))) + "," +
                appraiserID);
                toCategories.putExtra("ClientId", passedData);// pass on the data
                startActivity(toCategories);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                appraiserID = data.getStringExtra("appraiserID");
            }
        }
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
        String clientId;
        switch (item.getItemId()) {
            case R.id.cnt_mnu_edit:
                Log.v(TAG, "Made it to context menu edit action");
                clientId = namesClientKeys.get(names.get(info.position));
                Intent toClientEdit = new Intent(this, EditClientActivity.class);
                toClientEdit.putExtra("ClientId", String.valueOf(clientId));
                Log.v(TAG, "ClientID: " + toClientEdit.getStringExtra("ClientId"));
                startActivity(toClientEdit);
                break;
            case R.id.cnt_mnu_delete:
                clientId = clientIDs.get(info.position);

                Log.v(TAG, "Made to context menu delete action");
                clientId = namesClientKeys.get(names.get(info.position));
                DBQueries dbQueries = new DBQueries();
                dbQueries.deleteEntryFromDB(this, DBContract.Clients.TABLE_NAME, DBContract.Clients.KEY_ID,
                        clientId);
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
    public void sortLast(ArrayList<String> al) {
        Collections.sort(al, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] split1 = o1.split(" ");
                String[] split2 = o2.split(" ");
                String lastName1 = split1[1];
                String lastName2 = split2[1];
                if (lastName1.compareTo(lastName2) > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }
}
