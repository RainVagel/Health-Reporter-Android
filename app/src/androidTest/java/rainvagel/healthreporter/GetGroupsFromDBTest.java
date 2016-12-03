package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import java.util.UUID;

import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTransporter;

/**
 * Created by Cornelia on 02/12/2016.
 */

public class GetGroupsFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBTransporter transporter;

    private static final String ID1 = UUID.randomUUID().toString();
    private static final String ID2 = UUID.randomUUID().toString();



    @Test
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();
    }

    @Test
    public void getGroup() {
        queries.getGroupsFromDB(instrumentation.getTargetContext());
        //TODO

    }
}
