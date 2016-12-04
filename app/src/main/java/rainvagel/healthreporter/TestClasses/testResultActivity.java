package rainvagel.healthreporter.TestClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.UUID;

import rainvagel.healthreporter.*;
import rainvagel.healthreporter.DBClasses.DBQueries;

public class testResultActivity extends AppCompatActivity {
    private static final String TAG = "editTestResult";
    private static String appraisal_id;
    private static String test;
    private static Intent fromTestAdapter;
    private static EditText np;
    private static EditText trial1_edit;
    private static EditText trial2_edit;
    private static EditText trial3_edit;
    private static EditText note;
    private static int score;
    private static int trial1;
    private static int trial2;
    private static int trial3;
    private static String notes;
    private static DBQueries dbq;
    private static Map<String, String> app2score;
    private static Map<String, String> app2trial1;
    private static Map<String, String> app2trial2;
    private static Map<String, String> app2trial3;
    private static Map<String, String> app2note;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent toTests = new Intent(this, TestActivity.class);
        toTests.putExtra("IntentData", TestActivity.fromCategories.getStringExtra("IntentData"));
        Log.v(TAG, TestActivity.fromCategories.getStringExtra("IntentData"));

        //toCategories.putExtra("ClientId", CategoriesActivity.fromClients.getStringExtra("ClientId"));
        startActivity(toTests);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        np = (EditText) findViewById(R.id.numberPicker);
        trial1_edit = (EditText) findViewById(R.id.trial1);
        trial2_edit = (EditText) findViewById(R.id.trial2);
        trial3_edit = (EditText) findViewById(R.id.trial3);
        note = (EditText) findViewById(R.id.editText2);

        fromTestAdapter = getIntent();
        // intent always passes on the appraisal_id and test id
        String[] from = fromTestAdapter.getStringExtra("appraisal_id").split(",");
        test = from[1];
        appraisal_id = from[0];
        Log.v(TAG, "In test result acitivity");
        dbq = new DBQueries();
        app2score = dbq.getAppraisalTestsFromDB(this).getAppraisalIdToTestScores();
        app2trial1 = dbq.getAppraisalTestsFromDB(this).getAppraisalIdToTrial1();
        app2trial2 = dbq.getAppraisalTestsFromDB(this).getAppraisalIdToTrial2();
        app2trial3 = dbq.getAppraisalTestsFromDB(this).getAppraisalIdToTrial3();
        app2note = dbq.getAppraisalTestsFromDB(this).getAppraisalIdToNote();

        np.setText(app2score.get(appraisal_id));
        trial1_edit.setText(app2trial1.get(appraisal_id));
        trial2_edit.setText(app2trial2.get(appraisal_id));
        trial3_edit.setText(app2trial3.get(appraisal_id));
        note.setText(app2note.get(appraisal_id));


    }


    public void validate() {//use this method to validate inputs
        try {
            score = Integer.parseInt(np.getText().toString());
            trial1 = Integer.parseInt(trial1_edit.getText().toString());
            trial2 = Integer.parseInt(trial2_edit.getText().toString());
            trial3 = Integer.parseInt(trial3_edit.getText().toString());
            notes = note.getText().toString();

        } catch (Exception e) {
            Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateTestResult(View v) {
        validate();
        dbq.updateAppraisalTestInDB(this, appraisal_id, score, trial1, trial2, trial3, notes);
        finish();

        // now the database should be updated and we should go back to testActivity;
        // in order to maintain functionality and to save the same state
        // we have to get the intent data that was passed to the TestActivity from CategoriesActivity;

    }


    public void newTestResult(View v) {
        validate();
        EditText et = (EditText) findViewById(R.id.date);
        String[] date = et.getText().toString().split("-");
        if (date[0].length() == 4 && date[1].length() == 2 && date[2].length() == 2) {
            String client = TestActivity.fromCategories.getStringExtra("IntentData").split(",")[1];
            String appraiser = UUID.randomUUID().toString();//TODO
            if (TestActivity.appraiserID != null)
                appraiser = TestActivity.appraiserID;
            dbq.insertAppraisalTestAndAppraisalToDB(this, appraiser, client, et.getText().toString(), "2016-11-09", "2016-11-09", test, score, notes, trial1, trial2, trial3);
            finish();
        } else
            Toast.makeText(this, "Enter valid date", Toast.LENGTH_SHORT).show();


    }

    public void finish() {
        Intent testActivity = TestActivity.fromCategories;
        Intent back = new Intent(this, TestActivity.class);

        back.putExtra("IntentData", testActivity.getStringExtra("IntentData"));
        startActivity(back);
    }

}
