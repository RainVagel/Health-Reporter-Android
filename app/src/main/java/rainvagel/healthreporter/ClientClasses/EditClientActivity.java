package rainvagel.healthreporter.ClientClasses;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
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

import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTransporter;
import rainvagel.healthreporter.DatePickerFragment;
import rainvagel.healthreporter.EditGroupActivity;
import rainvagel.healthreporter.OnDataPass;
import rainvagel.healthreporter.R;

public class EditClientActivity extends AppCompatActivity {

    String clientId;
    String clientFirstName;
    String clientLastName;
    String clientGroupId;
    String clientBirthDate;
    String clientGender;
    String clientEmail;
    String clientUpdated;
    ArrayList<String> groupIDs = new ArrayList<>();
    Map<String, String> groups = new HashMap<>();
    Map<String, String> groupsReversed = new HashMap<>();
    String groupName;
    String groupUpdated;
    String TAG = "EditClientActivity";
    EditText firstName;
    EditText lastName;
    EditText email;
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
        textViewGroup = (TextView) findViewById(R.id.textview_group_name);
        gender = (RadioGroup) findViewById(R.id.radio_group_gender);

        DBQueries dbQueries = new DBQueries();
        ArrayList<String> clientDetails = dbQueries.getClientDetailsFromDB(this, clientId);
        clientFirstName = clientDetails.get(0);
        clientLastName = clientDetails.get(1);
        clientBirthDate = clientDetails.get(2);
        clientGender = clientDetails.get(3);
        clientGroupId = clientDetails.get(4);
        clientEmail = clientDetails.get(5);
        clientUpdated = clientDetails.get(6);

        ArrayList<String> groupDetails = dbQueries.getGroupDetailsFromDB(this, clientGroupId);
        groupName = groupDetails.get(0);
        groupUpdated = groupDetails.get(1);

        DBTransporter dbTransporter = dbQueries.getGroupsFromDB(this);
        groups = dbTransporter.getIdToName();
        groupsReversed = dbTransporter.getNameToId();
        groupIDs = dbTransporter.getGroupID();

        firstName.setText(clientFirstName);
        lastName.setText(clientLastName);
        email.setText(clientEmail);
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
                    Log.v(TAG, "Female checked");
                    clientGender = "0";
                } break;
            case R.id.radip_male:
                if (checked) {
//                    Add male as new clients gender
                    Log.v(TAG, "Male checked");
                    clientGender = "1";
                } break;
        }
    }

    public void onEditClientClicked(View view) {
        EditText day = (EditText) findViewById(R.id.edittext_birth_day);
        EditText month = (EditText) findViewById(R.id.edittext_birth_month);
        EditText year = (EditText) findViewById(R.id.editText_birth_year);

        String dayString = String.valueOf(day.getText());
        String monthString = String.valueOf(month.getText());
        String yearString = String.valueOf(year.getText());
        clientBirthDate = yearString + "-" + monthString + "-" + dayString;
        DBQueries dbQueries = new DBQueries();
        dbQueries.editClientInDB(this, clientId, firstName.getText().toString(),
                lastName.getText().toString(), clientBirthDate, email.getText().toString(),
                clientGender, clientGroupId, clientUpdated);
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
}