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
import java.util.Map;

import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.CategoryClasses.Category;
import rainvagel.healthreporter.DBClasses.DBClientsTransporter;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTransporter;

public class GroupClientActivity extends AppCompatActivity {

    final ArrayList<Category> categories = new ArrayList<>();
    ArrayList<String> clientnames = new ArrayList<>();
    ArrayList<String> clientIDs = new ArrayList<>();
    Intent toCategories;
    static String[] intentData;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_client);

       intentData = getIntent().getStringExtra("GroupID").split(",");

        tb = (Toolbar) findViewById(R.id.my_toolbar);
        String groupID = intentData[0];
        String title = intentData[1];//Group name

        tb.setTitle(title);

        DBQueries dbQueries = new DBQueries();
        DBClientsTransporter dbClientsTransporter = dbQueries.getClientsFromDB(this);
        ArrayList<String> clientId = dbClientsTransporter.getClientID();
        Map<String,String> clientIdToFirstName = dbClientsTransporter.getClientIdToFirstName();
        Map<String, String> clientIdToLastName = dbClientsTransporter.getClientIdToLastName();
        Map<String, String> clientIdToGroupId = dbClientsTransporter.getClientIdToGroupId();
        for (String id : clientId) {
            if (clientIdToGroupId.get(id).equals(intentData[0])) {
                clientnames.add(clientIdToFirstName.get(id) + " " + clientIdToLastName.get(id));
                clientIDs.add(id);
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
                String clientId= clientIDs.get(position);
                Intent toCategories = new Intent(GroupClientActivity.this, CategoriesActivity.class);
                // we will pass on client's name,group and id in a string, all separated with a comma.
                String passedData =(String.valueOf(clientId)+","+clientnames.get(position)+","+ intentData[1] + ",null");
                Log.v("client intet", passedData);
                toCategories.putExtra("ClientId", passedData);// pass on the data

                startActivity(toCategories);
            }
        });

    }
}