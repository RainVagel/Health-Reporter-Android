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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.CategoryClasses.Category;
import rainvagel.healthreporter.DBContract;
import rainvagel.healthreporter.DBHelper;
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
    Map<String, Test> preset = new HashMap<>();
    Map<String, String> preset_names = new HashMap<>();
    ArrayList<String> presetTests_testid;
    ArrayList<String> presetTest_id;
    ArrayList<String> arrayPresetNames;
    public static ArrayList<Test> selectedTests = new ArrayList<>();
    ListView testListView;
    ListView presetListView;


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
                presetListView = (ListView) findViewById(R.id.preset_list);
                AddPreSetAdapter apsa = new AddPreSetAdapter(AddTestActivity.this, preset,preset_names,arrayPresetNames);
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
        //no we should add those tests to the client
        String appraiser_id = String.valueOf(133742069);
        DBHelper mydb = new DBHelper(this);
        SQLiteDatabase db = mydb.getWritableDatabase();

        String updated = "2016-11-03";

        String dateofappraisal = updated;
        int score_nr = 0;
        int trial1_nr = 0;
        int trial2_nr = 0;
        int trial3_nr = 0;
        String notesdb ="--------";
        String clientid = CategoriesActivity.intentData[0];

        for(Test t : selectedTests) {
            String uniqueID = UUID.randomUUID().toString();

            int test_id = t.getId();

            String tablename = DBContract.Appraisals.TABLE_NAME;
            ContentValues values = new ContentValues();
            values.put(DBContract.Appraisals.KEY_ID, uniqueID);
            values.put(DBContract.Appraisals.KEY_APPRAISER_ID, appraiser_id);
            values.put(DBContract.Appraisals.KEY_CLIENT_ID, clientid);
            values.put(DBContract.Appraisals.KEY_DATE, dateofappraisal);
            values.put(DBContract.Appraisals.KEY_UPDATED, updated);
            values.put(DBContract.Appraisals.KEY_UPLOADED, updated);

            db.insert(tablename, null, values);

            values = new ContentValues();
            values.put(DBContract.AppraisalTests.KEY_APPRAISAL_ID, uniqueID);
            values.put(DBContract.AppraisalTests.KEY_TEST_ID, test_id);
            values.put(DBContract.AppraisalTests.KEY_SCORE, score_nr);
            values.put(DBContract.AppraisalTests.KEY_NOTE, notesdb);
            values.put(DBContract.AppraisalTests.KEY_TRIAL_1, trial1_nr);
            values.put(DBContract.AppraisalTests.KEY_TRIAL_2, trial2_nr);
            values.put(DBContract.AppraisalTests.KEY_TRIAL_3, trial3_nr);
            values.put(DBContract.AppraisalTests.KEY_UPDATED, updated);
            values.put(DBContract.AppraisalTests.KEY_UPLOADED, updated);
            db.insert(DBContract.AppraisalTests.TABLE_NAME, null, values);

        /*
            ArrayList<AppraisalTests> at = TestActivity.testToAppraisal.get(test_id);
            at.add(new AppraisalTests(uniqueID,test_id,String.valueOf(score_nr),notesdb,String.valueOf(trial1_nr),String.valueOf(trial2_nr),String.valueOf(trial3_nr),updated,updated));
            TestActivity.testToAppraisal.put(test_id,at);
            */

        }


        selectedTests = new ArrayList<>();
        testListView.setAdapter(new AddTestAdapter(this, allSuitableTests));

        db.close();
        mydb.close();

        //should return to categories?
        Intent toCategories = new Intent(this, CategoriesActivity.class);
        toCategories.putExtra("ClientId",CategoriesActivity.fromClients.getStringExtra("ClientId"));

        startActivity(toCategories);



    }

    protected void getPresets(Category c){

        Map<String, String> presets = new HashMap<>();//id key and value is name
        DBHelper mydb = new DBHelper(this);
        String[] columns = {DBContract.PresetTests.KEY_PRESET_ID,DBContract.PresetTests.KEY_TEST_ID};

        Cursor res = mydb.getReadableDatabase().query(DBContract.PresetTests.TABLE_NAME,columns,null,null,null,null,null);
        preset = new HashMap<>();
        presetTests_testid = new ArrayList<>();
        presetTest_id = new ArrayList<>();
        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            if(allTestIDs.contains(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_TEST_ID)))){
                preset.put(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID)),allTests.get(allTestIDs.indexOf(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_TEST_ID)))));
                //preset_names.put(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID)), res.getString(res.getColumnIndex(DBContract.PresetTests.)))

                presetTests_testid.add(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_TEST_ID)));
                presetTest_id.add(res.getString(res.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID)));
            }

        }


        columns = new String[] {DBContract.Presets.KEY_ID,DBContract.Presets.KEY_NAME};

        res = mydb.getReadableDatabase().query(DBContract.Presets.TABLE_NAME,columns,null,null,null,null,null);
        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            if(preset.keySet().contains(res.getString(res.getColumnIndex(DBContract.Presets.KEY_ID)))){
                preset_names.put(res.getString(res.getColumnIndex(DBContract.Presets.KEY_ID)),res.getString(res.getColumnIndex(DBContract.Presets.KEY_NAME)));
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
        String[] columns = {DBContract.Tests.KEY_ID, DBContract.Tests.KEY_CATEGORY_ID,DBContract.Tests.KEY_NAME,DBContract.Tests.KEY_DESCRIPTION,
                DBContract.Tests.KEY_UNITS, DBContract.Tests.KEY_DECIMALS,DBContract.Tests.KEY_WEIGHT,
                DBContract.Tests.KEY_FORMULA_F,DBContract.Tests.KEY_FORMULA_M,DBContract.Tests.KEY_POSITION,
                DBContract.Tests.KEY_UPDATED,DBContract.Tests.KEY_UPLOADED};

        Cursor res = mydb.getReadableDatabase().query(DBContract.Tests.TABLE_NAME,columns,null,null,null,null,null);


        int testidIndex = res.getColumnIndex(DBContract.Tests.KEY_ID);
        int categoryIndex = res.getColumnIndex(DBContract.Tests.KEY_CATEGORY_ID);
        int nameIndex = res.getColumnIndex(DBContract.Tests.KEY_NAME);
        int descriptionIndex = res.getColumnIndex(DBContract.Tests.KEY_DESCRIPTION);
        int unitsIndex = res.getColumnIndex(DBContract.Tests.KEY_UNITS);
        int decimalsIndex = res.getColumnIndex(DBContract.Tests.KEY_DECIMALS);
        int weightIndex = res.getColumnIndex(DBContract.Tests.KEY_WEIGHT);
        int formulaFIndex = res.getColumnIndex(DBContract.Tests.KEY_FORMULA_F);
        int formulaMIndex = res.getColumnIndex(DBContract.Tests.KEY_FORMULA_M);
        int positionIndex = res.getColumnIndex(DBContract.Tests.KEY_POSITION);
        int updatedIndex = res.getColumnIndex(DBContract.Tests.KEY_UPDATED);
        int uploadedIndex = res.getColumnIndex(DBContract.Tests.KEY_UPLOADED);

        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            Test test = new Test(Integer.parseInt(res.getString(testidIndex)),
                    Integer.parseInt(res.getString(categoryIndex)), res.getString(nameIndex), res.getString(descriptionIndex),
                    res.getString(unitsIndex), res.getString(decimalsIndex),
                    res.getString(weightIndex), res.getString(formulaFIndex), res.getString(formulaMIndex),
                    Integer.parseInt(res.getString(positionIndex)), res.getString(updatedIndex), res.getString(uploadedIndex));
            allTests.add(test);
            allTestIDs.add(res.getString(testidIndex));
            if(c.getId().equals(res.getString(categoryIndex)) ) {//look only for said category indexes
                allSuitableTestIDs.add(res.getString(testidIndex));
                allSuitableTests.add(test);
            }

        }
        String clientid = CategoriesActivity.intentData[0];

        // now we have to get all the appraisal_tests for said client

        columns = new String[]{DBContract.Appraisals.KEY_ID,DBContract.Appraisals.KEY_CLIENT_ID};
        res = mydb.getReadableDatabase().query(DBContract.Appraisals.TABLE_NAME,columns,null,null,null,null,null);
        ArrayList<String> appraisal_tests = new ArrayList<>();
        int idIndex = res.getColumnIndex(DBContract.Appraisals.KEY_ID);
        int clientIndex = res.getColumnIndex(DBContract.Appraisals.KEY_CLIENT_ID);
        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()) {
            if(res.getString(clientIndex).equals(clientid))
                appraisal_tests.add(res.getString(idIndex));
        }

        //now we have all the appraisals for said client
        columns = new String[] {DBContract.AppraisalTests.KEY_APPRAISAL_ID,DBContract.AppraisalTests.KEY_TEST_ID,
                DBContract.AppraisalTests.KEY_SCORE, DBContract.AppraisalTests.KEY_NOTE,  DBContract.AppraisalTests.KEY_TRIAL_1,
                DBContract.AppraisalTests.KEY_TRIAL_2,  DBContract.AppraisalTests.KEY_TRIAL_3, DBContract.AppraisalTests.KEY_UPDATED,
                DBContract.AppraisalTests.KEY_UPLOADED};
        res = mydb.getReadableDatabase().query(DBContract.AppraisalTests.TABLE_NAME,columns,null,null,null,null,null);
        Log.v("testids size",String.valueOf(allSuitableTestIDs.size()));
        Log.v("alltests sie",String.valueOf(allSuitableTests.size()));

        Log.v("koiktestid", String.valueOf(allTests.size()));
        Log.v("koikidetestide IDd",String.valueOf(allTestIDs.size()));
        int test_id = res.getColumnIndex(DBContract.AppraisalTests.KEY_TEST_ID);
        int appraisal_id = res.getColumnIndex(DBContract.AppraisalTests.KEY_APPRAISAL_ID);
        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()) {
            if(appraisal_tests.contains(res.getString(appraisal_id))){//look only for appraisals that said client has
                if(allSuitableTestIDs.contains(res.getString(test_id))){
                    allSuitableTests.remove(allSuitableTestIDs.indexOf(res.getString(test_id)));
                    allSuitableTestIDs.remove(res.getString(test_id));
                }

                if(allTestIDs.contains(res.getString(test_id))) {
                    allTests.remove(allTestIDs.indexOf(res.getString(test_id)));
                    allTestIDs.remove(res.getString(test_id));
                }
            }

            //remove all tests from the array that already exist in test activity

        }

        Log.v("alltestszise",String.valueOf(allSuitableTests.size()));
        Log.v("koiktestid", String.valueOf(allTests.size()));
        Log.v("koikidetestide IDd",String.valueOf(allTestIDs.size()));
        res.close();
        mydb.close();

    }



}



