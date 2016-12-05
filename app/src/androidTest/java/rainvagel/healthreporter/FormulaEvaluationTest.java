package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rainvagel.healthreporter.DBClasses.DBAppraisalTestsTransporter;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTestsTransporter;
import rainvagel.healthreporter.TestClasses.FormulaEvaluation;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 05/12/2016.
 */

public class FormulaEvaluationTest {

    private FormulaEvaluation formulaEvaluation;
    private Instrumentation instrumentation;
    private DBQueries queries;
    private DBHelper database;

    private DBAppraisalTestsTransporter transporter;
    private DBTestsTransporter testsTransporter;

    private static final String FIRSTNAME = "Test";
    private static final String LASTNAME = "Appraiser";

    private String appraiserID;
    private String clientUuid;
    private String testID;
    private String decimals;
    private String units;
    private String appraisalID;

    private static final int    SCORE       = 50;
    private static final String NOTE        = "testing";
    private static final int    TRIAL1      = 10;
    private static final int    TRIAL2      = 12;
    private static final int    TRIAL3      = 9;
    private static final String DATE        = "2016-12-05";
    private static final String UPDATED     = "2016-12-31";
    private static final String UPLOADED    = "2017-01-01";
    private static final String expectedResultF = "10.34";
    private static final String expectedResultM = "129.0";

    private String result;
    private String gender;


    @Before
    public void setUp() throws Exception {
        formulaEvaluation = new FormulaEvaluation();
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        queries.insertAppraiserToDB(instrumentation.getTargetContext(), FIRSTNAME, LASTNAME);
        String[] columns = {DBContract.Appraisers.KEY_ID, DBContract.Appraisers.KEY_NAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Appraisers.TABLE_NAME, columns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Appraisers.KEY_ID);
        int nameIdx = cursor.getColumnIndex(DBContract.Appraisers.KEY_NAME);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx).equals(FIRSTNAME + " " + LASTNAME)) {
                appraiserID = cursor.getString(idIdx);
            }
        }

        String[] clientColumns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_GENDER};
        cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int idIdx1 = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int genIdx = cursor.getColumnIndex(DBContract.Clients.KEY_GENDER);
        cursor.moveToFirst();
        clientUuid = cursor.getString(idIdx1);
        gender = cursor.getString(genIdx);

        String[] testColumns = {DBContract.Tests.KEY_ID, DBContract.Tests.KEY_DECIMALS, DBContract.Tests.KEY_UNITS};
        cursor = database.getReadableDatabase().query(DBContract.Tests.TABLE_NAME, testColumns, null,null,null,null,null);
        int idIdx2 = cursor.getColumnIndex(DBContract.Tests.KEY_ID);
        int decIdx = cursor.getColumnIndex(DBContract.Tests.KEY_DECIMALS);
        int unitIdx = cursor.getColumnIndex(DBContract.Tests.KEY_UNITS);
        cursor.moveToFirst();
        testID = cursor.getString(idIdx2);
        decimals = cursor.getString(decIdx);
        units = cursor.getString(unitIdx);
        cursor.close();


        queries.insertAppraisalTestAndAppraisalToDB(instrumentation.getTargetContext(), appraiserID, clientUuid, DATE, UPDATED,
                UPLOADED, testID, SCORE, NOTE, TRIAL1, TRIAL2, TRIAL3);

        String[] appColumns = {DBContract.Appraisals.KEY_ID, DBContract.Appraisals.KEY_APPRAISER_ID};
        cursor = database.getReadableDatabase().query(DBContract.Appraisals.TABLE_NAME, appColumns, null,null,null,null,null);
        int idIdx3 = cursor.getColumnIndex(DBContract.Appraisals.KEY_ID);
        int appIdIdx = cursor.getColumnIndex(DBContract.Appraisals.KEY_APPRAISER_ID);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(appIdIdx).equals(appraiserID)) {
                appraisalID = cursor.getString(idIdx3);
            }
        }
    }

    @Test
    public void evaluate() throws Exception {
        result = formulaEvaluation.evaluate(instrumentation.getTargetContext(), appraisalID, testID);
        if (gender.equals("0")) {
            assertEquals(expectedResultF, result);
        }
        else {
            assertEquals(expectedResultM, result);
        }

    }

    @After
    public void tearDown() {
        database.close();

    }
}
