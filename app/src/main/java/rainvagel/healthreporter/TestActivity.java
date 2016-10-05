package rainvagel.healthreporter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;


public class TestActivity extends AppCompatActivity {

    Button createButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //DBHelper dbHelper = new DBHelper(this);
    }

    public void createTest(View v){
        Intent intent = new Intent(this, NewTestActivity.class);
        startActivity(intent);
    }

    protected void searchTests() {
        //TODO method for searching tests
    }

}