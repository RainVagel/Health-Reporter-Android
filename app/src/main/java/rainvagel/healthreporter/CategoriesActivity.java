package rainvagel.healthreporter;

import android.content.Intent;
import android.database.Cursor;
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
    static final String TAG = "CATEGORIES ACTIVITY";
    final ArrayList<Category> categories = new ArrayList<>();
    final ArrayList<String> categorynames = new ArrayList<>();
    static String intentData;//this will contain the nr of Rows and last ID
    Intent fromClients;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        fromClients = getIntent();
        Log.v(TAG, String.valueOf(fromClients.getStringExtra("ClientId")));
        new Thread(new Runnable() {
            public void run(){


        DBHelper mydb = new DBHelper(CategoriesActivity.this);

        //Defines the columns that will be returned from the database query
        String[] columns = {DBContract.TestCategories.KEY_ID, DBContract.TestCategories.KEY_PARENT_ID, DBContract.TestCategories.KEY_NAME, DBContract.TestCategories.KEY_POSITION, DBContract.TestCategories.KEY_UPDATED, DBContract.TestCategories.KEY_UPLOADED};
        Cursor res = mydb.getReadableDatabase().query(DBContract.TestCategories.TABLE_NAME, columns, null,null,null,null,null);

        int idRow = res.getColumnIndex(DBContract.TestCategories.KEY_ID);
        int parentidRow = res.getColumnIndex(DBContract.TestCategories.KEY_PARENT_ID);
        int nameRow = res.getColumnIndex(DBContract.TestCategories.KEY_NAME);
        int posRow = res.getColumnIndex(DBContract.TestCategories.KEY_POSITION);
        int updatedRow = res.getColumnIndex(DBContract.TestCategories.KEY_UPDATED);
        int uploadedRow = res.getColumnIndex(DBContract.TestCategories.KEY_UPLOADED);

        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            // If parentid == null then real category
            // if parentid != null then a divider
            if(res.getString(parentidRow).equals("null")) {
                String name = res.getString(nameRow);
                Category cat = new Category(res.getString(idRow), res.getString(parentidRow), name
                        , res.getString(posRow), res.getString(updatedRow), res.getString(uploadedRow));
                categories.add(cat);
                categorynames.add(name);
            }
            if(res.isLast()){
                intentData = res.getString(idRow) + ","+ res.getString(posRow);

                Log.v(TAG, intentData);
            }

        }


        Log.v(TAG, intentData);
        mydb.close();//close the db connection

            }
        }).start();
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                categorynames
        );


        lv.setAdapter(arrayAdapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String entry = (String) parent.getItemAtPosition(position);
                //Based on the name of the category we retrieve the correct category object and redirect to another window
                //which displays said category data
                Category category = categories.get(categorynames.indexOf(entry));
                Log.v(TAG, category.getName());
                //After retrieving the category object we send an intent to TestActivity which contains said category id
                //Intent intent = new Intent(this, TestActivity.class);// class does not exist
                String categoryId = "2"; //dummyId, if backend has been developed then   //category.getId();
                //intent.putExtra("categoryId", categoryId);


            }
        });


    }
    public void createCategory(View v){
        Intent intent = new Intent(this, NewCategoryActivity.class);
        intent.putExtra("Data", intentData);
        startActivity(intent);
    }
}
