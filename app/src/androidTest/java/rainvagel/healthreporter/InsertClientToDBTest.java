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

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();
    }

    // gender 0 - female, 1 - male
    @Test
    public void insertingClient() {
        queries.insertClientToDB(instrumentation.getTargetContext(), "University of Tartu", "Mary",
                "Jane", "maryjane@gmail.com", 0, "1986", "8", "16", "2016-08-02");

        String[] clientColumns = {DBContract.Clients.KEY_GROUP_ID, DBContract.Clients.KEY_FIRSTNAME,
                DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_EMAIL, DBContract.Clients.KEY_BIRTHDATE,
        DBContract.Clients.KEY_UPDATED};

        Cursor cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, clientColumns, null,null,null,null,null);
        int groupIdx = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int firstNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int emailIdx = cursor.getColumnIndex(DBContract.Clients.KEY_EMAIL);
        int birthdayIdx = cursor.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);
        int updateIdx = cursor.getColumnIndex(DBContract.Clients.KEY_UPDATED);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            //TODO
        }
        cursor.close();
    }

    @After
    public void tearDown() {
        database.close();
    }
}
