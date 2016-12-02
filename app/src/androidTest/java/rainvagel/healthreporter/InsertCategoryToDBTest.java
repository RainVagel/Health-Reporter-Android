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

/**
 * Created by Cornelia on 02/12/2016.
 */

public class InsertCategoryToDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;

    private static final String PARENTID = "null";
    private static final String NAME     = "TestCategory";
    private static final int    POSITION = 1;
    private static final String DATE     = "2017-01-01";

    private String uuid;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();
    }

    @Test
    public void insertingCategory() {
        queries.insertCategoryToDB(instrumentation.getTargetContext(), PARENTID, NAME, POSITION, DATE);

        String[] catColumns = {DBContract.TestCategories.KEY_ID, DBContract.TestCategories.KEY_PARENT_ID, DBContract.TestCategories.KEY_NAME,
                DBContract.TestCategories.KEY_POSITION, DBContract.TestCategories.KEY_UPDATED};

        Cursor cursor = database.getReadableDatabase().query(DBContract.TestCategories.TABLE_NAME, catColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.TestCategories.KEY_ID);
        int parentIdx = cursor.getColumnIndex(DBContract.TestCategories.KEY_PARENT_ID);
        int nameIdx = cursor.getColumnIndex(DBContract.TestCategories.KEY_NAME);
        int posIdx = cursor.getColumnIndex(DBContract.TestCategories.KEY_POSITION);
        int updateIdx = cursor.getColumnIndex(DBContract.TestCategories.KEY_UPDATED);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx).equals(NAME)) {
                uuid = cursor.getString(idIdx);
                assertEquals(PARENTID, cursor.getString(parentIdx));
                assertEquals(POSITION, cursor.getInt(posIdx));
                assertEquals(DATE, cursor.getString(updateIdx));
            }
        }
        cursor.close();

    }

    @After
    public void tearDown() {
        queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.TestCategories.TABLE_NAME, DBContract.TestCategories.KEY_ID, uuid);
        database.close();

    }
}
