package rainvagel.healthreporter;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static android.support.test.espresso.matcher.ViewMatchers.hasFocus;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Cornelia on 16/10/2016.
 */

@RunWith(AndroidJUnit4.class)
public class ClientActivityTest {

    @Rule
    public ActivityTestRule<ClientActivity> mActivityRule = new ActivityTestRule<>(ClientActivity.class);

    @Before
    public void setActivity() {
        ClientActivity clientActivity = mActivityRule.getActivity();
    }

    @Test
    public void checkIf_miniFABsAreClickable() {
        onView(withId(R.id.fab1)).perform(click());
        onView(withId(R.id.fab2)).check(matches(isClickable()));
        onView(withId(R.id.fab3)).check(matches(isClickable()));

    }

    /*
    @Test
    public void checkIf_backgroundIsDimmedAndNotClickable() {  // should fail
        onView(withId(R.id.fab1)).perform(click());
        onView(withId(R.id.tabHost)).check(matches(not(isEnabled())));
        onView(withId(R.id.listViewClients)).check(matches(not(isClickable())));
        onView(withId(R.id.listViewGroups)).check(matches(not(isClickable())));
    }
    */

    @Test
    public void checkIf_rightIntentIsIssued_withCorrectExtra() {
        Intents.init();
        onData(hasToString(startsWith("Cornelia D"))).inAdapterView(withId(R.id.listViewClients)).perform(click());
        intended(allOf(hasComponent(CategoriesActivity.class.getName()), hasExtra("ClientId", "24,Cornelia Doe,Vilde")));
        Intents.release();
    }

    @Test
    public void checkIf_rightIntentIsIssued_withCorrectExtra2() {
        Intents.init();
        onData(hasToString(startsWith("Rain Mä"))).inAdapterView(withId(R.id.listViewClients)).perform(click());
        intended(allOf(hasComponent(CategoriesActivity.class.getName()), hasExtra("ClientId", "21,Rain Mänd,Konsum")));
        Intents.release();
    }
}
