package rainvagel.healthreporter.ClientClasses;

import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import rainvagel.healthreporter.DBContract;
import rainvagel.healthreporter.DBHelper;
import rainvagel.healthreporter.DatePickerFragment;
import rainvagel.healthreporter.OnDataPass;
import rainvagel.healthreporter.R;

public class InsertClientActivity extends AppCompatActivity implements OnDataPass {

    String birthDay;
    String birthMonth;
    String birthYear;
    int gender;
    EditText firstName;
    EditText lastName;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_client);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_female:
                if (checked) {
//                    Add female as new clients gender
                    Log.v("InsertClientActivity", "Female checked");
                    gender = 0;
                } break;
            case R.id.radip_male:
                if (checked) {
//                    Add male as new clients gender
                    Log.v("InsertClientActivity", "Male checked");
                    gender = 1;
                } break;
        }
    }

    @Override
    public void onDataPass(String data) {
        Log.v("InsertClientActivity", data);

        String[] splittedData = data.split(",");
        birthDay = splittedData[2];
        birthMonth = splittedData[1];
        birthYear = splittedData[0];

        TextView textViewDay = (TextView) findViewById(R.id.textview_birth_day);
        TextView textViewMonth = (TextView) findViewById(R.id.textview_birth_month);
        TextView textViewYear = (TextView) findViewById(R.id.textview_birth_year);

        textViewDay.setText(birthDay);
        textViewMonth.setText(birthMonth);
        textViewYear.setText(birthYear);

    }

    public void showDatePickerDialog(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    public void onAddClientClicked(View view) {
        firstName = (EditText) findViewById(R.id.first_name);
        lastName = (EditText) findViewById(R.id.last_name);
        email = (EditText) findViewById(R.id.email_address);

        String firstNameString = String.valueOf(firstName.getText());
        String lastNameString = String.valueOf(lastName.getText());
        String emailString = String.valueOf(email.getText());

//        In final product must use the generator

//        String uniqueID = UUID.randomUUID().toString();
        String uniqueID = "98076";

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        Log.v("InsertClientActivity", formattedDate);
        ContentValues values = new ContentValues();
        Log.v("InsertClientActivity", getIntent().getStringExtra("GroupID"));
        String[] groupInfo = getIntent().getStringExtra("GroupID").split(",");
        String group = groupInfo[0];
        values.put(DBContract.Clients.KEY_ID, uniqueID);
        values.put(DBContract.Clients.KEY_GROUP_ID, group);
        values.put(DBContract.Clients.KEY_FIRSTNAME, firstNameString);
        values.put(DBContract.Clients.KEY_LASTNAME, lastNameString);
        values.put(DBContract.Clients.KEY_EMAIL, emailString);
        values.put(DBContract.Clients.KEY_GENDER, gender);
        values.put(DBContract.Clients.KEY_BIRTHDATE, birthYear + "-" + birthMonth + "-" + birthDay);
        values.put(DBContract.Clients.KEY_UPDATED, formattedDate);
        values.put(DBContract.Clients.KEY_UPLOADED, "0000-00-00");
        sqLiteDatabase.insert(DBContract.Clients.TABLE_NAME, null, values);
        sqLiteDatabase.close();

//        This intent is for testing pruposes. In the final version it would take to the CategoryActivity
//        with the newly created client selected

        Intent returnToClients = new Intent(this, ClientActivity.class);
        startActivity(returnToClients);
    }
}
