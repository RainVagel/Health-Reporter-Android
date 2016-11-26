package rainvagel.healthreporter.ClientClasses;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.DBClasses.DBQueries;
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
    String groupName;

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
        birthDay = String.valueOf(Integer.parseInt(splittedData[2]) + 1);
        birthMonth = String.valueOf(Integer.parseInt(splittedData[1]) + 1);
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

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        Log.v("InsertClientActivity", formattedDate);
        Log.v("InsertClientActivity", getIntent().getStringExtra("GroupID"));
        String[] groupInfo = getIntent().getStringExtra("GroupID").split(",");
        String group = groupInfo[0];
        groupName = groupInfo[1];

        DBQueries dbQueries = new DBQueries();
        String uuid = dbQueries.insertClientToDB(this, group, firstNameString, lastNameString, emailString, gender,
                birthYear, birthMonth, birthDay, formattedDate);

        Intent toCategories = new Intent(this, CategoriesActivity.class);
        String passedData = (uuid + "," + firstNameString + " " + lastNameString + "," + groupName);
        toCategories.putExtra("ClientId", passedData);
        startActivity(toCategories);
    }
}
