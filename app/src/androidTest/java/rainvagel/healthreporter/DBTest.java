package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;

import static junit.framework.Assert.assertEquals;


/**
 * Created by Cornelia on 05/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class DBTest {

    private Instrumentation instrumentation;
    private DBHelper database;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
    }



    @Test
    public void checkAppraisers() {
        String[] appraiserColumns = {DBContract.Appraisers.KEY_ID, DBContract.Appraisers.KEY_NAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Appraisers.TABLE_NAME, appraiserColumns, null,null,null,null,null);
        int nameIdx = cursor.getColumnIndex(DBContract.Appraisers.KEY_NAME);
        int idIdx = cursor.getColumnIndex(DBContract.Appraisers.KEY_ID);

        cursor.moveToFirst();
        assertEquals("Karl Lubja", cursor.getString(nameIdx));
        assertEquals(133742069, Integer.parseInt(cursor.getString(idIdx)));
    }

    @Test
    public void checkAppraisals() {
        String[] appraisalColumns = {DBContract.Appraisals.KEY_ID, DBContract.Appraisals.KEY_APPRAISER_ID};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Appraisals.TABLE_NAME, appraisalColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Appraisals.KEY_ID);
        int appIdIdx = cursor.getColumnIndex(DBContract.Appraisals.KEY_APPRAISER_ID);

        cursor.moveToFirst();
        assertEquals(48, Integer.parseInt(cursor.getString(idIdx)));
        assertEquals(133742069, Integer.parseInt(cursor.getString(appIdIdx)));
    }

    @Test
    public void checkRatingLabels() {
        String[] ratingColumns = {DBContract.RatingLabels.KEY_ID, DBContract.RatingLabels.KEY_INTERPRETATION};
        Cursor cursor = database.getReadableDatabase().query(DBContract.RatingLabels.TABLE_NAME, ratingColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.RatingLabels.KEY_ID);
        int interpIdx = cursor.getColumnIndex(DBContract.RatingLabels.KEY_INTERPRETATION);

        cursor.moveToFirst();
        assertEquals(448, Integer.parseInt(cursor.getString(idIdx)));
        assertEquals("Something in here", cursor.getString(interpIdx));
        cursor.moveToNext();
        assertEquals(449, Integer.parseInt(cursor.getString(idIdx)));
        assertEquals("Something in here", cursor.getString(interpIdx));
    }

    @Test
    public void checkTests() {
        String[] testColumns = {DBContract.Tests.KEY_NAME, DBContract.Tests.KEY_DESCRIPTION,
                DBContract.Tests.KEY_UNITS, DBContract.Tests.KEY_DECIMALS, DBContract.Tests.KEY_WEIGHT,
                DBContract.Tests.KEY_FORMULA_F, DBContract.Tests.KEY_FORMULA_M, DBContract.Tests.KEY_POSITION};

        Cursor cursor = database.getReadableDatabase().query(DBContract.Tests.TABLE_NAME, testColumns, null,null,null,null,null);
        int nameIdx = cursor.getColumnIndex(DBContract.Tests.KEY_NAME);
        int descIdx = cursor.getColumnIndex(DBContract.Tests.KEY_DESCRIPTION);
        int unitIdx = cursor.getColumnIndex(DBContract.Tests.KEY_UNITS);
        int decIdx = cursor.getColumnIndex(DBContract.Tests.KEY_DECIMALS);
        int weightIdx = cursor.getColumnIndex(DBContract.Tests.KEY_WEIGHT);
        int forFIdx = cursor.getColumnIndex(DBContract.Tests.KEY_FORMULA_F);
        int forMIdx = cursor.getColumnIndex(DBContract.Tests.KEY_FORMULA_M);
        int posMIdx = cursor.getColumnIndex(DBContract.Tests.KEY_POSITION);

        cursor.moveToFirst();
        assertEquals("We Are The Champions", cursor.getString(nameIdx));
        assertEquals("Something", cursor.getString(descIdx));
        assertEquals("Unitz", cursor.getString(unitIdx));
        assertEquals(2, Integer.parseInt(cursor.getString(decIdx)));
        assertEquals(3, Integer.parseInt(cursor.getString(weightIdx)));
        assertEquals("2", cursor.getString(forFIdx));
        assertEquals("3", cursor.getString(forMIdx));
        assertEquals(0, Integer.parseInt(cursor.getString(posMIdx)));
    }

    @Test
    public void checkAppraisalTests() {
        String[] testColumns = {DBContract.AppraisalTests.KEY_SCORE, DBContract.AppraisalTests.KEY_NOTE,
                DBContract.AppraisalTests.KEY_TRIAL_1, DBContract.AppraisalTests.KEY_TRIAL_2, DBContract.AppraisalTests.KEY_TRIAL_3};

        Cursor cursor = database.getReadableDatabase().query(DBContract.AppraisalTests.TABLE_NAME, testColumns, null,null,null,null,null);
        int scoreIdx = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_SCORE);
        int noteIdx = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_NOTE);
        int t1Idx = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_1);
        int t2Idx = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_2);
        int t3Idx = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_3);

        cursor.moveToFirst();
        assertEquals(20, Integer.parseInt(cursor.getString(scoreIdx)));
        assertEquals("Hello", cursor.getString(noteIdx));
        assertEquals(2, Integer.parseInt(cursor.getString(t1Idx)));
        assertEquals(3, Integer.parseInt(cursor.getString(t2Idx)));
        assertEquals(20, Integer.parseInt(cursor.getString(t3Idx)));
    }

    @Test
    public void checkPresets() {
        String[] presetsColumns = {DBContract.Presets.KEY_ID, DBContract.Presets.KEY_NAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Presets.TABLE_NAME, presetsColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Presets.KEY_ID);
        int nameIdx = cursor.getColumnIndex(DBContract.Presets.KEY_NAME);

        cursor.moveToFirst();
        assertEquals(512, Integer.parseInt(cursor.getString(idIdx)));
        assertEquals("Basic Physical", cursor.getString(nameIdx));
    }
}
