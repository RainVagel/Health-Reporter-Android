package rainvagel.healthreporter.TestClasses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import rainvagel.healthreporter.R;

/**
 * Created by Karl on 07.11.2016.
 */

public class AddPreSetAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final Map<String, ArrayList<Test>> presetMap;
    private final Map<String, String> presetNames;
    private final ArrayList<String> preset_id;
    private static TextView presetname;
    private static CheckBox checked;


    public AddPreSetAdapter(Context context,Map<String, ArrayList<Test>> presetToTest, Map<String,String> presetNames, ArrayList<String> preset_id){
        super(context, R.layout.activity_add_test_list, preset_id);
        this.context = context;
        this.presetMap = presetToTest;
        this.presetNames = presetNames;
        this.preset_id = preset_id;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.activity_add_test_list, parent, false);
        presetname = (TextView) rowView.findViewById(R.id.testName);
        checked = (CheckBox) rowView.findViewById(R.id.checkBox);

        presetname.setText(presetNames.get(preset_id.get(position)));

        checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // pass on tests that belong to said preset
                    Log.v("PresetAdapter", String.valueOf(presetMap.get(preset_id.get(position)).size()));
                   AddTestActivity.selectedTests.addAll(presetMap.get(preset_id.get(position)));

                }
                else{
                    //remove the tests that belong to said preset

                    Log.v("PresetAdapter", String.valueOf(presetMap.get(preset_id.get(position)).size()));
                    AddTestActivity.selectedTests.removeAll(presetMap.get(preset_id.get(position)));
                }
            }
        });

        return rowView;
    }
}
