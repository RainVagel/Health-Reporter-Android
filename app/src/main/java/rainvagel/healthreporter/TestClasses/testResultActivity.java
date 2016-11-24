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

public class testResultActivity extends AppCompatActivity {
    private static final String TAG = "editTestResult";
    private static  String appraisal_id;
    private static Intent fromTestAdapter;
    private static NumberPicker np;

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
        int newResult = np.getValue();
        DBHelper mydb = new DBHelper(this);
        String[] columns = {DBContract.AppraisalTests.KEY_APPRAISAL_ID, DBContract.AppraisalTests.KEY_SCORE};

        SQLiteDatabase db = mydb.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.AppraisalTests.KEY_SCORE,newResult);

        String selection = DBContract.AppraisalTests.KEY_APPRAISAL_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(appraisal_id)};
        // update the database where appraisal_id = ...
        int count = db.update(DBContract.AppraisalTests.TABLE_NAME,values,selection,selectionArgs);


        // now the database should be updated and we should go back to testActivity;
        // in order to maintain functionality and to save the same state
        // we have to get the intent data that was passed to the TestActivity from CategoriesActivity;
        Intent testActivity = TestActivity.fromCategories;
        Intent back = new Intent(this, TestActivity.class);

        back.putExtra("IntentData",testActivity.getStringExtra("IntentData"));
        startActivity(back);

    }

}
