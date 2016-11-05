package rainvagel.healthreporter;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowIntent;

import rainvagel.healthreporter.ClientClasses.ClientActivity;
import rainvagel.healthreporter.ClientClasses.InsertClientActivity;
import rainvagel.healthreporter.ClientClasses.NewClientActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by Cornelia on 05/11/2016.
 */

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class IntentTests {

    ClientActivity clientActivity;
    NewClientActivity newClientActivity;

    @Before
    public void setup() {
        clientActivity = Robolectric.setupActivity(ClientActivity.class);
        newClientActivity = Robolectric.setupActivity(NewClientActivity.class);
    }

    @Test
    public void assertButtonClickLaunchesNewClientActivity() {
        clientActivity.findViewById(R.id.fab1).performClick();
        Intent expectedIntent = new Intent(clientActivity, NewClientActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(clientActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void assertButtonClickLaunchesAddClientActivity() {
        newClientActivity.findViewById(R.id.listViewGroups).performClick();
        //Robolectric.shadowOf(listView).performItemClick(position);
        Intent expectedIntent = new Intent(newClientActivity, InsertClientActivity.class);
        ShadowActivity shadowActivity = Shadows.shadowOf(clientActivity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

}
