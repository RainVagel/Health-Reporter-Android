package rainvagel.healthreporter;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewClientActivity extends AppCompatActivity {

    final ArrayList<Integer> groupIDs = new ArrayList<>();
    final ArrayList<String> groupNames = new ArrayList<>();
    final Map<Integer, String> groups = new HashMap<>();
    final Map<String, Integer> groupsReversed = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        DBHelper mydb = new DBHelper(this);

        String[] columns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
        Cursor cursor = mydb.getReadableDatabase().query(DBContract.Groups.TABLE_NAME,
                columns, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int groupId = Integer.parseInt(cursor.getString(idIndex));
//            Log.v("NewClientActivity", "groupId: " + String.valueOf(groupId));
//            Log.v("NewClientActivity", "Made to for");
//            if (groupIDs.contains(groupId)) {
//                Log.v("NewClientActivity", "Made to contains");
            groups.put(groupId, cursor.getString(nameIndex));
            groupsReversed.put(cursor.getString(nameIndex), groupId);
            groupNames.add(cursor.getString(nameIndex));
//            }
        }
        cursor.close();
        mydb.close();

        ListView listView = (ListView) findViewById(R.id.listViewGroups);
//        Log.v("NewClientActivity", String.valueOf(R.id.listViewGroups));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, groupNames
        );
        listView.setAdapter(arrayAdapter);
//        Log.v("NewClientActivity", groupNames.toString());

    }
}
