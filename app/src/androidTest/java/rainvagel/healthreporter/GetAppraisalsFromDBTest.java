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

import rainvagel.healthreporter.DBClasses.DBAppraisalsTransporter;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 04/12/2016.
 */

public class GetAppraisalsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBAppraisalsTransporter transporter;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        ArrayList<String> appraisalID = new ArrayList<>();
        Map<String, String> appraisalIdToAppraiserId = new HashMap<>();
        Map<String, String> appraisalIdToClientId = new HashMap<>();
        Map<String, String> appraisalIdToAppraisalDate = new HashMap<>();
        Map<String, String> appraisalIdToUpdated = new HashMap<>();
        Map<String, String> appraisalIdToUploaded = new HashMap<>();

        String[] columns = {DBContract.Appraisals.KEY_ID, DBContract.Appraisals.KEY_APPRAISER_ID,
                DBContract.Appraisals.KEY_CLIENT_ID, DBContract.Appraisals.KEY_DATE,
                DBContract.Appraisals.KEY_UPDATED, DBContract.Appraisals.KEY_UPLOADED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Appraisals.TABLE_NAME,
                columns, null, null, null, null, null);

        String appraisalIDWorkable;
        int appraisalIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_ID);
        int appraiserIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_APPRAISER_ID);
        int clientIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_CLIENT_ID);
        int dateIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_DATE);
        int updatedIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_UPDATED);
        int uploadedIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_UPLOADED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            appraisalIDWorkable = cursor.getString(appraisalIndex);
            appraisalID.add(appraisalIDWorkable);
            appraisalIdToAppraiserId.put(appraisalIDWorkable, cursor.getString(appraiserIndex));
            appraisalIdToClientId.put(appraisalIDWorkable, cursor.getString(clientIndex));
            appraisalIdToAppraisalDate.put(appraisalIDWorkable, cursor.getString(dateIndex));
            appraisalIdToUpdated.put(appraisalIDWorkable, cursor.getString(updatedIndex));
            appraisalIdToUploaded.put(appraisalIDWorkable, cursor.getString(uploadedIndex));
        }
        cursor.close();
        transporter = new DBAppraisalsTransporter(appraisalID, appraisalIdToAppraiserId, appraisalIdToUpdated,
                appraisalIdToClientId, appraisalIdToAppraisalDate, appraisalIdToUploaded);
    }

    @Test
    public void getAppraisals() {
        DBAppraisalsTransporter newTransporter = queries.getAppraisalsFromDB(instrumentation.getTargetContext());
        assertEquals(transporter.getAppraisalID(), newTransporter.getAppraisalID());
        assertEquals(transporter.getAppraisalIdToAppraiserId(), newTransporter.getAppraisalIdToAppraiserId());
        assertEquals(transporter.getAppraisalIdToUpdated(), newTransporter.getAppraisalIdToUpdated());
        assertEquals(transporter.getAppraisalIdToClientId(), newTransporter.getAppraisalIdToClientId());
        assertEquals(transporter.getAppraisalIdToAppraisalDate(), newTransporter.getAppraisalIdToAppraisalDate());
        assertEquals(transporter.getAppraisalIdToUploaded(), newTransporter.getAppraisalIdToUploaded());
    }

    @After
    public void tearDown() {
        database.close();

    }
}
