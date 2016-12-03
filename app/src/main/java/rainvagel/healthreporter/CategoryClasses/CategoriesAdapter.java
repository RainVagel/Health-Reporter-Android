package rainvagel.healthreporter.CategoryClasses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import rainvagel.healthreporter.R;

/**
 * Created by Karl on 10/26/2016.
 */

public class CategoriesAdapter extends ArrayAdapter<Category> {

    private final Context context;
    private final ArrayList<Category> categories;


    public CategoriesAdapter(Context context,ArrayList<Category>categories){
        super(context, R.layout.activity_categories_list,categories);
        this.context = context;
        this.categories=categories;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_categories_list,parent,false);
        TextView tv = (TextView) rowView.findViewById(R.id.textView);
        TextView nv = (TextView) rowView.findViewById(R.id.number);
        Log.v("adapter", String.valueOf(categories.size()));
        Log.v("adapterpos", String.valueOf(position));
        int category_score = CategoriesActivity.getCategoryScores(categories.get(position),context);
        nv.setText(String.valueOf(category_score));
        if(category_score <20)
            nv.setBackgroundResource(R.drawable.bg_red);
        else if(category_score <40)
            nv.setBackgroundResource(R.drawable.bg_orange);
        else if(category_score<60)
            nv.setBackgroundResource(R.drawable.bg_yellow);
        else if(category_score<80)
            nv.setBackgroundResource(R.drawable.bg_green);
        else
            nv.setBackgroundResource(R.drawable.bg_blue);

        tv.setText(categories.get(position).getName());

        return rowView;

    }




}
