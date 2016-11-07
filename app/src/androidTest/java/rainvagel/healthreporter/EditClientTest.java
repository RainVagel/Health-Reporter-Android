package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.ClientClasses.EditClientActivity;
import rainvagel.healthreporter.ClientClasses.InsertClientActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Cornelia on 07/11/2016.
 */

public class EditClientTest {

    private DBHelper database;

    @Rule
    public ActivityTestRule<ClientActivity> clientActivityRule =
            new ActivityTestRule<>(ClientActivity.class);

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        String[] clientColumns = {DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int firstNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);

        cursor.moveToFirst();
        String name = cursor.getString(firstNameIdx);
        String lastName = cursor.getString(lastNameIdx);
        database.close();

        onData(startsWith(name + " " + lastName)).inAdapterView(withId(R.id.listViewClients)).perform(longClick());
        onView(withText("Edit")).perform(click());
    }

    @Test
    public void testEditingClient() {
        onView(withId(R.id.first_name)).perform(replaceText("Sander"));
        onView(withId(R.id.last_name)).perform(replaceText("Õigus"));
        onView(withId(R.id.email_address)).perform(replaceText("Sander.Õigus@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.birthdate_picker)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.button_select_group)).perform(click());
        onView(withText("Vilde")).perform(click());
        onView(withId(R.id.button_add_client)).perform(click());

        String[] clientColumns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_GROUP_ID, DBContract.Clients.KEY_FIRSTNAME,
                DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_EMAIL, DBContract.Clients.KEY_GENDER,
                DBContract.Clients.KEY_BIRTHDATE, DBContract.Clients.KEY_UPDATED, DBContract.Clients.KEY_UPLOADED};
        Cursor cursor2 = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);

        int idIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_ID);
        int groupIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int firstNameIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int emailIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_EMAIL);
        int genIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_GENDER);
        int birtdayIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);
        int updateIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_UPDATED);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());

        cursor2.moveToFirst();
        assertEquals("Sander", cursor2.getString(firstNameIdx));
        assertEquals("Õigus", cursor2.getString(lastNameIdx));
        assertEquals("Sander.Õigus@gmail.com", cursor2.getString(emailIdx));
        assertEquals(3, cursor2.getInt(groupIdx));
        assertEquals(1, cursor2.getInt(genIdx));
        assertEquals(formattedDate, cursor2.getString(birtdayIdx));
        assertEquals(formattedDate, cursor2.getString(updateIdx));
    }

}
