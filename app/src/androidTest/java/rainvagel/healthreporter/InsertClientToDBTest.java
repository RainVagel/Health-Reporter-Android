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
 * Created by Cornelia on 29/11/2016.
 */

public class InsertClientToDBTest {
    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;

    private static final String GROUP     = "University of Tartu";
    private static final String FIRSTNAME = "Test";
    private static final String LASTNAME  = "Client";
    private static final String EMAIL     = "testclient@gmail.com";
    private static final int    GENDER    = 0;    //0 - female, 1 - male
    private static final String YEAR      = "1999";
    private static final String MONTH     = "1";
    private static final String DAY       = "1";
    private static final String UPDATED   = "2016-12-31";
    private static final String UPLOADED  = "0000-00-00";
    private static final String BIRTHDAY  = YEAR + "-" + MONTH + "-" + DAY;

    private String uuid;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();
    }

    @Test
    public void insertingClient() {
        queries.insertClientToDB(instrumentation.getTargetContext(), GROUP, FIRSTNAME,
                LASTNAME, EMAIL, GENDER, YEAR, MONTH, DAY, UPDATED);

        String[] clientColumns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_GROUP_ID, DBContract.Clients.KEY_FIRSTNAME,
                DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_EMAIL, DBContract.Clients.KEY_GENDER,
                DBContract.Clients.KEY_BIRTHDATE, DBContract.Clients.KEY_UPDATED, DBContract.Clients.KEY_UPLOADED};

        Cursor cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int groupIdx = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int firstNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int emailIdx = cursor.getColumnIndex(DBContract.Clients.KEY_EMAIL);
        int genderIdx = cursor.getColumnIndex(DBContract.Clients.KEY_GENDER);
        int birthdayIdx = cursor.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);
        int updateIdx = cursor.getColumnIndex(DBContract.Clients.KEY_UPDATED);
        int uploadIdx = cursor.getColumnIndex(DBContract.Clients.KEY_UPLOADED);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(groupIdx).equals(GROUP)) {
                if (cursor.getString(firstNameIdx).equals(FIRSTNAME)) {
                    uuid = cursor.getString(idIdx);
                    assertEquals(LASTNAME, cursor.getString(lastNameIdx));
                    assertEquals(EMAIL, cursor.getString(emailIdx));
                    assertEquals(GENDER, cursor.getInt(genderIdx));
                    assertEquals(BIRTHDAY, cursor.getString(birthdayIdx));
                    assertEquals(UPDATED, cursor.getString(updateIdx));
                    assertEquals(UPLOADED, cursor.getString(uploadIdx));
                }
            }
        }
        cursor.close();
    }

    @After
    public void tearDown() {
        queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.Clients.TABLE_NAME, DBContract.Clients.KEY_ID,uuid);
        database.close();
    }
}
