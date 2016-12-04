package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBPresetTestsTransporter;
import rainvagel.healthreporter.DBClasses.DBPresetsTransporter;
import rainvagel.healthreporter.DBClasses.DBQueries;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 04/12/2016.
 */

public class GetPresetTestsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBPresetTestsTransporter transporter;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        ArrayList<String> testsID = new ArrayList<>();
        Map<String, Set<String>> testsIdToPresetId = new HashMap<>();
        Map<String, String> testsIdToUpdated = new HashMap<>();
        Map<String, String> testsIdToUploaded = new HashMap<>();

        String[] columns = {DBContract.PresetTests.KEY_TEST_ID, DBContract.PresetTests.KEY_PRESET_ID,
                DBContract.PresetTests.KEY_UPDATED, DBContract.PresetTests.KEY_UPLOADED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.PresetTests.TABLE_NAME, columns,
                null, null, null, null, null);

        String testsIDWorkable;
        int testIndex = cursor.getColumnIndex(DBContract.PresetTests.KEY_TEST_ID);
        int presetIndex = cursor.getColumnIndex(DBContract.PresetTests.KEY_PRESET_ID);
        int updatedIndex = cursor.getColumnIndex(DBContract.PresetTests.KEY_UPDATED);
        int uploadedIndex = cursor.getColumnIndex(DBContract.PresetTests.KEY_UPLOADED);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            testsIDWorkable = cursor.getString(testIndex);
            testsID.add(testsIDWorkable);
            if (testsIdToPresetId.keySet().contains(testsIDWorkable)) {
                testsIdToPresetId.get(testsIDWorkable).add(cursor.getString(presetIndex));
            } else {
                testsIdToPresetId.put(testsIDWorkable, new HashSet<String>());
            }
            testsIdToUpdated.put(testsIDWorkable, cursor.getString(updatedIndex));
            testsIdToUploaded.put(testsIDWorkable, cursor.getString(uploadedIndex));
        }
        cursor.close();
        transporter = new DBPresetTestsTransporter(testsID, testsIdToPresetId, testsIdToUpdated, testsIdToUploaded);
    }

    @Test
    public void getPresetTests() {
        DBPresetTestsTransporter newTransporter = queries.getPresetTestsFromDB(instrumentation.getTargetContext());
        assertEquals(transporter.getTestID(), newTransporter.getTestID());
        assertEquals(transporter.getTestIdToPresetId(), newTransporter.getTestIdToPresetId());
        assertEquals(transporter.getTestIdToUpdated(), newTransporter.getTestIdToUpdated());
        assertEquals(transporter.getTestIdToUploaded(), newTransporter.getTestIdToUploaded());
    }

    @After
    public void tearDown() {
        database.close();
    }
}
