package rainvagel.healthreporter.ClientClasses;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTransporter;
import rainvagel.healthreporter.InsertGroupActivity;
import rainvagel.healthreporter.R;

public class NewClientActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Integer> groupIDs = new ArrayList<>();
    ArrayList<String> groupNames = new ArrayList<>();
    Map<String, String> groups = new HashMap<>();
    Map<String, String> groupsReversed = new HashMap<>();
    private FloatingActionButton fab1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(this);

        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        CharSequence title =  "Add client or group";
        tb.setTitle(title);

        DBQueries dbQueries = new DBQueries();
        DBTransporter dbTransporter = dbQueries.getGroupsFromDB(this);
        groups = dbTransporter.getIdToName();
        groupNames = dbTransporter.getNames();
        groupsReversed = dbTransporter.getNameToId();

        final ListView listView = (ListView) findViewById(R.id.listViewGroups);
//        Log.v("NewClientActivity", String.valueOf(R.id.listViewGroups));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, groupNames
        );
        listView.setAdapter(arrayAdapter);
//        Log.v("NewClientActivity", groupNames.toString());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toInsertClient = new Intent(NewClientActivity.this, InsertClientActivity.class);
                String passedData = groupsReversed.get(groupNames.get(position))
                        + "," + groupNames.get(position);
                Log.v("NewClientActivity", passedData);
                toInsertClient.putExtra("GroupID", passedData);
                startActivity(toInsertClient);
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab1:
                addNewGroup(v);
        }
    }
    public void addNewGroup(View v){
        Intent intent = new Intent(this, InsertGroupActivity.class);
        startActivity(intent);

    }
}
