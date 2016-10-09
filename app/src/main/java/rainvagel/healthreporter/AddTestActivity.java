package rainvagel.healthreporter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.Calendar;
import java.util.StringTokenizer;

public class AddTestActivity extends AppCompatActivity {

    EditText appraiserId;
    EditText testId;
    EditText score;
    EditText note;
    EditText trial1;
    EditText trial2;
    EditText trial3;
    EditText date;

    String clientId;
    Calendar cal;
    String currentDate;
    DBHelper dbHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        Intent intent = getIntent();
        clientId = intent.getStringExtra("ClientData");
    }

    public String getAppraisalId() {
        String[] appraisalIdColumn = {DBContract.Appraisals.KEY_ID};
        Cursor cursor = dbHelper.getReadableDatabase().query(DBContract.Appraisals.TABLE_NAME,appraisalIdColumn,null,null,null,null,null);
        int appraisalIdIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_ID);

        int appraisalId = 0;
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            if (Integer.parseInt(cursor.getString(appraisalIdIndex)) > appraisalId) {
                appraisalId = Integer.parseInt(cursor.getString(appraisalIdIndex));
            }
        }
        cursor.close();
        return String.valueOf(appraisalId+1);
    }

    public void complete() {
        appraiserId = (EditText) findViewById(R.id.appraiserID);
        testId = (EditText) findViewById(R.id.testID);
        score = (EditText) findViewById(R.id.score);
        note = (EditText) findViewById(R.id.note);
        trial1 = (EditText) findViewById(R.id.trial1);
        trial2 = (EditText) findViewById(R.id.trial2);
        trial3 = (EditText) findViewById(R.id.trial3);
        date = (EditText) findViewById(R.id.date);
        cal = Calendar.getInstance();
        currentDate = String.valueOf(cal.get(Calendar.YEAR))+"-"+String.valueOf(cal.get(Calendar.MONTH))+"-" +String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.AppraisalTests.KEY_APPRAISAL_ID, getAppraisalId());
        values.put(DBContract.AppraisalTests.KEY_TEST_ID, String.valueOf(testId.getText()));
        values.put(DBContract.AppraisalTests.KEY_SCORE, String.valueOf(score.getText()));
        values.put(DBContract.AppraisalTests.KEY_NOTE, String.valueOf(note.getText()));
        values.put(DBContract.AppraisalTests.KEY_TRIAL_1, String.valueOf(trial1.getText()));
        values.put(DBContract.AppraisalTests.KEY_TRIAL_2, String.valueOf(trial2.getText()));
        values.put(DBContract.AppraisalTests.KEY_TRIAL_3, String.valueOf(trial3.getText()));
        values.put(DBContract.AppraisalTests.KEY_UPDATED, currentDate);
        values.put(DBContract.AppraisalTests.KEY_UPLOADED, "null");

        ContentValues values1 = new ContentValues();
        values1.put(DBContract.Appraisals.KEY_ID, getAppraisalId());
        values1.put(DBContract.Appraisals.KEY_APPRAISER_ID, String.valueOf(appraiserId));
        values1.put(DBContract.Appraisals.KEY_CLIENT_ID, clientId);
        values1.put(DBContract.Appraisals.KEY_DATE, currentDate);
        values1.put(DBContract.Appraisals.KEY_UPDATED, currentDate);
        values1.put(DBContract.Appraisals.KEY_UPLOADED, "null");

        db.insert(DBContract.AppraisalTests.TABLE_NAME, null, values);
        db.insert(DBContract.Appraisals.TABLE_NAME, null, values1);
        db.close();

        Intent returnToCategoriesActivity = new Intent(this,CategoriesActivity.class);
        startActivity(returnToCategoriesActivity);
    }

}
