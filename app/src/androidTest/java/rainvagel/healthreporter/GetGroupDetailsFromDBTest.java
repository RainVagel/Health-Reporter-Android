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

public class GetGroupDetailsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;

    private static final String GROUPNAME = "Testgrupp2";
    private static final String UPDATED   = "2017-03-15";
    private static final String UPLOADED  = "0000-00-00";

    private String groupUuid;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        queries.insertGroupToDB(instrumentation.getTargetContext(), GROUPNAME, UPDATED);
        String[] groupColumns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, groupColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIdx = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx).equals(GROUPNAME)) {
                groupUuid = cursor.getString(idIdx);
            }
        }
        cursor.close();
    }

    @Test
    public void getDetails() {
        ArrayList<String> details = queries.getGroupDetailsFromDB(instrumentation.getTargetContext(), groupUuid);
        assertEquals(GROUPNAME, details.get(0));
        assertEquals(UPDATED, details.get(1));
    }

    @After
    public void tearDown() throws Exception {
        database.close();

    }
}
