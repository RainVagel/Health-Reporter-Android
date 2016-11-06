package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.view.Menu;
import android.view.MenuInflater;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.ClientClasses.InsertClientActivity;

import static android.support.test.espresso.Espresso.getIdlingResources;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Cornelia on 06/11/2016.
 */

public class RemoveClientTest {

    @Rule
    public ActivityTestRule<ClientActivity> clientActivityRule =
            new ActivityTestRule<>(ClientActivity.class);

    @Test
    public void testRemovingClient() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        DBHelper database = new DBHelper(instrumentation.getTargetContext());
        String[] clientColumns = {DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int firstNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);

        cursor.moveToFirst();
        String name = cursor.getString(firstNameIdx);
        String lastName = cursor.getString(lastNameIdx);
        cursor.moveToNext();
        String name2 = cursor.getString(firstNameIdx);
        String lastName2 = cursor.getString(lastNameIdx);

        onData(startsWith(name + " " + lastName)).inAdapterView(withId(R.id.listViewClients)).perform(longClick());
        onView(withText("Delete")).perform(click());


        Cursor cursor2 = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        cursor2.moveToFirst();
        firstNameIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        lastNameIdx = cursor2.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        assertEquals(name2, cursor2.getString(firstNameIdx));
        assertEquals(lastName2, cursor2.getString(lastNameIdx));

    }
}
