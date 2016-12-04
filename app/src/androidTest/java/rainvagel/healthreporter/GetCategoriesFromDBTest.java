package rainvagel.healthreporter;

import android.app.Instrumentation;
import android.database.Cursor;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rainvagel.healthreporter.DBClasses.DBCategoriesTransporter;
import rainvagel.healthreporter.DBClasses.DBContract;
import rainvagel.healthreporter.DBClasses.DBHelper;
import rainvagel.healthreporter.DBClasses.DBQueries;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Cornelia on 04/12/2016.
 */

public class GetCategoriesFromDBTest {

    private Instrumentation instrumentation;
    private DBHelper database;
    private DBQueries queries;
    private DBCategoriesTransporter transporter;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        database = new DBHelper(instrumentation.getTargetContext());
        queries = new DBQueries();

        ArrayList<String> categoriesID = new ArrayList<>();
        Map<String, String> categoriesIdToParentId = new HashMap<>();
        Map<String, String> categoriesIdToName = new HashMap<>();
        Map<String, String> categoriesIdToPosition = new HashMap<>();
        Map<String, String> categoriesIdToUpdated = new HashMap<>();
        Map<String, String> categoriesIdToUploaded = new HashMap<>();

        String[] columns = {DBContract.TestCategories.KEY_ID, DBContract.TestCategories.KEY_PARENT_ID,
                DBContract.TestCategories.KEY_NAME, DBContract.TestCategories.KEY_POSITION,
                DBContract.TestCategories.KEY_UPDATED, DBContract.TestCategories.KEY_UPLOADED};
        Cursor cursor = database.getReadableDatabase().query(DBContract.TestCategories.TABLE_NAME,
                columns, null, null, null, null, null);

        String categoriesIDWorkable;
        int categoriesIndex = cursor.getColumnIndex(DBContract.TestCategories.KEY_ID);
        int parentIndex = cursor.getColumnIndex(DBContract.TestCategories.KEY_PARENT_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.TestCategories.KEY_NAME);
        int positionIndex = cursor.getColumnIndex(DBContract.TestCategories.KEY_POSITION);
        int updatedIndex = cursor.getColumnIndex(DBContract.TestCategories.KEY_UPDATED);
        int uploadedIndex = cursor.getColumnIndex(DBContract.TestCategories.KEY_UPLOADED);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            categoriesIDWorkable = cursor.getString(categoriesIndex);
            categoriesID.add(categoriesIDWorkable);
            categoriesIdToParentId.put(categoriesIDWorkable, cursor.getString(parentIndex));
            categoriesIdToName.put(categoriesIDWorkable, cursor.getString(nameIndex));
            categoriesIdToPosition.put(categoriesIDWorkable, cursor.getString(positionIndex));
            categoriesIdToUpdated.put(categoriesIDWorkable, cursor.getString(updatedIndex));
            categoriesIdToUploaded.put(categoriesIDWorkable, cursor.getString(uploadedIndex));
        }
        cursor.close();
        transporter = new DBCategoriesTransporter(categoriesID, categoriesIdToParentId, categoriesIdToName,
                categoriesIdToPosition, categoriesIdToUpdated, categoriesIdToUploaded);
    }

    @Test
    public void getCategories() {
        DBCategoriesTransporter newTransporter = queries.getCategoriesFromDB(instrumentation.getTargetContext());
        assertEquals(transporter.getCategoriesID(), newTransporter.getCategoriesID());
        assertEquals(transporter.getCategoriesIdToParentId(), newTransporter.getCategoriesIdToParentId());
        assertEquals(transporter.getCategoriesIdToName(), newTransporter.getCategoriesIdToName());
        assertEquals(transporter.getCategoriesIdToPosition(), newTransporter.getCategoriesIdToPosition());
        assertEquals(transporter.getCategoriesIdToUpdated(), newTransporter.getCategoriesIdToUpdated());
        assertEquals(transporter.getCategoriesIdToUploaded(), newTransporter.getCategoriesIdToUploaded());
    }

    @After
    public void tearDown() {
        database.close();

    }
}
