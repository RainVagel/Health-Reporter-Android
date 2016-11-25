package rainvagel.healthreporter;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;

public class InsertGroupActivity2 extends AppCompatActivity implements OnDataPass {


    EditText groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_group);
    }

    @Override
    public void onDataPass(String data) {

    }

    public void onAddGroupClicked(View view){
        groupName = (EditText) findViewById(R.id.Group_name);
        String groupNameString = String.valueOf(groupName.getText());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());

        DBQueries dbQueries = new DBQueries();
        dbQueries.insertGroupToDB(this, groupNameString, formattedDate);

        Intent returnToGroups = new Intent(this, EditGroupActivity.class);
        startActivity(returnToGroups);
    }
}
