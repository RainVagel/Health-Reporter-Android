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
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.DBClasses.DBAppraisalTestsTransporter;
import rainvagel.healthreporter.DBClasses.DBAppraisalsTransporter;
import rainvagel.healthreporter.DBClasses.DBCategoriesTransporter;
import rainvagel.healthreporter.DBClasses.DBClientsTransporter;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTestsTransporter;
import rainvagel.healthreporter.DBClasses.DBTransporter;
import rainvagel.healthreporter.TestClasses.AddTestActivity;
import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.R;
import rainvagel.healthreporter.TestClasses.TestActivity;

public class CategoriesActivity extends Activity {
    static final String TAG = "CATEGORIES ACTIVITY";
    private ArrayList<Category> categories ;
    private ArrayList<String> categorynames ;
    private ArrayList<Category> divider = new ArrayList<>();
    public static Map<String, Category> forAddTest = new HashMap<>();
    public static Intent fromClients;
    public static String[] intentData;
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
        fromClients = getIntent();
        intentData = getIntent().getStringExtra("ClientId").split(",");
        categories = new ArrayList<>();
        categorynames = new ArrayList<>();

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
        Log.v(TAG+"siin",String.valueOf(categories.size()));

        CategoriesAdapter ca = new CategoriesAdapter(this, categories);

        lv.setAdapter(ca);

    }

    public void addTestToClient(View v) {
        Intent intent = new Intent(this, AddTestActivity.class);
        intent.putExtra("ClientData",intentData[0]);
        startActivity(intent);
    }

    public void getCategories(){

        //retrieve the KEY_IDS OF APPRAISALS WHICH WE WILL USE TO GO TO THE APPRAISAL_TESTS TABLE

        //QUERY FILTERS

        DBQueries dbQueries = new DBQueries();
        DBClientsTransporter dbClientsTransporter = dbQueries.getClientsFromDB(this);
        Map<String, String> clientIdToBirthDate = dbClientsTransporter.getClientIdToBirthDate();

        String birthdate = clientIdToBirthDate.get(intentData[0]);

        Log.v(TAG, "synnikuupaev: " + birthdate);
        String[] dates = birthdate.split("-");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String[] current = df.format(date).split("-");

        age = Integer.parseInt(current[0])-Integer.parseInt(dates[0]);
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

        Log.v("age",String.valueOf(age));
        CharSequence titleage = intentData[1] + ", " +String.valueOf(age);//correct toolbar title with age
        tb.setTitle(titleage);

        DBAppraisalsTransporter dbAppraisalsTransporter = dbQueries.getAppraisalsFromDB(this);
        ArrayList<String> appraisalsID = dbAppraisalsTransporter.getAppraisalID();
        Map<String, String> appraisalIdToClientId = dbAppraisalsTransporter.getAppraisalIdToClientId();

        ArrayList<String> correspondingIDs = new ArrayList<String>();

        for (String ID : appraisalsID) {
            if (appraisalIdToClientId.get(ID).equals(intentData[0])) {
                correspondingIDs.add(ID);
            }
        }

        DBAppraisalTestsTransporter dbAppraisalTestsTransporter = dbQueries.getAppraisalTestsFromDB(this);
        ArrayList<String> appraisalIDs = dbAppraisalTestsTransporter.getAppraisalID();
        Map<String, String> appraisalIdToTestId = dbAppraisalTestsTransporter.getAppraisalIdToTestId();

        ArrayList<String> testIDs = new ArrayList<>();

        for (String ID : appraisalIDs) {
            if (correspondingIDs.contains(ID)) {
                testIDs.add(appraisalIdToTestId.get(ID));
            }
        }

        DBTestsTransporter dbTestsTransporter = dbQueries.getTestsFromDB(this);
        ArrayList<String> testID = dbTestsTransporter.getTestID();
        Map<String, String> testIdToCategoryId = dbTestsTransporter.getTestIdToCategoryId();
        ArrayList<String> categoriesID = new ArrayList<>();

        for (String ID : testID) {
            if (testIDs.contains(ID)) {
                categoriesID.add(testIdToCategoryId.get(ID));
            }
        }

        DBCategoriesTransporter dbCategoriesTransporter = dbQueries.getCategoriesFromDB(this);
        ArrayList<String> categoryID = dbCategoriesTransporter.getCategoriesID();
        Map<String, String> categoryIdToParentId = dbCategoriesTransporter.getCategoriesIdToParentId();
        Map<String, String> categoryIdToName = dbCategoriesTransporter.getCategoriesIdToName();
        Map<String, String> categoryIdToPosition = dbCategoriesTransporter.getCategoriesIdToPosition();
        Map<String, String> categoryIdToUpdated = dbCategoriesTransporter.getCategoriesIdToUpdated();
        Map<String, String> categoryIdToUploaded = dbCategoriesTransporter.getCategoriesIdToUploaded();

        for (String ID : categoryID) {
            if (categoryIdToParentId.get(ID).equals("null")) {
                Category category = new Category(ID, categoryIdToParentId.get(ID), categoryIdToName.get(ID),
                        categoryIdToPosition.get(ID), categoryIdToUpdated.get(ID), categoryIdToUploaded.get(ID));
                forAddTest.put(categoryIdToName.get(ID), category);
                if (categoriesID.contains(ID)) {
                    categories.add(category);
                    categorynames.add(categoryIdToName.get(ID));
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        Intent toClient = new Intent(this, ClientActivity.class);
        startActivity(toClient);
    }
}
