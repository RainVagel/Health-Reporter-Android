package rainvagel.healthreporter;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rainvagel.healthreporter.ClientClasses.ClientActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Cornelia on 07/11/2016.
 */
@RunWith(AndroidJUnit4.class)
public class ClientOrderingTest {

    @Rule
    public ActivityTestRule<ClientActivity> clientActivityRule =
            new ActivityTestRule<>(ClientActivity.class);

    @Test
    public void orderByFirstNameTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Order by first name")).perform(click());
    }

    @Test
    public void orderByLastNameTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Order by last name")).perform(click());
    }

}
