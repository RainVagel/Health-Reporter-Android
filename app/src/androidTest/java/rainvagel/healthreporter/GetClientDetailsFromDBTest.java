package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 02/12/2016.
 */

public class GetClientDetailsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;

    private static final String GROUPNAME = "Vilde";

    private static final String FIRSTNAME = "Test2";
    private static final String LASTNAME  = "Client";
    private static final String EMAIL     = "testclient@gmail.com";
    private static final int    GENDER    = 0;    //0 - female, 1 - male
    private static final String YEAR      = "1960";
    private static final String MONTH     = "1";
    private static final String DAY       = "1";
    private static final String UPDATED   = "2016-12-31";
    private static final String BIRTHDAY  = YEAR + "-" + MONTH + "-" + DAY;

    private String groupUuid;
    private String clientUuid;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        String[] groupColumns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, groupColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIdx = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx).equals(GROUPNAME)) {
                groupUuid = cursor.getString(idIdx);
            }
        }

        queries.insertClientToDB(instrumentation.getTargetContext(), groupUuid, FIRSTNAME, LASTNAME, EMAIL, GENDER, YEAR, MONTH, DAY, UPDATED);
        String[] clientColumns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME};
        cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int idIdx1 = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int nameIdx1 = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx1).equals(FIRSTNAME)) {
                clientUuid = cursor.getString(idIdx1);
            }
        }
        cursor.close();
    }

    @Test
    public void getDetails() {
        ArrayList<String> details = queries.getClientDetailsFromDB(instrumentation.getTargetContext(), clientUuid);

        assertEquals(FIRSTNAME, details.get(0));
        assertEquals(LASTNAME, details.get(1));
        assertEquals(BIRTHDAY, details.get(2));
        assertEquals(GENDER, Integer.parseInt(details.get(3)));
        assertEquals(groupUuid, details.get(4));
        assertEquals(EMAIL, details.get(5));
        assertEquals(UPDATED, details.get(6));

    }


    @After
    public void tearDown() {
        //queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.Clients.TABLE_NAME, DBContract.Clients.KEY_ID, clientUuid);
        database.close();

    }
}
