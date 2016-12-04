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
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTestsTransporter;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 04/12/2016.
 */

public class GetTestsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBTestsTransporter transporter;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        ArrayList<String> testID = new ArrayList<>();
        Map<String, String> testIdToCategoryId = new HashMap<>();
        Map<String, String> testIdToName = new HashMap<>();
        Map<String, String> testIdToDescription = new HashMap<>();
        Map<String, String> testIdToUnits = new HashMap<>();
        Map<String, String> testIdToDecimals = new HashMap<>();
        Map<String, String> testIdToWeight = new HashMap<>();
        Map<String, String> testIdToFormulaF = new HashMap<>();
        Map<String, String> testIdToFormulaM = new HashMap<>();
        Map<String, String> testIdToPosition = new HashMap<>();
        Map<String, String> testIdToUpdated = new HashMap<>();
        Map<String, String> testIdToUploaded = new HashMap<>();

        String[] columns = {DBContract.Tests.KEY_ID, DBContract.Tests.KEY_CATEGORY_ID, DBContract.Tests.KEY_NAME,
                DBContract.Tests.KEY_DESCRIPTION, DBContract.Tests.KEY_UNITS, DBContract.Tests.KEY_DECIMALS,
                DBContract.Tests.KEY_WEIGHT, DBContract.Tests.KEY_FORMULA_F, DBContract.Tests.KEY_FORMULA_M,
                DBContract.Tests.KEY_POSITION, DBContract.Tests.KEY_UPDATED, DBContract.Tests.KEY_UPLOADED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Tests.TABLE_NAME, columns,
                null, null, null, null, null);

        String testIDWorkable;
        int testIndex = cursor.getColumnIndex(DBContract.Tests.KEY_ID);
        int categoryIndex = cursor.getColumnIndex(DBContract.Tests.KEY_CATEGORY_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.Tests.KEY_NAME);
        int descriptionIndex = cursor.getColumnIndex(DBContract.Tests.KEY_DESCRIPTION);
        int unitsIndex = cursor.getColumnIndex(DBContract.Tests.KEY_UNITS);
        int decimalsIndex = cursor.getColumnIndex(DBContract.Tests.KEY_DECIMALS);
        int weightIndex = cursor.getColumnIndex(DBContract.Tests.KEY_WEIGHT);
        int formulaFIndex = cursor.getColumnIndex(DBContract.Tests.KEY_FORMULA_F);
        int formulaMIndex = cursor.getColumnIndex(DBContract.Tests.KEY_FORMULA_M);
        int positionIndex = cursor.getColumnIndex(DBContract.Tests.KEY_POSITION);
        int updatedIndex = cursor.getColumnIndex(DBContract.Tests.KEY_UPDATED);
        int uploadedIndex = cursor.getColumnIndex(DBContract.Tests.KEY_UPLOADED);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            testIDWorkable = cursor.getString(testIndex);
            testID.add(testIDWorkable);
            testIdToCategoryId.put(testIDWorkable, cursor.getString(categoryIndex));
            testIdToName.put(testIDWorkable, cursor.getString(nameIndex));
            testIdToDescription.put(testIDWorkable, cursor.getString(descriptionIndex));
            testIdToUnits.put(testIDWorkable, cursor.getString(unitsIndex));
            testIdToDecimals.put(testIDWorkable, cursor.getString(decimalsIndex));
            testIdToWeight.put(testIDWorkable, cursor.getString(weightIndex));
            testIdToFormulaF.put(testIDWorkable, cursor.getString(formulaFIndex));
            testIdToFormulaM.put(testIDWorkable, cursor.getString(formulaMIndex));
            testIdToPosition.put(testIDWorkable, cursor.getString(positionIndex));
            testIdToUpdated.put(testIDWorkable, cursor.getString(updatedIndex));
            testIdToUploaded.put(testIDWorkable, cursor.getString(uploadedIndex));
        }
        cursor.close();
        transporter = new DBTestsTransporter(testID, testIdToCategoryId, testIdToName, testIdToDescription,
                testIdToUnits, testIdToDecimals, testIdToWeight, testIdToFormulaF, testIdToFormulaM,
                testIdToPosition, testIdToUpdated, testIdToUploaded);
    }

    @Test
    public void getTests() {
        DBTestsTransporter newTransporter = queries.getTestsFromDB(instrumentation.getTargetContext());
        assertEquals(transporter.getTestID(), newTransporter.getTestID());
        assertEquals(transporter.getTestIdToCategoryId(), newTransporter.getTestIdToCategoryId());
        assertEquals(transporter.getTestIdToName(), newTransporter.getTestIdToName());
        assertEquals(transporter.getTestIdToDescription(), newTransporter.getTestIdToDescription());
        assertEquals(transporter.getTestIdToUnits(), newTransporter.getTestIdToUnits());
        assertEquals(transporter.getTestIdToDecimals(), newTransporter.getTestIdToDecimals());
        assertEquals(transporter.getTestIdToWeight(), newTransporter.getTestIdToWeight());
        assertEquals(transporter.getTestIdToFormulaF(), newTransporter.getTestIdToFormulaF());
        assertEquals(transporter.getTestIdToFormulaM(), newTransporter.getTestIdToFormulaM());
        assertEquals(transporter.getTestIdToPosition(), newTransporter.getTestIdToPosition());
        assertEquals(transporter.getTestIdToUpdated(), newTransporter.getTestIdToUpdated());
        assertEquals(transporter.getTestIdToUploaded(), newTransporter.getTestIdToUploaded());

    }

    @After
    public void tearDown() {
        database.close();

    }
}
