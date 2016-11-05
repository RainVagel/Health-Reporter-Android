package rainvagel.healthreporter.CategoryClasses;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rainvagel.healthreporter.TestClasses.AddTestActivity;
import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.DBContract;
import rainvagel.healthreporter.DBHelper;
import rainvagel.healthreporter.R;
import rainvagel.healthreporter.TestClasses.TestActivity;

public class CategoriesActivity extends Activity {
    static final String TAG = "CATEGORIES ACTIVITY";
    final ArrayList<Category> categories = new ArrayList<>();
    final ArrayList<String> categorynames = new ArrayList<>();
    final ArrayList<Category> divider = new ArrayList<>();
    Intent fromClients;
    static String[] intentData;
    Toolbar tb;
    int age;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Log.v(TAG, "In categories");
        //first element is clientID, second is client's name and third is the group name

        intentData = getIntent().getStringExtra("ClientId").split(",");

        tb = (Toolbar) findViewById(R.id.my_toolbar);
        CharSequence title =  intentData[1];//first name
        CharSequence subtitle = intentData[2];//group
        tb.setTitle(title);
        tb.setSubtitle(subtitle);

        FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.floatingActionButton5);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addTestToClient(v);
            }
        });



        new Thread(new Runnable() {
            public void run(){

                getCategories();
            }}).start();



        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                categorynames
        );





        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getItemAtPosition(position);

                //Based on the name of the category we retrieve the correct category object and redirect to another window
                //which displays said category data
                //Category category = categories.get(categorynames.indexOf(entry));
                Log.v(TAG, category.getName());
                //After retrieving the category object we send an intent to TestActivity which contains said category id and clientid
                Intent intent = new Intent(CategoriesActivity.this, TestActivity.class);// class does not exist
                intent.putExtra("IntentData", category.getId()+","+intentData[0]+","+intentData[1]+","+String.valueOf(age)+"," +intentData[2]);
                Log.v(TAG, category.getId()+","+intentData[0]+","+intentData[1]+","+String.valueOf(age)+"," +intentData[2]);
                startActivity(intent);




            }
        });

        CategoriesAdapter ca = new CategoriesAdapter(this, categories);

       /* ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.activity_categories_list,android.R.id.text1,categories){
            public View getView(int position, View convertView,ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(categories.get(position).getName());
                text2.setText(categories.get(position).getId());
                text2.setTextSize(text1.getTextSize()/4);
                return view;
            }
        };*/
        lv.setAdapter(ca);

    }

    public void addTestToClient(View v) {
        Intent intent = new Intent(this, AddTestActivity.class);
        intent.putExtra("ClientData",intentData[0]);
        startActivity(intent);
    }



    public void getCategories(){


        DBHelper mydb = new DBHelper(CategoriesActivity.this);
        //retrieve the KEY_IDS OF APPRAISALS WHICH WE WILL USE TO GO TO THE APPRAISAL_TESTS TABLE

        //QUERY FILTERS


        String[] clientColumns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_BIRTHDATE};
        Cursor res = mydb.getReadableDatabase().query(DBContract.Clients.TABLE_NAME,clientColumns,null,null,null,null,null);
        int clientidIndex = res.getColumnIndex(DBContract.Clients.KEY_ID);
        int birthdateIndex = res.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);

        String birthdate = "";
        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            if(res.getString(clientidIndex).equals(intentData[0])){
                birthdate = res.getString(birthdateIndex);
            }
        }
        Log.v(TAG, "synnikuupaev: " + birthdate);
        String[] dates = birthdate.split("-");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String[] current = df.format(date).split("-");

        int age = Integer.parseInt(current[0])-Integer.parseInt(dates[0]);
        int delta_month = Integer.parseInt(current[1])-Integer.parseInt(dates[1]);//delta aka difference
        int delta_day = Integer.parseInt(current[2])-Integer.parseInt(dates[2]);

        if(delta_month <= 0){
            if(delta_month == 0){
                if(delta_day <0)
                    age -= 1;
            }
            else{
                age -=1;
            }

        }

        CharSequence titleage = intentData[1] + ", " +String.valueOf(age);//correct toolbar title with age
        tb.setTitle(titleage);


        String[] appraisalColumns = {DBContract.Appraisals.KEY_ID,DBContract.Appraisals.KEY_CLIENT_ID};
        res = mydb.getReadableDatabase().query(DBContract.Appraisals.TABLE_NAME, appraisalColumns,null,null, null, null, null);

        int idIndex = res.getColumnIndex(DBContract.Appraisals.KEY_ID);
        int client_Id = res.getColumnIndex(DBContract.Appraisals.KEY_CLIENT_ID);
        ArrayList<String> correspondingIDs = new ArrayList<String>();


        for (res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            if(res.getString(client_Id).equals(intentData[0])){
                correspondingIDs.add(res.getString(idIndex));
                Log.v(TAG, "Appraisal table");
            }
        }
        String[] columns = {DBContract.AppraisalTests.KEY_APPRAISAL_ID,DBContract.AppraisalTests.KEY_TEST_ID};
        res = mydb.getReadableDatabase().query(DBContract.AppraisalTests.TABLE_NAME,columns, null,null,null,null,null);

        int appraisalIndex = res.getColumnIndex(DBContract.AppraisalTests.KEY_APPRAISAL_ID);
        int testID = res.getColumnIndex(DBContract.AppraisalTests.KEY_TEST_ID);

        ArrayList<String> testIDs = new ArrayList<>();

        for (res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            if(correspondingIDs.contains(res.getString(appraisalIndex))){
                testIDs.add(res.getString(testID));
                Log.v(TAG, "appraisalTests");
            }
        }
        columns = new String[] {DBContract.Tests.KEY_ID, DBContract.Tests.KEY_CATEGORY_ID};


        res = mydb.getReadableDatabase().query(DBContract.Tests.TABLE_NAME,columns,null,null,null,null,null );

        int testidIndex = res.getColumnIndex(DBContract.Tests.KEY_ID);
        int categoryIndex = res.getColumnIndex(DBContract.Tests.KEY_CATEGORY_ID);

        ArrayList<String> categoriesID = new ArrayList<>();
        for (res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            if(testIDs.contains(res.getString(testidIndex))){
                Log.v(TAG,"Tests table");
                categoriesID.add(res.getString(categoryIndex));
            }
        }

        columns =new String[] {DBContract.TestCategories.KEY_ID, DBContract.TestCategories.KEY_PARENT_ID, DBContract.TestCategories.KEY_NAME, DBContract.TestCategories.KEY_POSITION, DBContract.TestCategories.KEY_UPDATED, DBContract.TestCategories.KEY_UPLOADED};
        res = mydb.getReadableDatabase().query(DBContract.TestCategories.TABLE_NAME, columns, null,null,null,null,null);

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
                if(categoriesID.contains(res.getString(idRow))) {
                    Log.v(TAG,"categories table");
                    String name = res.getString(nameRow);
                    Category cat = new Category(res.getString(idRow), res.getString(parentidRow), name
                            , res.getString(posRow), res.getString(updatedRow), res.getString(uploadedRow));
                    categories.add(cat);
                    categorynames.add(name);
                }
            }
            else{//divider
                if(categoriesID.contains(res.getString(idRow))){
                    Log.v(TAG, "divider");
                    Category cat = new Category(res.getString(idRow), res.getString(parentidRow), res.getString(nameRow)
                            , res.getString(posRow), res.getString(updatedRow), res.getString(uploadedRow));
                    divider.add(cat);
                }
            }

        }
        res.close();
        mydb.close();
    }

    @Override
    public void onBackPressed() {
        Intent toClient = new Intent(this, ClientActivity.class);
        startActivity(toClient);
    }
}
