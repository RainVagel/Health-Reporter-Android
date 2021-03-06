package rainvagel.healthreporter.AppraiserClasses;

import android.app.Activity;
import android.content.Intent;
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

import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.DBClasses.DBAppraiserTransporter;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.R;

public class AppraiserActivity extends AppCompatActivity {

    private String TAG = "AppraiserActivity";

    ArrayList<String> appraiserIDs;
    ArrayList<String> appraiserNames = new ArrayList<>();
    Map<String, String> appraiserIdToNames;
    Map<String, String> appraiserNameToId = new HashMap<>();
    ListView listView;
    private FloatingActionButton floatingActionButton;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraiser);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_appraiser);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppraiserActivity.this, NewAppraiserActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        DBQueries dbQueries = new DBQueries();
        DBAppraiserTransporter dbAppraiserTransporter = dbQueries.getAppraisersFromDB(this);

        appraiserIDs = dbAppraiserTransporter.getAppraiserID();
        appraiserIdToNames = dbAppraiserTransporter.getAppraiserIdToName();

        for (String ID: appraiserIDs) {
            appraiserNames.add(appraiserIdToNames.get(ID));
            appraiserNameToId.put(appraiserIdToNames.get(ID), ID);
        }

        listView = (ListView) findViewById(R.id.listViewAppraisers);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appraiserNames);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toClientActivity = new Intent(AppraiserActivity.this, ClientActivity.class);
                String passedData = (appraiserIDs.get(position));
                toClientActivity.putExtra("appraiserID", passedData);
                setResult(Activity.RESULT_OK, toClientActivity);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                Intent toClientActivity = new Intent(AppraiserActivity.this, ClientActivity.class);
                String passedData = data.getStringExtra("uuid");
                toClientActivity.putExtra("appraiserID", passedData);
                setResult(Activity.RESULT_OK, toClientActivity);
                finish();
            }
        }
    }
}
