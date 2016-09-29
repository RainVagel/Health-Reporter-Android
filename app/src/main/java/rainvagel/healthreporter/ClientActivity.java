package rainvagel.healthreporter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        //DUMMYLIST
        ArrayList<String> asi = new ArrayList<>();
        asi.add("Karl");
        asi.add("Kaarel");
        asi.add("Cornelia");
        asi.add("Rain");

        ListView lv = (ListView) findViewById(R.id.listViewClients);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                asi);
        lv.setAdapter(arrayAdapter);

        ListView elv = (ListView) findViewById(R.id.listViewGroups);
        elv.setAdapter(arrayAdapter);


    }
}
