package rainvagel.healthreporter.TestClasses;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;

import rainvagel.healthreporter.*;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.DBClasses.DBQueries;

public class testResultActivity extends AppCompatActivity {
    private static final String TAG = "editTestResult";
    private static  String appraisal_id;
    private static Intent fromTestAdapter;
    private static NumberPicker np;



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

        np = (NumberPicker) findViewById(R.id.numberPicker);
        np.setMinValue(0);
        np.setMaxValue(25);
        fromTestAdapter = getIntent();
        // intent always passes on the appraisal_id
        appraisal_id = fromTestAdapter.getStringExtra("appraisal_id");
        Log.v(TAG, "In test result acitivity");
    }

    public void updateTestResult(View v){
        DBQueries dbq = new DBQueries();
        dbq.updateAppraisalTestInDB(this, appraisal_id, np.getValue());

        // now the database should be updated and we should go back to testActivity;
        // in order to maintain functionality and to save the same state
        // we have to get the intent data that was passed to the TestActivity from CategoriesActivity;
        Intent testActivity = TestActivity.fromCategories;
        Intent back = new Intent(this, TestActivity.class);

        back.putExtra("IntentData",testActivity.getStringExtra("IntentData"));
        startActivity(back);

    }

}
