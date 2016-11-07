package rainvagel.healthreporter.TestClasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rainvagel.healthreporter.R;

public class NewTestActivity extends AppCompatActivity {

    Button create;
    EditText name;
    EditText description;
    EditText units;
    EditText weight;
    EditText formulaF; //A test can be a user-supplied formula (a simple equation) which contains the results
    // of other tests. When a test result is entered, any formulas which contain that test should be evaluated.
    EditText formulaM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
    }

    public void create() {
        create = (Button)findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });


    }
}
