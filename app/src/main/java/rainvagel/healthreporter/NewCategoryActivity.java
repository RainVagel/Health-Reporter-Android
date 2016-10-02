package rainvagel.healthreporter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

public class NewCategoryActivity extends AppCompatActivity {
    static int lastId;
    static int lastPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
        // need to retrieve the number of rows and last used ID.
        Intent intent = getIntent();
        String[] intentData = intent.getStringExtra("Data").split(",");// Data that is passed on from the CategoriesActivity separates the data with a ','

        lastId = Integer.parseInt(intentData[0]);
        lastPos = Integer.parseInt(intentData[1]);

    }
    /*
    String id; -- should be automatically incremented
    String parentId; -- not needed and has to be set to null
    String name; -- only one to be entered
    String position; -- automatically will go to the end
    String updated; -- set when updated -- currently will be the date when new entry is made.
    String uploaded; -- will be set when synchronized

     */
    public void createNewCategoryInDataBase(View v){
        EditText nameValue = (EditText) findViewById(R.id.newCategory);
        String name = nameValue.getText().toString();


        Calendar cal = Calendar.getInstance();
        String currentDate = String.valueOf(cal.get(Calendar.YEAR))+"-"+String.valueOf(cal.get(Calendar.MONTH))+"-" +String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        DBHelper mydb = new DBHelper(this);

        SQLiteDatabase db = mydb.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBContract.TestCategories.KEY_ID, lastId+1);
        values.put(DBContract.TestCategories.KEY_PARENT_ID, "null");
        values.put(DBContract.TestCategories.KEY_NAME, name);
        values.put(DBContract.TestCategories.KEY_POSITION, lastPos+1);
        values.put(DBContract.TestCategories.KEY_UPDATED, currentDate);
        values.put(DBContract.TestCategories.KEY_UPLOADED, "null");

        db.insert(DBContract.TestCategories.TABLE_NAME, null, values);
        db.close();

        Intent returnToCategoriesActivity = new Intent(this,CategoriesActivity.class);
        startActivity(returnToCategoriesActivity);








    }
}
