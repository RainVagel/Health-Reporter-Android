package rainvagel.healthreporter.TestClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import rainvagel.healthreporter.R;

/**
 * Created by Karl on 10/28/2016.
 */

public class TestAdapter extends ArrayAdapter<Test> {
    private static final String TAG = "TESTADAPTER";
    private final Context context;
    private final Map<Integer,AppraisalTests> appraisals;
    private final ArrayList<Test> tests;

    public TestAdapter(Context context,  Map<Integer,AppraisalTests> appraisals,ArrayList<Test> tests){
        super(context, R.layout.activity_tests_list,tests);
        this.context = context;
        this.tests = tests;
        this.appraisals = appraisals;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_tests_list,parent,false);
        TextView tv  = (TextView) rowView.findViewById(R.id.textView);
        TextView result = (TextView) rowView.findViewById(R.id.result);


        result.setText(appraisals.get(tests.get(position).getId()).getTrial1());

        tv.setText(tests.get(position).getName());

        return rowView;

    }
}
