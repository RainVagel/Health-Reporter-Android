package rainvagel.healthreporter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.ClientClasses.EditClientActivity;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTransporter;

public class EditGroupActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String> groupNames = new ArrayList<>();
    Map<String, String> groups = new HashMap<>();
    Map<String, String> groupsReversed = new HashMap<>();
    private FloatingActionButton fab1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        DBQueries dbQueries = new DBQueries();
        DBTransporter dbTransporter = dbQueries.getGroupsFromDB(this);

        groups = dbTransporter.getIdToName();
        groupsReversed = dbTransporter.getNameToId();
        groupNames = dbTransporter.getNames();

        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(this);

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
                Intent returnIntent = new Intent(EditGroupActivity.this, EditClientActivity.class);
                String passedData = groupsReversed.get(groupNames.get(position))
                        + "," + groupNames.get(position);
                returnIntent.putExtra("group", passedData);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
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
        Intent intent = new Intent(this, InsertGroupActivity2.class);
        startActivity(intent);
    }
}
