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

import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTransporter;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 02/12/2016.
 */

public class GetGroupsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBTransporter transporter;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        ArrayList<String> groupID = new ArrayList<>();
        Map<String, String> groups = new HashMap<>();
        Map<String, String> groupsReversed = new HashMap<>();
        ArrayList<String> groupNames = new ArrayList<>();

        String[] columns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME,
                columns, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String groupId = cursor.getString(idIndex);
            groupID.add(groupId);
            groups.put(groupId, cursor.getString(nameIndex));
            groupsReversed.put(cursor.getString(nameIndex), groupId);
            groupNames.add(cursor.getString(nameIndex));
        }
        cursor.close();

        transporter = new DBTransporter(groupID, null, groups, groupsReversed, null, groupNames);
    }

    @Test
    public void getGroup() {
        DBTransporter newTransporter = queries.getGroupsFromDB(instrumentation.getTargetContext());
        assertEquals(transporter.getClientID(), newTransporter.getClientID());
        assertEquals(transporter.getGroupID(), newTransporter.getGroupID());
        assertEquals(transporter.getNames(), newTransporter.getNames());
        assertEquals(transporter.getIdToName(), newTransporter.getIdToName());
        assertEquals(transporter.getNameToId(), newTransporter.getNameToId());
        assertEquals(transporter.getIdToId(), newTransporter.getIdToId());
    }

    @After
    public void tearDown() {
        database.close();
    }
}
