package rainvagel.healthreporter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.DBClasses.DBAppraiserTransporter;
import rainvagel.healthreporter.DBClasses.DBQueries;

public class AppraiserActivity extends AppCompatActivity {

    private String TAG = "AppraiserActivity";

    ArrayList<String> appraiserIDs;
    ArrayList<String> appraiserNames = new ArrayList<>();
    Map<String, String> appraiserIdToNames;
    Map<String, String> appraiserNameToId = new HashMap<>();
    ListView listView;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appraiser);

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
}
