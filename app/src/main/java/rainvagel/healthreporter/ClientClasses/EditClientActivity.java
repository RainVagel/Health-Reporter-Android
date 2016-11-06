package rainvagel.healthreporter.ClientClasses;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.DBContract;
import rainvagel.healthreporter.DBHelper;
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
    final ArrayList<Integer> groupIDs = new ArrayList<>();
    final Map<Integer, String> groups = new HashMap<>();
    final Map<String, Integer> groupsReversed = new HashMap<>();
    String groupName;
    String groupUpdated;
    String TAG = "EditClientActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        clientId = getIntent().getStringExtra("ClientId");
        EditText firstName = (EditText) findViewById(R.id.first_name);
        EditText lastName = (EditText) findViewById(R.id.last_name);
        EditText email = (EditText) findViewById(R.id.email_address);
        TextView textViewDay = (TextView) findViewById(R.id.textview_birth_day);
        TextView textViewMonth = (TextView) findViewById(R.id.textview_birth_month);
        TextView textviewYear = (TextView) findViewById(R.id.textview_birth_year);

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
    }
}