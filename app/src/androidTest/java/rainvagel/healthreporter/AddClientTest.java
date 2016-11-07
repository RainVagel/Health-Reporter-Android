package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import rainvagel.healthreporter.ClientClasses.InsertClientActivity;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;


/**
 * Created by Cornelia on 02/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class AddClientTest {

    @Rule
    public ActivityTestRule<InsertClientActivity> insertClientActivityRule =
            new ActivityTestRule<>(InsertClientActivity.class, true, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra("GroupID", "1,University of Tartu");
        insertClientActivityRule.launchActivity(intent);
    }

    @Test
    public void testAddingNewClient() {
        onView(withId(R.id.first_name)).perform(typeText("John"));
        onView(withId(R.id.last_name)).perform(typeText("Smith"));
        onView(withId(R.id.radip_male)).perform(click());
        onView(withId(R.id.email_address)).perform(typeText("john.smith@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.birthdate_picker)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.button_add_client)).perform(click());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        DBHelper database = new DBHelper(instrumentation.getTargetContext());
        String[] clientColumns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_GROUP_ID, DBContract.Clients.KEY_FIRSTNAME,
                DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_EMAIL, DBContract.Clients.KEY_GENDER,
                DBContract.Clients.KEY_BIRTHDATE, DBContract.Clients.KEY_UPDATED, DBContract.Clients.KEY_UPLOADED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int idIdx = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int groupIdx = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int firstNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int emailIdx = cursor.getColumnIndex(DBContract.Clients.KEY_EMAIL);
        int genIdx = cursor.getColumnIndex(DBContract.Clients.KEY_GENDER);
        int birtdayIdx = cursor.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);
        int updateIdx = cursor.getColumnIndex(DBContract.Clients.KEY_UPDATED);
        int uploadIdx = cursor.getColumnIndex(DBContract.Clients.KEY_UPLOADED);

        cursor.moveToLast();
        // for when we have id generator
        //cursor.moveToPrevious();
        //int prevID = Integer.parseInt(cursor.getString(idIdx));
        //cursor.moveToNext();
        //assertEquals(String.valueOf(prevID+1), cursor.getString(idIdx));
        assertEquals("98006", cursor.getString(idIdx));
        assertEquals(1, cursor.getInt(groupIdx));
        assertEquals("John", cursor.getString(firstNameIdx));
        assertEquals("Smith", cursor.getString(lastNameIdx));
        assertEquals("john.smith@gmail.com", cursor.getString(emailIdx));
        assertEquals(1, cursor.getInt(genIdx));
        //yy-mm-dd
        assertEquals("2016-11-06", cursor.getString(birtdayIdx));
        assertEquals(formattedDate, cursor.getString(updateIdx));
        assertEquals("0000-00-00", cursor.getString(uploadIdx));

    }

    @Test
    public void checkIf_correctDateIsDisplayed() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());
        String year = formattedDate.substring(0,4);
        String month = formattedDate.substring(5,7);
        String day = formattedDate.substring(8, formattedDate.length());

        onView(withId(R.id.birthdate_picker)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.textview_birth_day)).check(matches(withText(day)));
        onView(withId(R.id.textview_birth_month)).check(matches(withText(month)));
        onView(withId(R.id.textview_birth_year)).check(matches(withText(year)));
    }

    /*
    * there should be some kind of check after
    * 'add client' button is pressed to verify
    * that correct data is passed on
    * (in final product)
     */
    @Test
    public void displayError_ifIncorrectDataIsInserted() {
        //TODO

    }

}
