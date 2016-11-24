package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.Test;

import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Cornelia on 16/10/2016.
 */

@RunWith(AndroidJUnit4.class)
public class ClientActivityTest {

    private static final String SEARCH_NAME = "Mary Jane";
    private DBHelper database;

    @Rule
    public ActivityTestRule<ClientActivity> activityRule = new ActivityTestRule<>(ClientActivity.class);

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());

    }

    // for toolbar
    private static ViewInteraction matchToolbarTitle(CharSequence title) {
        return onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarTitle(is(title))));
    }

    private static Matcher<Object> withToolbarTitle(
            final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    @Test
    public void checkIf_correctToolbarIsShown() {
        matchToolbarTitle("Health Reporter");
    }


    // ---------------------------------------------------------------------------------------------
    // for search

    // works when autocorrect is turned off
    /*
    @Test
    public void testClientNotFound() {
        onView(withId(R.id.menu_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(SEARCH_NAME), pressImeActionButton());
        onView(withId(R.id.activity_search_results)).check(matches(isDisplayed()));
        //onData().inAdapterView(withId(R.id.main)).perform(click());
        // maybe later should display some message like "no clients found"
        matchToolbarTitle(SEARCH_NAME);
    }
    */

    public String getFirstClientsName() {
        String[] clientColumns = {DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, clientColumns, null,null,null,null,null);
        int firstNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIdx = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);

        cursor.moveToFirst();
        String firstName = cursor.getString(firstNameIdx);
        String lastName = cursor.getString(lastNameIdx);
        cursor.close();
        return firstName + " " + lastName;
    }

    /*
    @Test
    public void testClientFound() {
        String search = getFirstClientsName();
        onView(withId(R.id.menu_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressImeActionButton());
        onView(withId(R.id.activity_search_results)).check(matches(isDisplayed()));

        onData(startsWith(search)).inAdapterView(withId(R.id.main)).check(matches(isDisplayed()));
        matchToolbarTitle(search);
    }
    */
    /*
    @Test
    public void testGroupNotFound() {
        onView(withText("Groups")).perform(click());
        onView(withId(R.id.menu_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(SEARCH_NAME), pressImeActionButton());
        onView(withId(R.id.activity_search_results)).check(matches(isDisplayed()));
        //onData().inAdapterView(withId(R.id.main)).perform(click());
        // maybe later should display some message like "no clients found"
        matchToolbarTitle(SEARCH_NAME);
    }
    */

    public String getFirstGroupName() {
        String[] groupColumns = {DBContract.Groups.KEY_NAME};
        Cursor cursor = database.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, groupColumns, null,null,null,null,null);
        int nameIdx = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);

        cursor.moveToFirst();
        String name = cursor.getString(nameIdx);
        cursor.close();
        return name;
    }

    @Test
    public void testGroupFound() {
        String search = getFirstGroupName();
        onView(withText("Groups")).perform(click());
        onView(withId(R.id.menu_search)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText(search), pressImeActionButton());
        onView(withId(R.id.activity_search_results)).check(matches(isDisplayed()));

        onData(startsWith(search)).inAdapterView(withId(R.id.main)).check(matches(isDisplayed()));
        matchToolbarTitle(search);
    }
}
