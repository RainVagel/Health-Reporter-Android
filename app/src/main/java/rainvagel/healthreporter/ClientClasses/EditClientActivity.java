package rainvagel.healthreporter.ClientClasses;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.DBContract;
import rainvagel.healthreporter.DBHelper;
import rainvagel.healthreporter.DatePickerFragment;
import rainvagel.healthreporter.EditGroupActivity;
import rainvagel.healthreporter.OnDataPass;
import rainvagel.healthreporter.R;

public class EditClientActivity extends AppCompatActivity implements OnDataPass{

    String clientId;
    String clientFirstName;
    String clientLastName;
    String clientGroupId;
    String clientBirthDate;
    String clientGender;
    String clientEmail;
    String clientUpdated;
    final ArrayList<Integer> groupIDs = new ArrayList<>();
    final Map<Integer, String> groups = new HashMap<>();
    final Map<String, Integer> groupsReversed = new HashMap<>();
    String groupName;
    String groupUpdated;
    String TAG = "EditClientActivity";
    EditText firstName;
    EditText lastName;
    EditText email;
    TextView textViewDay;
    TextView textViewMonth;
    TextView textviewYear;
    TextView textViewGroup;
    RadioGroup gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);

        clientId = getIntent().getStringExtra("ClientId");
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email_address);
        textViewDay = (TextView) findViewById(R.id.textview_birth_day);
        textViewMonth = (TextView) findViewById(R.id.textview_birth_month);
        textviewYear = (TextView) findViewById(R.id.textview_birth_year);
        textViewGroup = (TextView) findViewById(R.id.textview_group_name);
        gender = (RadioGroup) findViewById(R.id.radio_group_gender);

        DBHelper dbHelper = new DBHelper(this);
        String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME,
        DBContract.Clients.KEY_GROUP_ID, DBContract.Clients.KEY_EMAIL, DBContract.Clients.KEY_BIRTHDATE,
        DBContract.Clients.KEY_GENDER, DBContract.Clients.KEY_UPDATED};
        Cursor cursor = dbHelper.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns,
                null, null, null, null, null);

        int clientIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int genderIndex = cursor.getColumnIndex(DBContract.Clients.KEY_GENDER);
        int groupIndex = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int emailIndex = cursor.getColumnIndex(DBContract.Clients.KEY_EMAIL);
        int birthDateIndex = cursor.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);
        int updatedIndex = cursor.getColumnIndex(DBContract.Clients.KEY_UPDATED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (clientId.equals(cursor.getString(clientIndex))) {
                clientFirstName = cursor.getString(firstNameIndex);
                clientLastName = cursor.getString(lastNameIndex);
                clientBirthDate = cursor.getString(birthDateIndex);
                clientGender = cursor.getString(genderIndex);
                clientGroupId = cursor.getString(groupIndex);
                clientEmail = cursor.getString(emailIndex);
                clientUpdated = cursor.getString(updatedIndex);
            }
        }
        cursor.close();

        String[] columnsGroups = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME,
                DBContract.Groups.KEY_UPDATED};
        cursor = dbHelper.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, columnsGroups,
                null, null, null, null, null);
        groupIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int groupNameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);
        int groupUpdatedIndex = cursor.getColumnIndex(DBContract.Groups.KEY_UPDATED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (clientGroupId.equals(cursor.getString(groupIndex))) {
                groupName = cursor.getString(groupNameIndex);
                groupUpdated = cursor.getString(groupUpdatedIndex);
            }
            groupIDs.add(Integer.parseInt(cursor.getString(groupIndex)));
            groups.put(Integer.parseInt(cursor.getString(groupIndex)), cursor.getString(groupNameIndex));
            groupsReversed.put(cursor.getString(groupNameIndex), Integer.parseInt(cursor.getString(groupIndex)));
        }
        cursor.close();

        String[] date = clientBirthDate.split("-");

        firstName.setText(clientFirstName);
        lastName.setText(clientLastName);
        email.setText(clientEmail);
        textViewDay.setText(date[2]);
        textViewMonth.setText(date[1]);
        textviewYear.setText(date[0]);
        textViewGroup.setText(groupName);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        clientUpdated = simpleDateFormat.format(calendar.getTime());

        if (clientGender.equals("1")) {
            gender.check(R.id.radip_male);
        } else gender.check(R.id.radio_female);
    }

    public void onEditGroupClicked(View view) {
        Intent toEditGroup = new Intent(this, EditGroupActivity.class);
        startActivityForResult(toEditGroup, 100);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_female:
                if (checked) {
//                    Add female as new clients gender
                    Log.v("InsertClientActivity", "Female checked");
                    clientGender = "0";
                } break;
            case R.id.radip_male:
                if (checked) {
//                    Add male as new clients gender
                    Log.v("InsertClientActivity", "Male checked");
                    clientGender = "1";
                } break;
        }
    }

    public void onEditClientClicked(View view) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.Clients.KEY_FIRSTNAME, firstName.getText().toString());
        contentValues.put(DBContract.Clients.KEY_LASTNAME, lastName.getText().toString());
        contentValues.put(DBContract.Clients.KEY_BIRTHDATE, clientBirthDate);
        contentValues.put(DBContract.Clients.KEY_EMAIL, email.getText().toString());
        contentValues.put(DBContract.Clients.KEY_GENDER, clientGender);
        contentValues.put(DBContract.Clients.KEY_GROUP_ID, clientGroupId);
        contentValues.put(DBContract.Clients.KEY_UPDATED, clientUpdated);
        sqLiteDatabase.update(DBContract.Clients.TABLE_NAME, contentValues,
                DBContract.Clients.KEY_ID + "=" + clientId, null);
        sqLiteDatabase.close();
        Intent toClientActivity = new Intent(this, ClientActivity.class);
        startActivity(toClientActivity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                String[] groupData = data.getStringExtra("group").split(",");
                Log.v(TAG, "Data1: " + groupData[0]);
                Log.v(TAG, "Data2: " + groupData[1]);
                clientGroupId = groupData[0];
                textViewGroup.setText(groupData[1]);
            }
        }
    }

    public void showDatePickerDialog(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDataPass(String data) {
        Log.v(TAG, data);

        String[] splittedData = data.split(",");
        String birthDay = String.valueOf(Integer.parseInt(splittedData[2]) + 1);
        String birthMonth = String.valueOf(Integer.parseInt(splittedData[1]) + 1);
        String birthYear = splittedData[0];

        TextView textViewDay = (TextView) findViewById(R.id.textview_birth_day);
        TextView textViewMonth = (TextView) findViewById(R.id.textview_birth_month);
        TextView textViewYear = (TextView) findViewById(R.id.textview_birth_year);

        textViewDay.setText(birthDay);
        textViewMonth.setText(birthMonth);
        textViewYear.setText(birthYear);

        clientBirthDate = birthYear + "-" + birthMonth + "-" + birthDay;
    }
}