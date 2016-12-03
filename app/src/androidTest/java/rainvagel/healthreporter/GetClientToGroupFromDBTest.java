package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

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
 * Created by Cornelia on 03/12/2016.
 */

public class GetClientToGroupFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBTransporter transporter;
    private static final String GROUPNAME = "Group1";
    private static final String UPDATED   = "2017-12-31";

    private static final String FIRSTNAME = "Client";
    private static final String LASTNAME  = "Client";
    private static final String EMAIL     = "testclient@gmail.com";
    private static final int    GENDER    = 0;    //0 - female, 1 - male
    private static final String YEAR      = "1999";
    private static final String MONTH     = "1";
    private static final String DAY       = "1";
    private static final String UPDATED1   = "2016-12-31";
    private static final String UPLOADED  = "0000-00-00";
    private static final String BIRTHDAY  = YEAR + "-" + MONTH + "-" + DAY;

    private String groupUuid;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        queries.insertGroupToDB(instrumentation.getTargetContext(), GROUPNAME, UPDATED);
        String[] groupColumns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, groupColumns,
                null,null,null,null,null);

        int idIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIdx = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()) {
            if (cursor.getString(nameIdx).equals(GROUPNAME)) {
                groupUuid = cursor.getString(idIndex);
            }
        }

        queries.insertClientToDB(instrumentation.getTargetContext(), groupUuid, FIRSTNAME, LASTNAME, EMAIL, GENDER, YEAR, MONTH, DAY, UPDATED1);

        ArrayList<String> clientIDs = new ArrayList<>();
        ArrayList<String> groupIDs = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        Map<String, String> clientIdGroupId = new HashMap<>();
        Map<String, String> namesGroupKeys = new HashMap<>();
        Map<String, String> namesClientKeys = new HashMap<>();

        String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME,
                DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_GROUP_ID};
        cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns,
                null,null,null,null,null);

        int rowIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        final int groupIndex  = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            //Log.v("ClientActivity", "Made it here");
            clientIDs.add(cursor.getString(rowIndex));
            groupIDs.add(cursor.getString(groupIndex));
            //groupids contains exact amount of groupids as clientids
            names.add(cursor.getString(firstNameIndex)+ " " + cursor.getString(lastNameIndex));

            clientIdGroupId.put(cursor.getString(rowIndex),cursor.getString(groupIndex));

            namesGroupKeys.put(cursor.getString(firstNameIndex)+ " " + cursor.getString(lastNameIndex),
                    (cursor.getString(groupIndex)));

            namesClientKeys.put(cursor.getString(firstNameIndex)+ " "+ cursor.getString(lastNameIndex),
                    (cursor.getString(rowIndex)));
        }

        cursor.close();
        transporter = new DBTransporter(groupIDs, clientIDs, namesClientKeys, namesGroupKeys, clientIdGroupId, names);
    }

    @Test
    public void getClientToGroupInfo() {
        DBTransporter newTransporter = queries.getClientToGroupFromDB(instrumentation.getTargetContext());
        for (int i = 0; i<transporter.getIdToName().size();i++) {
            if (transporter.getIdToName().get(FIRSTNAME + " " + LASTNAME).equals(groupUuid)) {
                assertEquals(groupUuid, newTransporter.getIdToName().get(FIRSTNAME + " " + LASTNAME));
            }
        }
        assertEquals(transporter.getClientID(), newTransporter.getClientID());
        assertEquals(transporter.getGroupID(), newTransporter.getGroupID());
        assertEquals(transporter.getNames(), newTransporter.getNames());
        assertEquals(transporter.getIdToName(), newTransporter.getIdToName());
        assertEquals(transporter.getNameToId(), newTransporter.getNameToId());
        assertEquals(transporter.getIdToId(), newTransporter.getIdToId());

    }
}
