package rainvagel.healthreporter.AppraiserClasses;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.R;

public class NewAppraiserActivity extends AppCompatActivity {

    private String TAG = "NewAppraiserActivity";
    EditText firstName;
    EditText lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appraiser);
    }

    public void onAddAppraiserClicked(View view) {
        firstName = (EditText) findViewById(R.id.appraiser_firstname);
        lastName = (EditText) findViewById(R.id.appraiser_lastname);

        String firstNameString = String.valueOf(firstName.getText());
        String lastNameString = String.valueOf(lastName.getText());

        DBQueries dbQueries = new DBQueries();
        String uuid = dbQueries.insertAppraiserToDB(this, firstNameString, lastNameString);

        Log.v(TAG, "uuid: " + uuid);

        Intent intent = new Intent();
        intent.putExtra("uuid", uuid);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
