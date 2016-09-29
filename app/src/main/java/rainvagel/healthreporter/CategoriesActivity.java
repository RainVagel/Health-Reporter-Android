package rainvagel.healthreporter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoriesActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        DBHelper mydb = new DBHelper(this);

        //final ArrayList<Category> categories = new ArrayList<>();
        //final ArrayList<String> categorynames = new ArrayList<>();
        final ArrayList<String> ats = new ArrayList<>();
        ats.addAll(Arrays.asList("atastastasta","asdasdasdasdasd","astatastastst"));
        Log.v("ats","ats");

        //Cursor res = mydb.getReadableDatabase().rawQuery("select * from test_categories", null);

        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                ats
        );


        lv.setAdapter(arrayAdapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // String entry = (String) parent.getItemAtPosition(position);
                //Based on the name of the category we retrieve the correct category object and redirect to another window
                //which displays said category data
                //Category category = categories.get(categorynames.indexOf(entry));
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(ats.get(position));

                //After retrieving the category object we send an intent to TestActivity which contains said category id
                //Intent intent = new Intent(this, TestActivity.class);// class does not exist
                String categoryId = "2"; //dummyId, if backend has been developed then   //category.getId();
                //intent.putExtra("categoryId", categoryId);


            }
        });


    }
}
