package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBPresetsTransporter;
import rainvagel.healthreporter.DBClasses.DBQueries;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 04/12/2016.
 */

public class GetPresetsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBPresetsTransporter transporter;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        ArrayList<String> presetID = new ArrayList<>();
        Map<String, String> presetIdToName = new HashMap<>();
        Map<String, String> presetIdToUpdated = new HashMap<>();
        Map<String, String> presetIdToUploaded = new HashMap<>();

        String[] columns = {DBContract.Presets.KEY_ID, DBContract.Presets.KEY_NAME,
                DBContract.Presets.KEY_UPDATED, DBContract.Presets.KEY_UPLOADED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Presets.TABLE_NAME, columns,
                null, null, null, null, null);

        String presetIDWorkable;
        int presetIndex = cursor.getColumnIndex(DBContract.Presets.KEY_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.Presets.KEY_NAME);
        int updatedIndex = cursor.getColumnIndex(DBContract.Presets.KEY_UPDATED);
        int uploadedIndex = cursor.getColumnIndex(DBContract.Presets.KEY_UPLOADED);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            presetIDWorkable = cursor.getString(presetIndex);
            presetID.add(presetIDWorkable);
            presetIdToName.put(presetIDWorkable, cursor.getString(nameIndex));
            presetIdToUpdated.put(presetIDWorkable, cursor.getString(updatedIndex));
            presetIdToUploaded.put(presetIDWorkable, cursor.getString(uploadedIndex));
        }

        cursor.close();
        transporter = new DBPresetsTransporter(presetID, presetIdToName, presetIdToUpdated, presetIdToUploaded);
    }

    @Test
    public void getPresets() {
        DBPresetsTransporter newTransporter = queries.getPresetsFromDB(instrumentation.getTargetContext());
        assertEquals(transporter.getPresetID(), newTransporter.getPresetID());
        assertEquals(transporter.getPresetIdToName(), newTransporter.getPresetIdToName());
        assertEquals(transporter.getPresetIdToUpdated(), newTransporter.getPresetIdToUpdated());
        assertEquals(transporter.getPresetIdToUploaded(), newTransporter.getPresetIdToUploaded());
    }

    @After
    public void tearDown() {
        database.close();
    }
}
