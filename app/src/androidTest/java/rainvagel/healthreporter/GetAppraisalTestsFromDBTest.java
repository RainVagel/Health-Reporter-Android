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

import rainvagel.healthreporter.DBClasses.DBAppraisalTestsTransporter;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 04/12/2016.
 */

public class GetAppraisalTestsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBAppraisalTestsTransporter transporter;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        ArrayList<String> appraisalID = new ArrayList<>();
        ArrayList<String> testID = new ArrayList<>();
        Map<String, String> appraisalIdToTestId = new HashMap<>();
        Map<String, String> appraisalIdToTestScores = new HashMap<>();
        Map<String, String> appraisalIdToNote = new HashMap<>();
        Map<String, String> appraisalIdToTrial1 = new HashMap<>();
        Map<String, String> appraisalIdToTrial2 = new HashMap<>();
        Map<String, String> appraisalIdToTrial3 = new HashMap<>();
        Map<String, String> appraisalIdToUpdated = new HashMap<>();
        Map<String, String> appraisalIdToUploaded = new HashMap<>();

        String[] columns = {DBContract.AppraisalTests.KEY_APPRAISAL_ID, DBContract.AppraisalTests.KEY_TEST_ID,
                DBContract.AppraisalTests.KEY_SCORE, DBContract.AppraisalTests.KEY_NOTE,
                DBContract.AppraisalTests.KEY_TRIAL_1, DBContract.AppraisalTests.KEY_TRIAL_2,
                DBContract.AppraisalTests.KEY_TRIAL_3, DBContract.AppraisalTests.KEY_UPDATED,
                DBContract.AppraisalTests.KEY_UPLOADED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.AppraisalTests.TABLE_NAME,
                columns, null, null, null, null, null);

        String appraisalIDWorkable;
        String testIDWorkable;
        int appraisalIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_APPRAISAL_ID);
        int testIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TEST_ID);
        int scoreIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_SCORE);
        int noteIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_NOTE);
        int trial1Index = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_1);
        int trial2Index = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_2);
        int trial3Index = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_3);
        int updatedIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_UPDATED);
        int uploadedIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_UPLOADED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            appraisalIDWorkable = cursor.getString(appraisalIndex);
            testIDWorkable = cursor.getString(testIndex);
            appraisalID.add(appraisalIDWorkable);
            testID.add(testIDWorkable);
            appraisalIdToTestId.put(appraisalIDWorkable, testIDWorkable);
            appraisalIdToTestScores.put(appraisalIDWorkable, cursor.getString(scoreIndex));
            appraisalIdToNote.put(appraisalIDWorkable, cursor.getString(noteIndex));
            appraisalIdToTrial1.put(appraisalIDWorkable, cursor.getString(trial1Index));
            appraisalIdToTrial2.put(appraisalIDWorkable, cursor.getString(trial2Index));
            appraisalIdToTrial3.put(appraisalIDWorkable, cursor.getString(trial3Index));
            appraisalIdToUpdated.put(appraisalIDWorkable, cursor.getString(updatedIndex));
            appraisalIdToUploaded.put(appraisalIDWorkable, cursor.getString(uploadedIndex));
        }
        cursor.close();
        transporter = new DBAppraisalTestsTransporter(appraisalID, testID, appraisalIdToTestId, appraisalIdToTestScores,
                appraisalIdToNote, appraisalIdToTrial1, appraisalIdToTrial2, appraisalIdToTrial3, appraisalIdToUpdated, appraisalIdToUploaded);
    }

    @Test
    public void getTests() {
        DBAppraisalTestsTransporter newTransporter = queries.getAppraisalTestsFromDB(instrumentation.getTargetContext());
        assertEquals(transporter.getAppraisalID(), newTransporter.getAppraisalID());
        assertEquals(transporter.getTestID(), newTransporter.getTestID());
        assertEquals(transporter.getAppraisalIdToTestId(), newTransporter.getAppraisalIdToTestId());
        assertEquals(transporter.getAppraisalIdToTestScores(), newTransporter.getAppraisalIdToTestScores());
        assertEquals(transporter.getAppraisalIdToNote(), newTransporter.getAppraisalIdToNote());
        assertEquals(transporter.getAppraisalIdToTrial1(), newTransporter.getAppraisalIdToTrial1());
        assertEquals(transporter.getAppraisalIdToTrial2(), newTransporter.getAppraisalIdToTrial2());
        assertEquals(transporter.getAppraisalIdToTrial3(), newTransporter.getAppraisalIdToTrial3());
        assertEquals(transporter.getAppraisalIdToUpdated(), newTransporter.getAppraisalIdToUpdated());
        assertEquals(transporter.getAppraisalIdToUploaded(), newTransporter.getAppraisalIdToUploaded());
    }

    @After
    public void tearDown() {
        database.close();

    }
}
