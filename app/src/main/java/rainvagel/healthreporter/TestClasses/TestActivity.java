package rainvagel.healthreporter.TestClasses;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.DBClasses.DBAppraisalTestsTransporter;
import rainvagel.healthreporter.DBClasses.DBAppraisalsTransporter;
import rainvagel.healthreporter.DBClasses.DBCategoriesTransporter;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTestsTransporter;
import rainvagel.healthreporter.R;


public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    Button createButton;
   public static String[] fromCategoriesData;
    ArrayList<AppraisalTests> appraisalTests = new ArrayList<>();
     ArrayList<Test> testArray = new ArrayList<>();
    public static String appraiserID = null;
    ArrayList<String> correctTests= new ArrayList<>();
   public static Map<String, ArrayList<AppraisalTests>> testToAppraisal = new HashMap<>();//TODO VALUE SHOULD BE AN ARRAY LIST OF APPRAISTESTS
    public static Intent fromCategories;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent toCategories = new Intent(this, CategoriesActivity.class);
        toCategories.putExtra("ClientId", CategoriesActivity.fromClients.getStringExtra("ClientId"));
        startActivity(toCategories);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        fromCategories = getIntent();
        fromCategoriesData = getIntent().getStringExtra("IntentData").split(",");
        getAppraiserID();
        Log.v(TAG, "appraiserID: " + appraiserID);
        //Intent from categories contains client id(index 0) and category id in sa string which has been split by ","
        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);
