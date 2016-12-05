package rainvagel.healthreporter.TestClasses;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.CategoryClasses.Category;
import rainvagel.healthreporter.DBClasses.DBAppraisalTestsTransporter;
import rainvagel.healthreporter.DBClasses.DBAppraisalsTransporter;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBPresetTestsTransporter;
import rainvagel.healthreporter.DBClasses.DBPresetsTransporter;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTestsTransporter;
import rainvagel.healthreporter.R;


public class AddTestActivity extends AppCompatActivity  {
    Spinner categories_spinner;
    List<String> categories = new ArrayList<>();
    ArrayList<Category> category_objects = new ArrayList<>() ;
    Map<String, Category> category_map = CategoriesActivity.forAddTest;
    ArrayList<Test> allSuitableTests = new ArrayList<>();
    ArrayList<Test> allTests = new ArrayList<>();
    ArrayList<String> allTestIDs = new ArrayList<>();
    ArrayList<String> allSuitableTestIDs = new ArrayList<>();
    Map<String, ArrayList<Test>> preset = new HashMap<>();//PresetID -> Corresponding preset tests.
    Map<String, String> preset_names = new HashMap<>();// PReset-ID -> Preset name
    ArrayList<String> presetTests_testid;
    ArrayList<String> presetTest_id;
    ArrayList<String> arrayPresetNames;
    public static ArrayList<Test> selectedTests = new ArrayList<>();
    ListView testListView;
    ListView presetListView;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

       // Intent toTests = new Intent(this, TestActivity.class);
        //toTests.putExtra("IntentData", TestActivity.fromCategoriesData);
       // startActivity(toTests);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        categories_spinner = (Spinner) findViewById(R.id.categories_spinner);
        Log.v("asdsad", String.valueOf(category_map.keySet().size()));
        Log.v("asdasd,",String.valueOf(category_map.values().size()));
        for (String s : category_map.keySet()){
            Log.v("asdasd,",s);
            category_objects.add(category_map.get(s));
            categories.add(s);
        }

        Log.v("addtest", String.valueOf(categories.size()));

        ArrayAdapter<String> categories_spinner_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        categories_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories_spinner.setAdapter(categories_spinner_adapter);

        categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {


                Toast.makeText(AddTestActivity.this, "Selected",
                        Toast.LENGTH_SHORT).show();
                // here we should display all the tests that match said category and don't exist in test activity.
                allSuitableTests = new ArrayList<>();
                allSuitableTestIDs = new ArrayList<String>();
                getCategoryTests(category_objects.get(arg2));
                getPresets(category_objects.get(arg2));
                testListView = (ListView)findViewById(R.id.test_list);

                Log.v("alltestsbefore adapter",String.valueOf(allSuitableTests.size()));
                AddTestAdapter ata = new AddTestAdapter(AddTestActivity.this, allSuitableTests);
                testListView.setAdapter(ata);


                Set<String> hs = new HashSet<String>();//remove all duplicates
                hs.addAll(presetTest_id);
                presetTest_id.clear();
                presetTest_id.addAll(hs);

                presetListView = (ListView) findViewById(R.id.preset_list);
                AddPreSetAdapter apsa = new AddPreSetAdapter(AddTestActivity.this, preset,preset_names,presetTest_id);
                presetListView.setAdapter(apsa);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    protected void addTests(View v){
        Log.v("addtests",String.valueOf(selectedTests.size()));
        //Remove duplicates
        Set<Test> testid = new HashSet<>();
        testid.addAll(selectedTests);
        selectedTests.clear();
        selectedTests.addAll(testid);
        Log.v("addtest",String.valueOf(selectedTests.size()));

        //no we should add those tests to the client

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//current date
        String appraiser_id = String.valueOf(133742069);
        String updated = df.format(new Date()).toString();

        String dateofappraisal = updated;
        int score_nr = 0;
        int trial1_nr = 0;
        int trial2_nr = 0;
        int trial3_nr = 0;
        String notesdb ="--------";
        String clientid = CategoriesActivity.intentData[0];

        for(Test t : selectedTests) {
            String test_id = t.getId();

            DBQueries dbQ = new DBQueries();
            dbQ.insertAppraisalTestAndAppraisalToDB(this, appraiser_id, clientid,dateofappraisal,updated, updated, test_id,score_nr,notesdb,trial1_nr,trial2_nr,
                    trial3_nr);

        }
        setData();



        //should return to categories?
        Intent toCategories = new Intent(this, CategoriesActivity.class);
        toCategories.putExtra("ClientId",CategoriesActivity.fromClients.getStringExtra("ClientId"));

        startActivity(toCategories);
    }

    protected void setData(){
        selectedTests = new ArrayList<>();
        testListView.setAdapter(new AddTestAdapter(this, allSuitableTests));
    }

    protected void getPresets(Category c){
        Map<String, String> presets = new HashMap<>();//id key and value is name
        DBHelper mydb = new DBHelper(this);
        String[] columns = {DBContract.PresetTests.KEY_PRESET_ID,DBContract.PresetTests.KEY_TEST_ID};

        DBQueries dbQueries = new DBQueries();
        DBPresetTestsTransporter dbPresetTestsTransporter = dbQueries.getPresetTestsFromDB(this);
        ArrayList<String> testID = dbPresetTestsTransporter.getTestID();
        Map<String, Set<String>> testIdToPresetId = dbPresetTestsTransporter.getTestIdToPresetId();
        preset = new HashMap<>();
        presetTests_testid = new ArrayList<>();
        presetTest_id = new ArrayList<>();


        Cursor res = mydb.getReadableDatabase().query(DBContract.PresetTests.TABLE_NAME,columns,null,null,null,null,null);
        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            if(allTestIDs.contains(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_TEST_ID)))){
                if(preset.containsKey(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID)))) {
                    ArrayList<Test> temp = preset.get(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID)));
                    temp.add(allTests.get(allTestIDs.indexOf(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_TEST_ID)))));
                    preset.put(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID)), temp);
                    //preset_names.put(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID)), res.getString(res.getColumnIndex(DBContract.PresetTests.)))
                }
                else{
                    ArrayList<Test> temp = new ArrayList<>();
                    temp.add(allTests.get(allTestIDs.indexOf(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_TEST_ID)))));
                    preset.put(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID)),temp);
                }

                presetTests_testid.add(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_TEST_ID)));
                presetTest_id.add(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID)));
            }

        }

        DBPresetsTransporter dbPresetsTransporter = dbQueries.getPresetsFromDB(this);
        ArrayList<String> presetID = dbPresetsTransporter.getPresetID();
        Map<String, String> presetIdToName = dbPresetsTransporter.getPresetIdToName();

        for (String ID : presetID) {
            if (preset.keySet().contains(ID)) {
                preset_names.put(ID, presetIdToName.get(ID));
            }
        }

        arrayPresetNames = new ArrayList<>();
        for(String s : preset_names.values()){
            Log.v("asdasd", s);
            arrayPresetNames.add(s);
        }

        Log.v("preseti suurus", String.valueOf(preset.size()));
        Log.v("presetinimede suurus", String.valueOf(preset_names.size()));
        Log.v("presetnimedarray suurus",String.valueOf(arrayPresetNames.size()));

        res.close();
        mydb.close();
    }

    protected void getCategoryTests(Category c){
        DBHelper mydb = new DBHelper(this);

        DBQueries dbQueries = new DBQueries();
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
        Map<String, String> testIdToPosition = dbTestsTransporter.getTestIdToPosition();
        Map<String, String> testsIdToUpdated = dbTestsTransporter.getTestIdToUpdated();
        Map<String, String> testIdToUploaded = dbTestsTransporter.getTestIdToUploaded();

        for (String ID : testsID) {
            Test test = new Test(ID, testsIdToCategoryId.get(ID), testsIdToName.get(ID), testsIdToDescription.get(ID),
                    testsIdToUnits.get(ID), testsIdToDecimals.get(ID), testsIdToWeight.get(ID),
                    testsIdToFormulaF.get(ID), testsIdToFormulaM.get(ID), testIdToPosition.get(ID),
                    testsIdToUpdated.get(ID), testIdToUploaded.get(ID));
            allTests.add(test);
            allTestIDs.add(ID);
            if (c.getId().equals(testsIdToCategoryId.get(ID))) {
                allSuitableTestIDs.add(ID);
                allSuitableTests.add(test);
            }
        }

        String clientid = CategoriesActivity.intentData[0];

        // now we have to get all the appraisal_tests for said client

        DBAppraisalsTransporter dbAppraisalsTransporter = dbQueries.getAppraisalsFromDB(this);
        ArrayList<String> appraisalsID = dbAppraisalsTransporter.getAppraisalID();
        Map<String, String> appraisalsIdToClientId = dbAppraisalsTransporter.getAppraisalIdToClientId();
        ArrayList<String> appraisal_tests = new ArrayList<>();

        for (String ID : appraisalsID) {
            if (appraisalsIdToClientId.get(ID).equals(clientid)) {
                appraisal_tests.add(ID);
            }
        }

        DBAppraisalTestsTransporter dbAppraisalTestsTransporter = dbQueries.getAppraisalTestsFromDB(this);
        ArrayList<String> appraisalTestsID = dbAppraisalTestsTransporter.getAppraisalID();
        Map<String, String> appraisalTestsIdToTestId = dbAppraisalTestsTransporter.getAppraisalIdToTestId();

        //now we have all the appraisals for said client
        Log.v("testids size",String.valueOf(allSuitableTestIDs.size()));
        Log.v("alltests sie",String.valueOf(allSuitableTests.size()));

        Log.v("koiktestid", String.valueOf(allTests.size()));
        Log.v("koikidetestide IDd",String.valueOf(allTestIDs.size()));

        for (String ID : appraisalTestsID) {
            if (appraisal_tests.contains(ID)) {
                if (allSuitableTestIDs.contains(appraisalTestsIdToTestId.get(ID))) {
                    allSuitableTests.remove(allSuitableTestIDs.indexOf(appraisalTestsIdToTestId.get(ID)));
                    allSuitableTestIDs.remove(appraisalTestsIdToTestId.get(ID));
                }
                if (allTestIDs.contains(appraisalTestsIdToTestId.get(ID))) {
                    allTests.remove(allTestIDs.indexOf(appraisalTestsIdToTestId.get(ID)));
                    allTestIDs.remove(appraisalTestsIdToTestId.get(ID));
                }
            }
        }

            //remove all tests from the array that already exist in test activity

        Log.v("alltestszise",String.valueOf(allSuitableTests.size()));
        Log.v("koiktestid", String.valueOf(allTests.size()));
        Log.v("koikidetestide IDd",String.valueOf(allTestIDs.size()));
    }
}