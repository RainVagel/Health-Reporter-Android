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

import rainvagel.healthreporter.ClientClasses.ClientActivity;

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
        String uniqueID = "9999";
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());

        ContentValues values = new ContentValues();

        values.put(DBContract.Groups.KEY_ID, uniqueID);
        values.put(DBContract.Groups.KEY_NAME,groupNameString);
        values.put(DBContract.Groups.KEY_UPDATED,formattedDate);
        values.put(DBContract.Groups.KEY_UPLOADED,"0000-00-00");
        sqLiteDatabase.insert(DBContract.Groups.TABLE_NAME,null,values);
        sqLiteDatabase.close();


        Intent returnToGroups = new Intent(this, EditGroupActivity.class);
        startActivity(returnToGroups);
    }
}
