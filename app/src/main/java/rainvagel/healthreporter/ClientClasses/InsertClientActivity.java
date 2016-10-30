package rainvagel.healthreporter.ClientClasses;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import rainvagel.healthreporter.DatePickerFragment;
import rainvagel.healthreporter.OnDataPass;
import rainvagel.healthreporter.R;

public class InsertClientActivity extends AppCompatActivity implements OnDataPass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_client);
    }

//    TODO
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_female:
                if (checked) {
//                    Add female as new clients gender
                } break;
            case R.id.radip_male:
                if (checked) {
//                    Add male as new clients gender
                } break;
        }
    }

    @Override
    public void onDataPass(String data) {
        Log.v("InsertClientActivity", data);
    }

    public void showDatePickerDialog(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }
}
