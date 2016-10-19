package rainvagel.healthreporter;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Cornelia on 18/10/2016.
 */

@RunWith(AndroidJUnit4.class)
public class CategoriesActivityTest {

    @Rule
    public ActivityTestRule<CategoriesActivity> mActivityRule = new ActivityTestRule<>(CategoriesActivity.class);

    @Before
    public void setActivity() {
        CategoriesActivity categoriesActivity = mActivityRule.getActivity();
    }

    @Test
    public void checkIf_addTestIntentIsIssued() {
        Intents.init();
        onView(withId(R.id.floatingActionButton5)).perform(click());
        intended(allOf(hasComponent(AddTestActivity.class.getName()), hasExtra("ClientData", "2")));
        Intents.release();
    }


    @Test
    public void checkIf_testActivityIntentIsIssued() {
        Intents.init();
        onView(withId(R.id.listView)).perform(click());
        intended(allOf(hasComponent(TestActivity.class.getName())));
        Intents.release();
    }

}
