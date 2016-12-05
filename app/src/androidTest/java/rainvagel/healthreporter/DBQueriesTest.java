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
 * Created by Cornelia on 04/12/2016.
 */

public class DBQueriesTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;

    private static final String GROUPNAME0 = "TestGroup001";
    private static final String UPDATED0   = "2016-12-04";
    private String groupUuid0;

    private static final String FIRSTNAME2 = "FirstName2";
    private static final String LASTNAME2  = "LastName2";
    private static final String EMAIL2     = "first2last2@gmail.com";
    private static final int    GENDER2    =  1;    //0 - female, 1 - male
    private static final String YEAR2      = "1942";
    private static final String MONTH2     = "02";
    private static final String DAY2       = "01";
    private static final String UPDATED3   = "2016-12-10";
    private static final String BIRTHDAY2  = YEAR2 + "-" + MONTH2 + "-" + DAY2;
    private String clientUuid2;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        queries.insertGroupToDB(instrumentation.getTargetContext(), GROUPNAME0, UPDATED0);
        String[] groupColumns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME, DBContract.Groups.KEY_UPDATED, DBContract.Groups.KEY_UPLOADED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, groupColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIdx = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx).equals(GROUPNAME0)) {
                groupUuid0 = cursor.getString(idIdx);
            }
        }

        queries.insertClientToDB(instrumentation.getTargetContext(), groupUuid0, FIRSTNAME2, LASTNAME2, EMAIL2, GENDER2, YEAR2, MONTH2, DAY2, UPDATED3);
        String[] clientColumns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_GROUP_ID, DBContract.Clients.KEY_FIRSTNAME,
                DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_EMAIL, DBContract.Clients.KEY_GENDER,
                DBContract.Clients.KEY_BIRTHDATE, DBContract.Clients.KEY_UPDATED, DBContract.Clients.KEY_UPLOADED};

        cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int idIdx1 = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int groupIdx = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int firstNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(groupIdx).equals(groupUuid0)) {
                if (cursor.getString(firstNameIdx).equals(FIRSTNAME2)) {
                    clientUuid2 = cursor.getString(idIdx1);
                }
            }
        }
        cursor.close();

    }

    private static final String FIRSTNAME = "FirstName";
    private static final String LASTNAME  = "LastName";
    private static final String EMAIL     = "firstlastt@gmail.com";
    private static final int    GENDER    = 0;    //0 - female, 1 - male
    private static final String YEAR      = "1940";
    private static final String MONTH     = "01";
    private static final String DAY       = "01";
    private static final String UPDATED   = "2016-12-04";
    private static final String UPLOADED  = "0000-00-00";
    private static final String BIRTHDAY  = YEAR + "-" + MONTH + "-" + DAY;

    private String clientUuid;

    @Test
    public void insertClientTest() {
        queries.insertClientToDB(instrumentation.getTargetContext(), groupUuid0, FIRSTNAME,
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
            if (cursor.getString(groupIdx).equals(groupUuid0)) {
                if (cursor.getString(firstNameIdx).equals(FIRSTNAME)) {
                    clientUuid = cursor.getString(idIdx);
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

    private static final String GROUPNAME1 = "TestGroup002";
    private static final String UPDATED1   = "2016-12-05";
    private static final String UPlOADED1  = "0000-00-00";
    private String groupUuid1;

    @Test
    public void insertGroupTest() {
        queries.insertGroupToDB(instrumentation.getTargetContext(), GROUPNAME1, UPDATED1);
        String[] groupColumns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME, DBContract.Groups.KEY_UPDATED, DBContract.Groups.KEY_UPLOADED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, groupColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIdx = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);
        int dateIdx = cursor.getColumnIndex(DBContract.Groups.KEY_UPDATED);
        int uploadIdx = cursor.getColumnIndex(DBContract.Groups.KEY_UPLOADED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx).equals(GROUPNAME1)) {
                groupUuid1 = cursor.getString(idIdx);
                assertEquals(UPDATED1, cursor.getString(dateIdx));
                assertEquals(UPlOADED1, cursor.getString(uploadIdx));
            }
        }
        cursor.close();
    }

    private static final String PARENTID = "null";
    private static final String NAME     = "TestCategory001";
    private static final int    POSITION = 1;
    private static final String DATE     = "2017-12-07";
    private String catUuid;

    @Test
    public void insertCategory() {
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
                catUuid = cursor.getString(idIdx);
                assertEquals(PARENTID, cursor.getString(parentIdx));
                assertEquals(POSITION, cursor.getInt(posIdx));
                assertEquals(DATE, cursor.getString(updateIdx));
            }
        }
        cursor.close();

    }

    private static final String FIRSTNAME1 = "FirstName1";
    private static final String LASTNAME1  = "LastName1";
    private static final String EMAIL1     = "first1last1@gmail.com";
    private static final String GENDER1    = "1";    //0 - female, 1 - male
    private static final String YEAR1      = "1941";
    private static final String MONTH1     = "02";
    private static final String DAY1       = "01";
    private static final String UPDATED2   = "2016-12-10";
    private static final String BIRTHDAY1  = YEAR1 + "-" + MONTH1 + "-" + DAY1;

    @Test
    public void editClient() {
        queries.editClientInDB(instrumentation.getTargetContext(), clientUuid, FIRSTNAME1, LASTNAME1,
                BIRTHDAY1, EMAIL1, GENDER1, groupUuid0, UPDATED2);

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
                assertEquals(groupUuid0, cursor.getString(groupIdx));
                assertEquals(FIRSTNAME1, cursor.getString(firstNameIdx));
                assertEquals(LASTNAME1, cursor.getString(lastNameIdx));
                assertEquals(EMAIL1, cursor.getString(emailIdx));
                assertEquals(GENDER1, cursor.getString(genderIdx));
                assertEquals(BIRTHDAY1, cursor.getString(birthdayIdx));
                assertEquals(UPDATED2, cursor.getString(updateIdx));
            }
        }
        cursor.close();
    }

    @Test
    public void getClientDetails() {
        ArrayList<String> details = queries.getClientDetailsFromDB(instrumentation.getTargetContext(), clientUuid2);

        assertEquals(FIRSTNAME2, details.get(0));
        assertEquals(LASTNAME2, details.get(1));
        assertEquals(BIRTHDAY2, details.get(2));
        assertEquals(GENDER2, Integer.parseInt(details.get(3)));
        assertEquals(groupUuid0, details.get(4));
        assertEquals(EMAIL2, details.get(5));
        assertEquals(UPDATED2, details.get(6));

    }

    @Test
    public void getGroupDetails() {
        ArrayList<String> details = queries.getGroupDetailsFromDB(instrumentation.getTargetContext(), groupUuid0);
        assertEquals(GROUPNAME0, details.get(0));
        assertEquals(UPDATED, details.get(1));
    }

    @After
    public void tearDown() throws Exception {
        queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.Groups.TABLE_NAME, DBContract.Groups.KEY_ID, groupUuid0);
        queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.Groups.TABLE_NAME, DBContract.Groups.KEY_ID, groupUuid1);
        queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.Clients.TABLE_NAME, DBContract.Clients.KEY_ID, clientUuid);
        queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.Clients.TABLE_NAME, DBContract.Clients.KEY_ID, clientUuid2);
        queries.deleteEntryFromDB(instrumentation.getTargetContext(), DBContract.TestCategories.TABLE_NAME, DBContract.TestCategories.KEY_ID, catUuid);
        database.close();

    }
}
