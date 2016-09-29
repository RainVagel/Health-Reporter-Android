package rainvagel.healthreporter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rainvagel on 22.09.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.Groups.CREATE_TABLE);
        db.execSQL(DBContract.Clients.CREATE_TABLE);
        db.execSQL(DBContract.Appraisers.CREATE_TABLE);
        db.execSQL(DBContract.Appraisals.CREATE_TABLE);
        db.execSQL(DBContract.TestCategories.CREATE_TABLE);
        db.execSQL(DBContract.Tests.CREATE_TABLE);
        db.execSQL(DBContract.RatingLabels.CREATE_TABLE);
        db.execSQL(DBContract.Ratings.CREATE_TABLE);
        db.execSQL(DBContract.Presets.CREATE_TABLE);
        db.execSQL(DBContract.PresetTests.CREATE_TABLE);
        db.execSQL(DBContract.AppraisalTests.CREATE_TABLE);
        insertMockData(db);
    }

    private void insertMockData(SQLiteDatabase db) {
//        Groups
        ContentValues groupsValues = new ContentValues();
        groupsValues.put(DBContract.Groups.KEY_ID, 001);
        groupsValues.put(DBContract.Groups.KEY_NAME, "University of Tartu");
        groupsValues.put(DBContract.Groups.KEY_UPDATED, "2014-05-10");
        groupsValues.put(DBContract.Groups.KEY_UPLOADED, "2014-05-11");

//        Clients
        ContentValues clientsValues = new ContentValues();
        clientsValues.put(DBContract.Clients.KEY_ID, 002);
        clientsValues.put(DBContract.Clients.KEY_FIRSTNAME, "Kaarel");
        clientsValues.put(DBContract.Clients.KEY_LASTNAME, "Sõrmus");
        clientsValues.put(DBContract.Clients.KEY_GROUP_ID, 001);
        clientsValues.put(DBContract.Clients.KEY_EMAIL, "kaarel.sõrmus@gmail.com");
        clientsValues.put(DBContract.Clients.KEY_GENDER, 1);
        clientsValues.put(DBContract.Clients.KEY_BIRTHDATE, "1995-05-10");
        clientsValues.put(DBContract.Clients.KEY_UPDATED, "2014-05-12");
        clientsValues.put(DBContract.Clients.KEY_UPLOADED, "2014-05-13");

//        Appraisers
        ContentValues appraisersValues = new ContentValues();
        appraisersValues.put(DBContract.Appraisers.KEY_ID, 003);
        appraisersValues.put(DBContract.Appraisers.KEY_NAME,"Karl Lubja");
        appraisersValues.put(DBContract.Appraisers.KEY_UPDATED, "2014-05-10");
        appraisersValues.put(DBContract.Appraisers.KEY_UPLOADED, "2014-05-09");

//        Appraisals
        ContentValues appraisalsValues = new ContentValues();
        appraisalsValues.put(DBContract.Appraisals.KEY_ID, 004);
        appraisalsValues.put(DBContract.Appraisals.KEY_APPRAISER_ID, 003);
        appraisalsValues.put(DBContract.Appraisals.KEY_CLIENT_ID, 002);
        appraisalsValues.put(DBContract.Appraisals.KEY_DATE, "2014-05-13");
        appraisalsValues.put(DBContract.Appraisals.KEY_UPDATED, "2014-05-13");
        appraisalsValues.put(DBContract.Appraisals.KEY_UPLOADED, "2014-05-14");

//        RatingLabels
        ContentValues ratingLabelsValues = new ContentValues();
        ratingLabelsValues.put(DBContract.RatingLabels.KEY_ID, 005);
        ratingLabelsValues.put(DBContract.RatingLabels.KEY_NAME, "Maximillian");
        ratingLabelsValues.put(DBContract.RatingLabels.KEY_INTERPRETATION, "Sick");
        ratingLabelsValues.put(DBContract.RatingLabels.KEY_UPDATED, "2014-05-09");
        ratingLabelsValues.put(DBContract.RatingLabels.KEY_UPLOADED, "2014-05-10");

//        TestCategories
        ContentValues testCategoriesValues = new ContentValues();
        testCategoriesValues.put(DBContract.TestCategories.KEY_ID, 006);
        testCategoriesValues.put(DBContract.TestCategories.KEY_PARENT_ID, "null");
        testCategoriesValues.put(DBContract.TestCategories.KEY_NAME, "Test");
        testCategoriesValues.put(DBContract.TestCategories.KEY_POSITION, 1);
        testCategoriesValues.put(DBContract.TestCategories.KEY_UPDATED, "2014-05-15");
        testCategoriesValues.put(DBContract.TestCategories.KEY_UPLOADED, "2014-05-15");

//        Tests
        ContentValues testValues = new ContentValues();
        testValues.put(DBContract.Tests.KEY_ID, 007);
        testValues.put(DBContract.Tests.KEY_CATEGORY_ID, 006);
        testValues.put(DBContract.Tests.KEY_NAME, "Skin");
        testValues.put(DBContract.Tests.KEY_DESCRIPTION, "For some stuff");
        testValues.put(DBContract.Tests.KEY_UNITS, "meters");
        testValues.put(DBContract.Tests.KEY_DECIMALS, 2);
        testValues.put(DBContract.Tests.KEY_WEIGHT, 3);
        testValues.put(DBContract.Tests.KEY_FORMULA_F, "2");
        testValues.put(DBContract.Tests.KEY_FORMULA_M, "3");
        testValues.put(DBContract.Tests.KEY_POSITION, 0);
        testValues.put(DBContract.Tests.KEY_UPDATED, "2014-05-01");
        testValues.put(DBContract.Tests.KEY_UPLOADED, "2014-05-01");

//        Ratings

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }
}
