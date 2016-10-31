package rainvagel.healthreporter;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
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
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

/**
 * Created by Cornelia on 16/10/2016.
 */

@RunWith(AndroidJUnit4.class)
public class ClientActivityTest {

    public static final String SEARCH_NAME = "Mary Jane";

    @Rule
    public ActivityTestRule<ClientActivity> activityRule = new ActivityTestRule<>(ClientActivity.class);

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
    // for buttons

    @Test
    public void checkIf_miniFABsAreClickable() {
        onView(withId(R.id.fab1)).perform(click());
        onView(withId(R.id.fab2)).check(matches(isClickable()));
        onView(withId(R.id.fab3)).check(matches(isClickable()));

    }

    @Test
    public void testClosingAddButton () {
        onView(withId(R.id.fab1)).perform(click());
        onView(withId(R.id.fab1)).perform(click());
        onView(withId(R.id.fab2)).check(matches(not(isDisplayed())));
        onView(withId(R.id.fab3)).check(matches(not(isDisplayed())));
    }

    @Test
    public void checkIf_backgroundIsDimmedAndNotClickable() {  // should fail
        onView(withId(R.id.fab1)).perform(click());
        onView(withId(R.id.tabHost)).check(matches(not(isEnabled())));
        onView(withId(R.id.listViewClients)).check(matches(not(isClickable())));
        onView(withId(R.id.listViewGroups)).check(matches(not(isClickable())));
    }


    // ---------------------------------------------------------------------------------------------
    // for search

    // works when autocorrect is turned off
    @Test
    public void testClientNotFound() {
        // Click on the search icon
        onView(withId(R.id.menu_search)).perform(click());

        // Type the text in the search field and submit the query
        onView(isAssignableFrom(EditText.class)).perform(typeText(SEARCH_NAME), pressImeActionButton());

        // Check the empty view is displayed
        onView(withId(R.id.activity_search_results)).check(matches(isDisplayed()));
        matchToolbarTitle(SEARCH_NAME);
    }
}
