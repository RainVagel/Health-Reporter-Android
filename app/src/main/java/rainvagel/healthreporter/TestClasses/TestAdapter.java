package rainvagel.healthreporter.TestClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
    private final Map<String,ArrayList<AppraisalTests>> appraisals_Tests;
    private final ArrayList<Test> tests;
    private static TextView test_name;
    private static TextView result;




    public TestAdapter(Context context,  Map<String,ArrayList<AppraisalTests>> appraisals,ArrayList<Test> tests){
        super(context, R.layout.activity_tests_list,tests);
        this.context = context;
        this.tests = tests;
        this.appraisals_Tests = appraisals;
    }

    @NonNull
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(tests.get(position) != null) {//if not divider
            View rowView = inflater.inflate(R.layout.activity_tests_list,parent,false);
            test_name = (TextView) rowView.findViewById(R.id.textView);
            result = (TextView) rowView.findViewById(R.id.result);


            ArrayList<AppraisalTests> at = appraisals_Tests.get(tests.get(position).getId());
            int lastScore = -5000;
            int year = 1900;
            int day = -2;
            int month = 0;

            for(AppraisalTests a : at){
                Log.v(TAG, a.getScore());
                Log.v(TAG, a.getUpdated());
                String[] dates = a.getUpdated().split("-");
                int a_score =  Integer.parseInt(a.getScore());
                if(at.indexOf(a) == 0){
                    lastScore = Integer.parseInt(a.getScore());
                }
                else{
                    Log.v(TAG, "siin");

                    int a_year = Integer.parseInt(dates[0]);
                    int a_month = Integer.parseInt(dates[1]);
                    int a_day = Integer.parseInt(dates[2]);
                    if(a_year >= year) {
                       if(a_year > year) {
                           lastScore = a_score;
                           year = a_year;
                           day = a_day;
                           month = a_month;
                       }
                        else{
                           if(a_month>= month){
                               if(a_month > month) {
                                   lastScore = a_score;
                                   year = a_year;
                                   day = a_day;
                                   month = a_month;
                               }
                               else{
                                   if(a_day >= day){
                                       if(a_day>day){
                                           lastScore = a_score;
                                           year = a_year;
                                           day = a_day;
                                           month = a_month;
                                       }
                                       //else stay the same
                                   }
                               }
                           }
                       }
                    }

                }
            }
            Log.v("lastscore", String.valueOf(lastScore));
            result.setText(String.valueOf(lastScore));//displaay the last one updated aka the last test taken

            test_name.setText(tests.get(position).getName());

            test_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, "clicked on test name");
                }
            });

            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, "clicked on test result");
                    // we have to edit appraisals Map and then set that to the testactivity one.

                    String appraisal_id = appraisals_Tests.get(tests.get(position).getId()).get(0).getId();

                    // send the user to another activity to update the result
                    Intent editAppraisal = new Intent(context, testResultActivity.class);
                    editAppraisal.putExtra("appraisal_id", String.valueOf(appraisal_id));
                    context.startActivity(editAppraisal);

                }
            });
            return rowView;
        }
        else{

            View rowView = inflater.inflate(R.layout.activity_tests_list_divider, parent ,false);
           // test_name.setText("DIDIVDEEEEEEEEEEEEEEEEEEEER");

            //result.setText("DIVIDEEEEEEEEEEEEEEEEEEEEEEER");

            return rowView;
        }
    }
}
