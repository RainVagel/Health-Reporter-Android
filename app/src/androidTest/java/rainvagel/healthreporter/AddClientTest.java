package rainvagel.healthreporter;

import android.app.DatePickerDialog;
import android.support.test.espresso.ViewInteraction;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.ClientClasses.InsertClientActivity;
import rainvagel.healthreporter.ClientClasses.NewClientActivity;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;



/**
 * Created by Cornelia on 02/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class AddClientTest {

    //under construction

    @Rule
    public ActivityTestRule<ClientActivity> clientActivityRule = new ActivityTestRule<>(ClientActivity.class);

    @Rule
    public ActivityTestRule<NewClientActivity> newClientActivityRule = new ActivityTestRule<>(NewClientActivity.class);

    @Rule
    public ActivityTestRule<InsertClientActivity> insertClientActivityRule = new ActivityTestRule<>(InsertClientActivity.class);

    @Before
    public void setUp() {
        onView(withId(R.id.fab1)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listViewGroups)).perform(click());
    }


    @Test
    public void testAddingNewClient() {
        onData(withId(R.id.first_name)).perform(typeText("John"));
        onData(withId(R.id.last_name)).perform(typeText("Smith"));
        onView(withId(R.id.radip_male)).perform(click());
        onView(withId(R.id.email_address)).perform(typeText("john.smith@gmail.com"));
        onView(withId(R.id.birthdate_picker)).perform(click());
        //onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(click());
        //onView(withText("OK")).perform(click());
        //onView(withId(R.id.button_add_client)).perform(click());
    }

    @Test
    public void checkIf_correctDateIsDisplayed() {
        //TODO
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
