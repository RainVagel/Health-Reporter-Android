package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Cornelia on 29/11/2016.
 */

public class InsertGroupToDBTest {
    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();
    }

    @Test
    public void addingGroup() {
        queries.insertGroupToDB(instrumentation.getTargetContext(), "Testgrupp1", "2017-01-01");
        String[] groupColumns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, groupColumns, null,null,null,null,null);
        int nameIdx = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);
        int dateIdx = cursor.getColumnIndex(DBContract.Groups.KEY_UPDATED);
        int uploadIdx = cursor.getColumnIndex(DBContract.Groups.KEY_UPLOADED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx).equals("Testgrupp1")) {
                assertEquals("2017-01-01", cursor.getString(dateIdx));
                assertEquals("0000-00-00", cursor.getString(uploadIdx));
            }
        }
        cursor.close();
    }

    @After
    public void tearDown() {
        database.close();
    }
}
