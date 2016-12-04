package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Cornelia on 04/12/2016.
 */

public class EditClientTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;

    private String clientUuid;
    private String groupUuid;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String year;
    private String month;
    private String day;
    private String birthday;
    private String updated;
    private String uploaded;

    private String vildeUuid;

    private static final String FIRSTNAME = "FirstName";
    private static final String LASTNAME  = "LastName";
    private static final String EMAIL     = "firstlast@gmail.com";
    private static final int    GENDER    = 0;    //0 - female, 1 - male
    private static final String YEAR      = "1940";
    private static final String MONTH     = "01";
    private static final String DAY       = "01";
    private static final String UPDATED   = "2016-12-04";
    private static final String UPLOADED  = "0000-00-00";
    private static final String BIRTHDAY  = YEAR + "-" + MONTH + "-" + DAY;

    @Rule
    public ActivityTestRule<ClientActivity> clientActivityActivityTestRule =
            new ActivityTestRule<>(ClientActivity.class);

    @Before
    public void setUp() throws Exception {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        String[] clientColumns = {DBContract.Clients.KEY_ID};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Clients.KEY_ID);

        cursor.moveToFirst();
        clientUuid = cursor.getString(idIdx);
        ArrayList<String> details = queries.getClientDetailsFromDB(instrumentation.getTargetContext(), clientUuid);

        firstName  = details.get(0);
        lastName   = details.get(1);
        birthday   = details.get(2);
        gender     = details.get(3);
        groupUuid  = details.get(4);
        email      = details.get(5);
        updated    = details.get(6);

        String[] groupColumns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
        cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, groupColumns, null,null,null,null,null);
        int idIdx1 = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIdx = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getString(nameIdx).equals("Vilde")) {
                vildeUuid = cursor.getString(idIdx1);
            }
        }

        cursor.close();

        onData(startsWith(firstName + " " + lastName)).inAdapterView(withId(R.id.listViewClients)).perform(longClick());
        onView(withText("Edit")).perform(click());
    }

    @Test
    public void editClient() {
        onView(withId(R.id.first_name)).perform(replaceText(FIRSTNAME));
        onView(withId(R.id.last_name)).perform(replaceText(LASTNAME));
        onView(withId(R.id.radio_female)).perform(click());
        onView(withId(R.id.email_address)).perform(replaceText(EMAIL));
        closeSoftKeyboard();
        onView(withId(R.id.birthdate_picker)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.button_select_group)).perform(click());
        onView(withText("Vilde")).perform(click());
        onView(withId(R.id.button_add_client)).perform(click());

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
            if (cursor.getString(idIdx).equals(clientUuid)) {
                assertEquals(vildeUuid, cursor.getString(groupIdx));
                assertEquals(FIRSTNAME, cursor.getString(firstNameIdx));
                assertEquals(LASTNAME, cursor.getString(lastNameIdx));
                assertEquals(EMAIL, cursor.getString(emailIdx));
                assertEquals(GENDER, cursor.getInt(genderIdx));
                //assertEquals(BIRTHDAY, cursor.getString(birthdayIdx));
            }
        }
        cursor.close();
    }

    @After
    public void tearDown() {
        //queries.editClientInDB(instrumentation.getTargetContext(), clientUuid, firstName, lastName, email, gender, birthday, updated, uploaded);
        database.close();

    }
}
