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

public class EditClientInDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;

    private static final String GROUPNAME = "Test123";
    private static final String UPDATED   = "2013-12-31";

    private static final String FIRSTNAME0 = "Test1";
    private static final String LASTNAME0  = "Client1";
    private static final String EMAIL0     = "test1client1@gmail.com";
    private static final int    GENDER0    = 0;    //0 - female, 1 - male
    private static final String YEAR0      = "1999";
    private static final String MONTH0     = "1";
    private static final String DAY0       = "1";
    private static final String UPDATED0   = "2016-12-31";
    private static final String BIRTHDAY0  = YEAR0 + "-" + MONTH0 + "-" + DAY0;

    private static final String FIRSTNAME1 = "Test2";
    private static final String LASTNAME1  = "Client2";
    private static final String EMAIL1     = "test2client2@gmail.com";
    private static final String GENDER1    = "1";    //0 - female, 1 - male
    private static final String YEAR1      = "1888";
    private static final String MONTH1     = "12";
    private static final String DAY1       = "31";
    private static final String UPDATED1   = "2016-01-31";
    private static final String BIRTHDAY1  = YEAR1 + "-" + MONTH1 + "-" + DAY1;

    private String groupUuid;
    private String clientUuid;

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

        queries.insertClientToDB(instrumentation.getTargetContext(), groupUuid, FIRSTNAME0, LASTNAME0, EMAIL0, GENDER0, YEAR0, MONTH0, DAY0, UPDATED0);
        String[] clientColumns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME};
        cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int idIdx1 = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int nameIdx1 = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx1).equals(FIRSTNAME0)) {
                clientUuid = cursor.getString(idIdx1);
            }
        }
        cursor.close();
    }

    @Test
    public void editClient() {
        queries.editClientInDB(instrumentation.getTargetContext(), clientUuid, FIRSTNAME1, LASTNAME1,
                BIRTHDAY1, EMAIL1, GENDER1, groupUuid, UPDATED1);

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

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(idIdx).equals(clientUuid)) {
                assertEquals(groupUuid, cursor.getString(groupIdx));
                assertEquals(FIRSTNAME1, cursor.getString(firstNameIdx));
                assertEquals(LASTNAME1, cursor.getString(lastNameIdx));
                assertEquals(EMAIL1, cursor.getString(emailIdx));
                assertEquals(GENDER1, cursor.getString(genderIdx));
                assertEquals(BIRTHDAY1, cursor.getString(birthdayIdx));
                assertEquals(UPDATED1, cursor.getString(updateIdx));
            }
        }
        cursor.close();


    }

    @After
    public void tearDown() {
        //queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.Clients.TABLE_NAME, DBContract.Clients.KEY_ID, clientUuid);
        //queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.Groups.TABLE_NAME, DBContract.Groups.KEY_ID, groupUuid);
        database.close();
    }
}
