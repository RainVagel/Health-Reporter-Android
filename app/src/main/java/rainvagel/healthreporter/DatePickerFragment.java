package rainvagel.healthreporter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by rainvagel on 30.10.16.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    OnDataPass dataPasser;
    String finalMonth;
    String finalDay;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataPasser = (OnDataPass) activity;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if (month < 10) finalMonth = "0" + month;
        else finalMonth = String.valueOf(month);
        if (day < 10) finalDay = "0" + day;
        else finalDay = String.valueOf(day);
        dataPasser.onDataPass(year + "," + finalMonth + "," + finalDay);
    }
}
