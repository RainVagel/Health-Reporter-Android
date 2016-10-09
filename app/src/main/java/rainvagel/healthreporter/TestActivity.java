package rainvagel.healthreporter;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    Button createButton;
    String[] fromCategoriesData;
    ArrayList<AppraisalTests> appraisalTests = new ArrayList<>();
    ArrayList<Test> testArray = new ArrayList<>();
    Map<Integer, Test> appraisalToTest = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String[] fromCategoriesData = getIntent().getStringExtra("IntentData").split(",");
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

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2,
                android.R.id.text1, appraisalTests) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setText(appraisalToTest.get(appraisalTests.get(position).getTestid()).getName());
                return view;
            }
        };
        listView.setAdapter(adapter);


    }

    public void createTestResult(View v){
        Intent intent = new Intent(this, NewTestActivity.class);
        startActivity(intent);
    }

    protected void searchTests() {
        //TODO method for searching tests
    }


    protected void getTests(){
        DBHelper  mydb = new DBHelper(TestActivity.this);
        String[] fromCategoriesData = getIntent().getStringExtra("IntentData").split(",");
        // Using clientID we have to query the database to get all appraisal_tests that belong to said Client
        // Then using the categoryID we filter out unneccessary tests.
        String[] appraisalsColumns = {DBContract.Appraisals.KEY_ID,DBContract.Appraisals.KEY_CLIENT_ID};
        String clientId = fromCategoriesData[1];
        Cursor res = mydb.getReadableDatabase().query(DBContract.Appraisals.TABLE_NAME,appraisalsColumns,null,null,null,null,null);

        int idIndex = res.getColumnIndex(DBContract.Appraisals.KEY_ID);
        int clientIndex = res.getColumnIndex(DBContract.Appraisals.KEY_CLIENT_ID);

        ArrayList<String> appraisalIDs = new ArrayList<>();
        for(res.moveToFirst();!res.isAfterLast();res.moveToNext()){
            // if appraisal client id matches with the id received from the CategoriesActivity
            // we add it to the array.
            if(res.getString(clientIndex).equals(clientId))
                appraisalIDs.add(res.getString(idIndex));
        }


        String[] columns = {DBContract.AppraisalTests.KEY_APPRAISAL_ID,DBContract.AppraisalTests.KEY_TEST_ID,
         DBContract.AppraisalTests.KEY_SCORE, DBContract.AppraisalTests.KEY_NOTE,  DBContract.AppraisalTests.KEY_TRIAL_1,
                DBContract.AppraisalTests.KEY_TRIAL_2,  DBContract.AppraisalTests.KEY_TRIAL_3, DBContract.AppraisalTests.KEY_UPDATED,
                DBContract.AppraisalTests.KEY_UPLOADED};
         res = mydb.getReadableDatabase().query(DBContract.AppraisalTests.TABLE_NAME,columns, null,null,null,null,null);

        int appraisalIndex = res.getColumnIndex(DBContract.AppraisalTests.KEY_APPRAISAL_ID);
        int testID = res.getColumnIndex(DBContract.AppraisalTests.KEY_TEST_ID);
        int scoreID = res.getColumnIndex( DBContract.AppraisalTests.KEY_SCORE);
        int noteID = res.getColumnIndex(DBContract.AppraisalTests.KEY_NOTE);
        int trial1ID = res.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_1);
        int trial2ID = res.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_2);
        int trial3ID = res.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_3);
        int updatedIndex = res.getColumnIndex(DBContract.AppraisalTests.KEY_UPDATED);
        int uploadedIndex = res.getColumnIndex(DBContract.AppraisalTests.KEY_UPLOADED);

        ArrayList<String> testIDs = new ArrayList<>();
        //add all TESTIDs to an array to later  crosscheck with categories
        // and create appraisalTest objects
        for (res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            if(appraisalIDs.contains(res.getString(appraisalIndex))) {
                testIDs.add(res.getString(testID));
                appraisalTests.add(new AppraisalTests(Integer.parseInt(res.getString(appraisalIndex)),
                        Integer.parseInt(res.getString(testID)), res.getString(scoreID), res.getString(noteID), res.getString(trial1ID),
                        res.getString(trial2ID), res.getString(trial3ID), res.getString(updatedIndex), res.getString(uploadedIndex)));
            }
        }
        columns = new String[] {DBContract.Tests.KEY_ID, DBContract.Tests.KEY_CATEGORY_ID,DBContract.Tests.KEY_NAME,DBContract.Tests.KEY_DESCRIPTION,
        DBContract.Tests.KEY_UNITS, DBContract.Tests.KEY_DECIMALS,DBContract.Tests.KEY_WEIGHT,
        DBContract.Tests.KEY_FORMULA_F,DBContract.Tests.KEY_FORMULA_M,DBContract.Tests.KEY_POSITION,
        DBContract.Tests.KEY_UPDATED,DBContract.Tests.KEY_UPLOADED};


        res = mydb.getReadableDatabase().query(DBContract.Tests.TABLE_NAME,columns,null,null,null,null,null );

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
         updatedIndex = res.getColumnIndex(DBContract.Tests.KEY_UPDATED);
         uploadedIndex = res.getColumnIndex(DBContract.Tests.KEY_UPLOADED);

        ArrayList<String> correctTests = new ArrayList<>();
        for (res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            if(res.getString(categoryIndex).equals(fromCategoriesData[0])){
                Log.v(TAG,"Tests table");
                if(testIDs.contains(res.getString(testidIndex))){
                    Test test = new Test(Integer.parseInt(res.getString(testidIndex)),
                            Integer.parseInt(res.getString(categoryIndex)),res.getString(nameIndex),res.getString(descriptionIndex),
                            res.getString(unitsIndex),res.getString(decimalsIndex),
                            res.getString(weightIndex),res.getString(formulaFIndex),res.getString(formulaMIndex),
                            Integer.parseInt(res.getString(positionIndex)),res.getString(updatedIndex),res.getString(uploadedIndex));
                    appraisalToTest.put(test.getId(), test);
                    correctTests.add(testIDs.get(testIDs.indexOf(res.getString(testidIndex))));
                    testArray.add(test);
                }
            }
        }


        res.close();
        mydb.close();

        Log.v(TAG, correctTests.toString());


    }

}