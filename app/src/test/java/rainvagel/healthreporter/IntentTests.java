package rainvagel.healthreporter;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenu;
import org.robolectric.shadows.ShadowActivity;

import rainvagel.healthreporter.CategoryClasses.CategoriesActivity;
import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.ClientClasses.InsertClientActivity;
import rainvagel.healthreporter.ClientClasses.NewClientActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
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
    InsertGroupActivity insertGroupActivity;

    @Before
    public void setup() {
        clientActivity = Robolectric.setupActivity(ClientActivity.class);
        newClientActivity = Robolectric.setupActivity(NewClientActivity.class);
        insertClientActivity = Robolectric.setupActivity(InsertClientActivity.class);
        //categoriesActivity = Robolectric.setupActivity(CategoriesActivity.class);
        insertGroupActivity = Robolectric.setupActivity(InsertGroupActivity.class);
    }

    // in ClientActivity
    @Test
    public void assert_buttonClick_launchesNewClientActivity() {
        clientActivity.findViewById(R.id.fab1).performClick();
        Intent expectedIntent = new Intent(clientActivity, NewClientActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(clientActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void assert_clickOnClient_launchesCategoriesActivity() {
        ListView list = (ListView) clientActivity.findViewById(R.id.listViewClients);
        Shadows.shadowOf(list).performItemClick(0);
        Intent expectedIntent = new Intent(clientActivity, CategoriesActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(clientActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void assert_longClickOnClient_launchesMenuInflater() {
        clientActivity.findViewById(R.id.listViewClients).performLongClick();
        MenuInflater menuInflater = new MenuInflater(clientActivity);
        Menu menu = new RoboMenu(clientActivity);
        menuInflater.inflate(R.menu.context_menu, menu);
        assertNotNull(menu);
        assertEquals("Edit", menu.findItem(R.id.cnt_mnu_edit).getTitle());
        assertEquals("Delete", menu.findItem(R.id.cnt_mnu_delete).getTitle());
    }

    // in NewClientActivity
    @Test
    public void assert_clickOnCategory_launchesInsertClientActivity() {
        ListView list = (ListView) newClientActivity.findViewById(R.id.listViewGroups);
        Shadows.shadowOf(list).performItemClick(0);
        Intent expectedIntent = new Intent(newClientActivity, InsertClientActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(newClientActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void assert_clickOnFAB_launchesInsertGroupActivity() {
        newClientActivity.findViewById(R.id.fab1).performClick();
        Intent expectedIntent = new Intent(newClientActivity, InsertGroupActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(newClientActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    // in CategoriesActivity
    /*
    @Test
    public void assert_clickOnCategory_launchesTestsActivity() {
        ListView list = (ListView) categoriesActivity.findViewById(R.id.listView);
        Shadows.shadowOf(list).performItemClick(0);
        Intent expectedIntent = new Intent(categoriesActivity, InsertClientActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(categoriesActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void assert_clickOnButton_launchesAddTestActivity() {
        categoriesActivity.findViewById(R.id.floatingActionButton5).performClick();
        Intent expectedIntent = new Intent(categoriesActivity, AddTestActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(categoriesActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
    */


    // in InsertGroupActivity
    @Test
    public void assert_clickOnButton_launchesClientActivity() {
        insertGroupActivity.findViewById(R.id.button_add_client).performClick();
        Intent expectedIntent = new Intent(insertGroupActivity, ClientActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(insertGroupActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
}
