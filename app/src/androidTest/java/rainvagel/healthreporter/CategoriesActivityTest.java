package rainvagel.healthreporter;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.Test;

import rainvagel.healthreporter.ClientClasses.ClientActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

/**
 * Created by Cornelia on 18/10/2016.
 */

@RunWith(AndroidJUnit4.class)
public class CategoriesActivityTest {

    // under construction

    @Rule
    public ActivityTestRule<ClientActivity> clientActivityRule = new ActivityTestRule<>(ClientActivity.class);

    @Rule
    public ActivityTestRule<CategoriesActivity> catActivityRule = new ActivityTestRule<>(CategoriesActivity.class);

    @Before
    public void setUp() {
        onData(withText(containsString("Kaarel Doe"))).perform(click());
    }

    @Test
    public void checkIf_addTestIntentIsIssued() {
        Intents.init();
        onData(withId(R.id.floatingActionButton5)).perform(click());
        intended(hasComponent(AddTestActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void checkIf_testActivityIntentIsIssued() {
        Intents.init();
        onView(withId(R.id.listView)).perform(click());
        intended(hasComponent(TestActivity.class.getName()));
        Intents.release();
    }

}
