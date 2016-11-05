package rainvagel.healthreporter;

import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.ClientClasses.InsertClientActivity;
import rainvagel.healthreporter.ClientClasses.NewClientActivity;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Cornelia on 05/11/2016.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class IntentTests {

    ClientActivity clientActivity;
    NewClientActivity newClientActivity;
    InsertClientActivity insertClientActivity;
    CategoriesActivity categoriesActivity;

    @Before
    public void setup() {
        clientActivity = Robolectric.setupActivity(ClientActivity.class);
        newClientActivity = Robolectric.setupActivity(NewClientActivity.class);
        insertClientActivity = Robolectric.setupActivity(InsertClientActivity.class);
        //categoriesActivity = Robolectric.setupActivity(CategoriesActivity.class);
    }

    // in ClientActivity
    @Test
    public void assertButtonClick_launchesNewClientActivity() {
        clientActivity.findViewById(R.id.fab1).performClick();
        Intent expectedIntent = new Intent(clientActivity, NewClientActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(clientActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void assertclickOnClient_launchesCategoriesActivity() {
        ListView list = (ListView) clientActivity.findViewById(R.id.listViewClients);
        Shadows.shadowOf(list).performItemClick(0);
        Intent expectedIntent = new Intent(clientActivity, CategoriesActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(clientActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }


    // in NewClientActivity
    @Test
    public void assertClickOnCategory_launchesInsertClientActivity() {
        ListView list = (ListView) newClientActivity.findViewById(R.id.listViewGroups);
        Shadows.shadowOf(list).performItemClick(0);
        Intent expectedIntent = new Intent(newClientActivity, InsertClientActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(newClientActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    // in CategorisActivity
    /*
    @Test
    public void assertClickOnCategory_launchesTestsActivity() {
        ListView list = (ListView) categoriesActivity.findViewById(R.id.listView);
        Shadows.shadowOf(list).performItemClick(0);
        Intent expectedIntent = new Intent(categoriesActivity, InsertClientActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(categoriesActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void assertClickOnButton_launchesAddTestActivity() {
        categoriesActivity.findViewById(R.id.floatingActionButton5).performClick();
        Intent expectedIntent = new Intent(categoriesActivity, AddTestActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(categoriesActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
    */
}
