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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
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

    @Test
    public void checkIf_correctDateIsDisplayed() {
        String[] clientColumn = {DBContract.Clients.KEY_BIRTHDATE};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumn, null,null,null,null,null);
        int birthdayIdx = cursor.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);
        cursor.moveToFirst();
        String birthday = cursor.getString(birthdayIdx);
        database.close();

        String year = birthday.substring(0,4);
        String month = birthday.substring(5,7);
        String day = birthday.substring(8, birthday.length());

        onView(withId(R.id.textview_birth_day)).check(matches(withText(day)));
        onView(withId(R.id.textview_birth_month)).check(matches(withText(month)));
        onView(withId(R.id.textview_birth_year)).check(matches(withText(year)));
    }

    @Test
    public void checkIf_correctDateIsDisplayed_afterPickingNewDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());

        String year1 = formattedDate.substring(0,4);
        String month1 = formattedDate.substring(5,7);
        String day1 = formattedDate.substring(8, formattedDate.length());

        onView(withId(R.id.birthdate_picker)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.textview_birth_day)).check(matches(withText(day1)));
        onView(withId(R.id.textview_birth_month)).check(matches(withText(month1)));
        onView(withId(R.id.textview_birth_year)).check(matches(withText(year1)));
    }

    @Test
    public void checkIf_correctGroupIsDisplayed() {
        String[] clientColumn = {DBContract.Clients.KEY_GROUP_ID};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumn, null, null, null, null, null);
        int groupIdx = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        cursor.moveToFirst();
        String clientGroupId = cursor.getString(groupIdx);
        cursor.close();

        String[] groupColumns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
        cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, groupColumns, null, null, null, null, null);
        groupIdx = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int groupNameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);

        String groupName = "";
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (clientGroupId.equals(cursor.getString(groupIdx))) {
                groupName = cursor.getString(groupNameIndex);
            }
        }
        cursor.close();

        onView(withId(R.id.textview_group_name)).check(matches(withText(groupName)));
    }
}