//        intentis ID 2 = nimi; 3 = vanus ; 4 = grupp
        getSupportActionBar().setTitle(fromCategoriesData[2] + ", " + fromCategoriesData[3]);
        getSupportActionBar().setSubtitle(fromCategoriesData[4]);

        new Thread(new Runnable() {
            public void run(){

                getTests();
            }}).start();

        ListView listView = (ListView) findViewById(R.id.listViewTests);

        TestAdapter ta = new TestAdapter(this,testToAppraisal,testArray);


        listView.setAdapter(ta);

    }

    private void getAppraiserID() {
        if (fromCategoriesData[5].equals("null")) {
            DBQueries dbQueries = new DBQueries();
            DBAppraisalsTransporter dbAppraisalsTransporter = dbQueries.getAppraisalsFromDB(this);
            DBAppraisalTestsTransporter dbAppraisalTestsTransporter = dbQueries.getAppraisalTestsFromDB(this);
            DBTestsTransporter dbTestsTransporter = dbQueries.getTestsFromDB(this);

            ArrayList<String> appraisalID = dbAppraisalsTransporter.getAppraisalID();
            Map<String, String> appraisalIdToAppraiserId = dbAppraisalsTransporter.getAppraisalIdToAppraiserId();
            Map<String, String> appraisalIdToClientId = dbAppraisalsTransporter.getAppraisalIdToClientId();
            Map<String, String> appraisalsIdToAppraisalDate = dbAppraisalsTransporter.getAppraisalIdToAppraisalDate();


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String mostRecentAppraisalID = null;
            Date mostRecent;
            Date runnerDate;
            try {
                mostRecent = dateFormat.parse("1900-01-01");
                for (String ID : appraisalID) {
                    runnerDate = dateFormat.parse(appraisalsIdToAppraisalDate.get(ID));
                    Log.v(TAG, "runnerDate: " + runnerDate);
                    Log.v(TAG, "mostRecent: " + mostRecent);
                    if (mostRecent.compareTo(runnerDate) < 0) {
                        Log.v(TAG, "got to inner if");
                        mostRecentAppraisalID = ID;
                        mostRecent = runnerDate;
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (String ID: appraisalID) {
                if (mostRecentAppraisalID.equals(ID)) {
                    appraiserID = appraisalIdToAppraiserId.get(ID);
                }
            }

        }
        else {
            appraiserID = fromCategoriesData[5];
        }
    }

    protected void getTests(){
//        String[] fromCategoriesData = getIntent().getStringExtra("IntentData").split(",");
        // Using clientID we have to query the database to get all appraisal_tests that belong to said Client
        // Then using the categoryID we filter out unneccessary tests.

        String clientId = fromCategoriesData[1];

        DBQueries dbQueries = new DBQueries();
        DBAppraisalsTransporter dbAppraisalsTransporter = dbQueries.getAppraisalsFromDB(this);
        ArrayList<String> appraisalsID = dbAppraisalsTransporter.getAppraisalID();
        Map<String, String> appraisalIdToClientId = dbAppraisalsTransporter.getAppraisalIdToClientId();
        ArrayList<String> appraisalIDs = new ArrayList<>();

        for (String ID : appraisalsID) {
            if (appraisalIdToClientId.get(ID).equals(clientId)) {
                appraisalIDs.add(ID);
            }
        }

        DBAppraisalTestsTransporter dbAppraisalTestsTransporter = dbQueries.getAppraisalTestsFromDB(this);
        ArrayList<String> appraisalTestsID = dbAppraisalTestsTransporter.getAppraisalID();
        Map<String, String> appraisalTestsIdToTestId = dbAppraisalTestsTransporter.getAppraisalIdToTestId();
        Map<String, String> appraisalTestsIdToTestScore = dbAppraisalTestsTransporter.getAppraisalIdToTestScores();
        Map<String, String> appraisalTestsIdToNote = dbAppraisalTestsTransporter.getAppraisalIdToNote();
        Map<String, String> appraisalTestsIdToTrial1 = dbAppraisalTestsTransporter.getAppraisalIdToTrial1();
        Map<String, String> appraisalTestsIdToTrial2 = dbAppraisalTestsTransporter.getAppraisalIdToTrial2();
        Map<String, String> appraisalTestsIdToTrial3 = dbAppraisalTestsTransporter.getAppraisalIdToTrial3();
        Map<String, String> appraisalTestsIdToUpdated = dbAppraisalTestsTransporter.getAppraisalIdToUpdated();
        Map<String, String> appraisalTestsIdToUploaded = dbAppraisalTestsTransporter.getAppraisalIdToUploaded();

        ArrayList<String> testIDs = new ArrayList<>();

        for (String ID : appraisalTestsID) {
            if (appraisalIDs.contains(ID)) {
                testIDs.add(appraisalTestsIdToTestId.get(ID));
                appraisalTests.add(new AppraisalTests(ID, appraisalTestsIdToTestId.get(ID),
                        appraisalTestsIdToTestScore.get(ID), appraisalTestsIdToNote.get(ID),
                        appraisalTestsIdToTrial1.get(ID), appraisalTestsIdToTrial2.get(ID),
                        appraisalTestsIdToTrial3.get(ID), appraisalTestsIdToUpdated.get(ID),
                        appraisalTestsIdToUploaded.get(ID)));
            }
        }
        //add all TESTIDs to an array to later  crosscheck with categories
        // and create appraisalTest objects

        DBTestsTransporter dbTestsTransporter = dbQueries.getTestsFromDB(this);
        ArrayList<String> testsID = dbTestsTransporter.getTestID();
        Map<String, String> testsIdToCategoryId = dbTestsTransporter.getTestIdToCategoryId();
        Map<String, String> testsIdToName = dbTestsTransporter.getTestIdToName();
        Map<String, String> testsIdToDescription = dbTestsTransporter.getTestIdToDescription();
        Map<String, String> testsIdToUnits = dbTestsTransporter.getTestIdToUnits();
        Map<String, String> testsIdToDecimals = dbTestsTransporter.getTestIdToDecimals();
        Map<String, String> testsIdToWeight = dbTestsTransporter.getTestIdToWeight();
        Map<String, String> testsIdToFormulaF = dbTestsTransporter.getTestIdToFormulaF();
        Map<String, String> testsIdToFormulaM = dbTestsTransporter.getTestIdToFormulaM();
        Map<String, String> testsIdToPosition = dbTestsTransporter.getTestIdToPosition();
        Map<String, String> testsIdToUpdated = dbTestsTransporter.getTestIdToUpdated();
        Map<String, String> testsIdToUploaded = dbTestsTransporter.getTestIdToUploaded();

        for (String ID : testsID) {
            if (testsIdToCategoryId.get(ID).equals(fromCategoriesData[0])) {
                if (testIDs.contains(ID)) {
                    Test test = new Test(ID, testsIdToCategoryId.get(ID), testsIdToName.get(ID),
                            testsIdToDescription.get(ID), testsIdToUnits.get(ID), testsIdToDecimals.get(ID),
                            testsIdToWeight.get(ID), testsIdToFormulaF.get(ID), testsIdToFormulaM.get(ID),
                            testsIdToPosition.get(ID), testsIdToUpdated.get(ID), testsIdToUploaded.get(ID));
                    correctTests.add(testIDs.get(testIDs.indexOf(ID)));
                    testArray.add(test);
                }
            }
        }

        DBCategoriesTransporter dbCategoriesTransporter = dbQueries.getCategoriesFromDB(this);
        ArrayList<String> categoriesID = dbCategoriesTransporter.getCategoriesID();
        Map<String, String> categoriesIdToParentId = dbCategoriesTransporter.getCategoriesIdToParentId();
        ArrayList<String> divider = new ArrayList<>();

        for (String ID : categoriesID) {
            if (correctTests.contains(categoriesIdToParentId.get(ID)) && !categoriesIdToParentId.get(ID).equals("null")) {
                divider.add(categoriesIdToParentId.get(ID));
            }
        }
        //retrieve dividers

        //add all appraisal for said category in to a map with the key being appraisals testID
        for(String i : correctTests){
            Log.v(TAG, "OLEN SIIN");
            Log.v(TAG, i);
            Log.v(TAG, String.valueOf(testArray.size()));

            if(testToAppraisal.containsKey(i)){//if the map already has said key
                testToAppraisal.put(i, testToAppraisal.get(i)).add(appraisalTests.get(testIDs.indexOf(i)));
            }
            else{
                ArrayList<AppraisalTests> appraisals = new ArrayList<>();
                appraisals.add(appraisalTests.get(testIDs.indexOf(i)));
                testToAppraisal.put(i,appraisals);
            }

            if(divider.contains(i)){// if current test has a divider
                testArray.add(correctTests.indexOf(i),null);
            }
        }

        //FOR TESTING PURPOSES BECAUSE NO DIVIDERS IN DATABASE AT THE MOMENT!!!!!!!!!!!!!!
        // REMOVE IF DATABASE HAS BEEN UPDATED
        // TODO
        testArray.add(1,null);
    }

}