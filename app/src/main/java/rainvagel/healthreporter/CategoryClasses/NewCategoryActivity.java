package rainvagel.healthreporter.CategoryClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.R;

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
        String currentDate = String.valueOf(cal.get(Calendar.YEAR))+"-"
                +String.valueOf(cal.get(Calendar.MONTH))+"-"
                +String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        DBQueries dbQueries = new DBQueries();
        dbQueries.insertCategoryToDB(this, "null", name, lastPos+1, currentDate);

        Intent returnToCategoriesActivity = new Intent(this,CategoriesActivity.class);
        startActivity(returnToCategoriesActivity);








    }
}
