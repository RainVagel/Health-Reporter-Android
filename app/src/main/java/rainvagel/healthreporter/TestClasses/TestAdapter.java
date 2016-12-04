package rainvagel.healthreporter.TestClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.contextmanager.Relation;
import com.google.android.gms.vision.Frame;

import java.util.ArrayList;
import java.util.Map;

import rainvagel.healthreporter.R;

import static android.widget.LinearLayout.VERTICAL;

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
    private static HorizontalScrollView hori;
    private boolean visible = false;



    public TestAdapter(Context context,  Map<String,ArrayList<AppraisalTests>> appraisals,ArrayList<Test> tests){
        super(context, R.layout.activity_tests_list,tests);
        this.context = context;
        this.tests = tests;
        this.appraisals_Tests = appraisals;
    }

    public void setButtonsVisible(boolean isVisible) {
        visible = isVisible;
    }


    @NonNull
    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
         final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(tests.get(position) != null) {//if not divider
            final View rowView = inflater.inflate(R.layout.activity_tests_list,parent,false);


            test_name = (TextView) rowView.findViewById(R.id.textView);
            result = (TextView) rowView.findViewById(R.id.result);


            ArrayList<AppraisalTests> at = appraisals_Tests.get(tests.get(position).getId());
            int lastScore = -5000;
            AppraisalTests lastScoreAppraisal = null;
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
                    lastScoreAppraisal = a;
                }
                else{
                    Log.v(TAG, "siin");

                    int a_year = Integer.parseInt(dates[0]);
                    int a_month = Integer.parseInt(dates[1]);
                    int a_day = Integer.parseInt(dates[2]);
                    if(a_year >= year) {
                       if(a_year > year) {
                           lastScore = a_score;
                           lastScoreAppraisal = a;
                           year = a_year;
                           day = a_day;
                           month = a_month;
                       }
                        else{
                           if(a_month>= month){
                               if(a_month > month) {
                                   lastScore = a_score;
                                   lastScoreAppraisal = a;
                                   year = a_year;
                                   day = a_day;
                                   month = a_month;
                               }
                               else{
                                   if(a_day >= day){
                                       if(a_day>day){
                                           lastScore = a_score;
                                           lastScoreAppraisal = a;
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

            LinearLayout inner = (LinearLayout)rowView.findViewById(R.id.innerLay);

            for(AppraisalTests a : at){
                Log.v("idvordlus",a.getId());
                Log.v("idvordluslast",lastScoreAppraisal.getId());
                if(!a.getId().equals(lastScoreAppraisal.getId())) {
                    LinearLayout uus = new LinearLayout(context);
                    uus.setOrientation(VERTICAL);
                    TextView tv1 = new TextView(context);

                    tv1.setPadding(5, 0, 5, 10);
                    //tv1.setHeight(inner.getHeight());
                    tv1.setText(a.score);
                    uus.addView(tv1);
                    TextView tv2 = new TextView(context);
                    tv2.setText(a.getUpdated());
                    tv2.setTextSize(8);
                    tv2.setPadding(5, 0, 5, 10);


                    uus.addView(tv2);
                    inner.addView(uus);
                    ImageView div = new ImageView(context);
                    div.setBackgroundColor(Color.GRAY);
                    LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 5);

                    div.setLayoutParams(lp1);

                    inner.addView(div, new ViewGroup.LayoutParams(5, ViewGroup.LayoutParams.MATCH_PARENT));
                }

            }
            Log.v("lastscore", String.valueOf(lastScore));
            result.setText(String.valueOf(lastScore));//displaay the last one updated aka the last test taken

            test_name.setText(tests.get(position).getName());

            test_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, "clicked on test name");

                    if(visible){
                    rowView.findViewById(R.id.hsv).setVisibility(visible ? View.GONE : View.VISIBLE);
                        rowView.findViewById(R.id.innerLay).setVisibility(visible ? View.GONE : View.VISIBLE);
                       // rowView.findViewById(R.id.nupp).setVisibility(visible ? View.GONE : View.VISIBLE);
                        Log.v("PEAKS MINEMA N*H",String.valueOf(visible));

                        setButtonsVisible(!visible);}

                    else{

                    rowView.findViewById(R.id.hsv).setVisibility(!visible ? View.VISIBLE: View.GONE);
                        rowView.findViewById(R.id.innerLay).setVisibility(!visible ? View.VISIBLE: View.GONE);
                       // rowView.findViewById(R.id.nupp).setVisibility(!visible ? View.VISIBLE: View.GONE);
                        Log.v("MUUTSIN NÄHTAVAKS",String.valueOf(visible));

                        setButtonsVisible(!visible);}
                }
            });

            result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v(TAG, "clicked on test result");
                    // we have to edit appraisals Map and then set that to the testactivity one.

                    String appraisal_id = appraisals_Tests.get(tests.get(position).getId()).get(0).getId() + "," + appraisals_Tests.get(tests.get(position).getId()).get(0).getTestid();

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
