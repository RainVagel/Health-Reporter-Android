package rainvagel.healthreporter.TestClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import rainvagel.healthreporter.R;

/**
 * Created by Karl on 06.11.2016.
 */

public class AddTestAdapter extends ArrayAdapter<Test> {
    private final Context context;
    private final ArrayList<Test> tests;
    private static TextView testName;
    private static CheckBox checked;


    public AddTestAdapter(Context context, ArrayList<Test> tests){
        super(context, R.layout.activity_add_test_list,tests);
        this.context = context;
        this.tests = tests;
    }

    //@NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_add_test_list,parent,false);
        testName = (TextView) rowView.findViewById(R.id.testName);
        checked = (CheckBox)rowView.findViewById(R.id.checkBox);


        testName.setText(tests.get(position).getName());

        checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AddTestActivity.selectedTests.add(tests.get(position));
                }
                else{
                    AddTestActivity.selectedTests.remove(tests.get(position));
                }
            }
        });



        return rowView;


    }



}
