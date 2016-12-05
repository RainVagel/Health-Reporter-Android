package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.DBClasses.DBClientsTransporter;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 04/12/2016.
 */

public class GetClientsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBClientsTransporter transporter;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();



        ArrayList<String> clientID = new ArrayList<>();
        Map<String, String> clientIdToFirstName = new HashMap<>();
        Map<String, String> clientIdToLastName = new HashMap<>();
        Map<String, String> clientIdToEmail = new HashMap<>();
        Map<String, String> clientIdToGender = new HashMap<>();
        Map<String, String> clientIdToGroupId = new HashMap<>();
        Map<String, String> clientIdToBirthDate = new HashMap<>();
        Map<String, String> clientIdToUpdated = new HashMap<>();

        String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME,
                DBContract.Clients.KEY_GROUP_ID, DBContract.Clients.KEY_EMAIL, DBContract.Clients.KEY_BIRTHDATE,
                DBContract.Clients.KEY_GENDER, DBContract.Clients.KEY_UPDATED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns,
                null, null, null, null, null);

        String clientIDWorkable;
        int clientIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int genderIndex = cursor.getColumnIndex(DBContract.Clients.KEY_GENDER);
        int groupIndex = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int emailIndex = cursor.getColumnIndex(DBContract.Clients.KEY_EMAIL);
        int birthDateIndex = cursor.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);
        int updatedIndex = cursor.getColumnIndex(DBContract.Clients.KEY_UPDATED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            clientIDWorkable = cursor.getString(clientIndex);
            clientID.add(clientIDWorkable);
            clientIdToFirstName.put(clientIDWorkable, cursor.getString(firstNameIndex));
            clientIdToLastName.put(clientIDWorkable, cursor.getString(lastNameIndex));
            clientIdToGender.put(clientIDWorkable, cursor.getString(genderIndex));
            clientIdToGroupId.put(clientIDWorkable, cursor.getString(groupIndex));
            clientIdToEmail.put(clientIDWorkable, cursor.getString(emailIndex));
            clientIdToBirthDate.put(clientIDWorkable, cursor.getString(birthDateIndex));
            clientIdToUpdated.put(clientIDWorkable, cursor.getString(updatedIndex));
        }
        cursor.close();
        transporter = new DBClientsTransporter(clientID, clientIdToFirstName, clientIdToLastName,
                clientIdToEmail, clientIdToGender, clientIdToGroupId, clientIdToBirthDate, clientIdToUpdated);

    }

    @Test
    public void getClients() {
        DBClientsTransporter newTransporter = queries.getClientsFromDB(instrumentation.getTargetContext());
        assertEquals(transporter.getClientID(), newTransporter.getClientID());
        assertEquals(transporter.getClientIdToFirstName(), newTransporter.getClientIdToFirstName());
        assertEquals(transporter.getClientIdToLastName(), newTransporter.getClientIdToLastName());
        assertEquals(transporter.getClientIdToEmail(), newTransporter.getClientIdToEmail());
        assertEquals(transporter.getClientIdToGender(), newTransporter.getClientIdToGender());
        assertEquals(transporter.getClientIdToGroupId(), newTransporter.getClientIdToGroupId());
        assertEquals(transporter.getClientIdToBirthDate(), newTransporter.getClientIdToBirthDate());
        assertEquals(transporter.getClientIdToUpdated(), newTransporter.getClientIdToUpdated());

    }
}
