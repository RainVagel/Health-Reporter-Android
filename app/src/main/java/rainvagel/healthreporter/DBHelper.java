package rainvagel.healthreporter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        Random random = new Random();

//        Groups
        List<String> groupName = new ArrayList<>();
        groupName.add("University of Tartu");
        groupName.add("University of Tallinn");
        groupName.add("Vilde");
        groupName.add("Konsum");
        groupName.add("Cinamon");
        int key = 1;
        int groupStart = key;
        for (String group : groupName) {
            ContentValues groupsValues = new ContentValues();
            groupsValues.put(DBContract.Groups.KEY_ID, key);
            groupsValues.put(DBContract.Groups.KEY_NAME, group);
            groupsValues.put(DBContract.Groups.KEY_UPDATED, "2014-05-10");
            groupsValues.put(DBContract.Groups.KEY_UPLOADED, "2014-05-11");
            key += 1;
            db.insert(DBContract.Groups.TABLE_NAME, null, groupsValues);

        }
        int groupsEnd = key;
        int clientsStart = key;

//        Clients
        List<String> clientNameFirst = new ArrayList<>();
        clientNameFirst.add("Kaarel");
        clientNameFirst.add("Karl");
        clientNameFirst.add("Rain");
        clientNameFirst.add("Cornelia");
        clientNameFirst.add("John");
        clientNameFirst.add("Martin");
        clientNameFirst.add("Sandra");
        List<String> clientNameLast = new ArrayList<>();
        clientNameLast.add("Doe");
        clientNameLast.add("Malkovich");
        clientNameLast.add("Tamm");
        clientNameLast.add("Mänd");
        clientNameLast.add("Kask");
        clientNameLast.add("Bauman");
        for (String firstName : clientNameFirst) {
            for (String lastName : clientNameLast) {
                ContentValues clientsValues = new ContentValues();
                clientsValues.put(DBContract.Clients.KEY_ID, key);
                clientsValues.put(DBContract.Clients.KEY_FIRSTNAME, firstName);
                clientsValues.put(DBContract.Clients.KEY_LASTNAME, lastName);
                clientsValues.put(DBContract.Clients.KEY_GROUP_ID, random.nextInt(groupsEnd
                        - groupStart + 1) + groupStart);
                clientsValues.put(DBContract.Clients.KEY_EMAIL, firstName + "." + lastName + "@gmail.com");
                clientsValues.put(DBContract.Clients.KEY_GENDER, random.nextInt(2-1+1)+1);
                clientsValues.put(DBContract.Clients.KEY_BIRTHDATE, "1995-05-10");
                clientsValues.put(DBContract.Clients.KEY_UPDATED, "2014-05-12");
                clientsValues.put(DBContract.Clients.KEY_UPLOADED, "2014-05-13");
                key += 1;
                db.insert(DBContract.Clients.TABLE_NAME, null, clientsValues);
            }
        }
        int clientsEnd = key;
//        Groups
//        ContentValues groupsValues = new ContentValues();
//        groupsValues.put(DBContract.Groups.KEY_ID, 1);
//        groupsValues.put(DBContract.Groups.KEY_NAME, "University of Tartu");
//        groupsValues.put(DBContract.Groups.KEY_UPDATED, "2014-05-10");
//        groupsValues.put(DBContract.Groups.KEY_UPLOADED, "2014-05-11");

//        Clients
//        ContentValues clientsValues = new ContentValues();
//        clientsValues.put(DBContract.Clients.KEY_ID, 2);
//        clientsValues.put(DBContract.Clients.KEY_FIRSTNAME, "Kaarel");
//        clientsValues.put(DBContract.Clients.KEY_LASTNAME, "Sõrmus");
//        clientsValues.put(DBContract.Clients.KEY_GROUP_ID, 1);
//        clientsValues.put(DBContract.Clients.KEY_EMAIL, "kaarel.sõrmus@gmail.com");
//        clientsValues.put(DBContract.Clients.KEY_GENDER, 1);
//        clientsValues.put(DBContract.Clients.KEY_BIRTHDATE, "1995-05-10");
//        clientsValues.put(DBContract.Clients.KEY_UPDATED, "2014-05-12");
//        clientsValues.put(DBContract.Clients.KEY_UPLOADED, "2014-05-13");

//        Appraisers
        ContentValues appraisersValues = new ContentValues();
        appraisersValues.put(DBContract.Appraisers.KEY_ID, 133742069);
        appraisersValues.put(DBContract.Appraisers.KEY_NAME,"Karl Lubja");
        appraisersValues.put(DBContract.Appraisers.KEY_UPDATED, "2014-05-10");
        appraisersValues.put(DBContract.Appraisers.KEY_UPLOADED, "2014-05-09");
        db.insert(DBContract.Appraisers.TABLE_NAME, null, appraisersValues);

        int appraisalStart = key;
//        Appraisals
        int i = 0;
        while (i != 40) {
            ContentValues appraisalValues = new ContentValues();
            appraisalValues.put(DBContract.Appraisals.KEY_ID, key);
            appraisalValues.put(DBContract.Appraisals.KEY_APPRAISER_ID, 133742069);
            appraisalValues.put(DBContract.Appraisals.KEY_CLIENT_ID, random.nextInt(clientsEnd
                    - clientsStart + 1) + clientsStart);
            appraisalValues.put(DBContract.Appraisals.KEY_DATE, "2014-05-13");
            appraisalValues.put(DBContract.Appraisals.KEY_UPDATED, "2014-05-13");
            appraisalValues.put(DBContract.Appraisals.KEY_UPLOADED, "2014-05-14");
            key += 1;
            db.insert(DBContract.Appraisals.TABLE_NAME,null,appraisalValues);
            i += 1;
        }
        int appraisalEnd = key;

//        Appraisals
//        ContentValues appraisalsValues = new ContentValues();
//        appraisalsValues.put(DBContract.Appraisals.KEY_ID, 4);
//        appraisalsValues.put(DBContract.Appraisals.KEY_APPRAISER_ID, 3);
//        appraisalsValues.put(DBContract.Appraisals.KEY_CLIENT_ID, 2);
//        appraisalsValues.put(DBContract.Appraisals.KEY_DATE, "2014-05-13");
//        appraisalsValues.put(DBContract.Appraisals.KEY_UPDATED, "2014-05-13");
//        appraisalsValues.put(DBContract.Appraisals.KEY_UPLOADED, "2014-05-14");

//        RatingLabels
//        ContentValues ratingLabelsValues = new ContentValues();
//        ratingLabelsValues.put(DBContract.RatingLabels.KEY_ID, 5);
//        ratingLabelsValues.put(DBContract.RatingLabels.KEY_NAME, "Maximillian");
//        ratingLabelsValues.put(DBContract.RatingLabels.KEY_INTERPRETATION, "Sick");
//        ratingLabelsValues.put(DBContract.RatingLabels.KEY_UPDATED, "2014-05-09");
//        ratingLabelsValues.put(DBContract.RatingLabels.KEY_UPLOADED, "2014-05-10");

        int categoriesStart = key;
//        TestCategories
        List<String> categoriesNames = new ArrayList<>();
        categoriesNames.add("Insener");
        categoriesNames.add("Gagarini");
        categoriesNames.add("Hüperboloid");
        categoriesNames.add("Tühjad");
        categoriesNames.add("Pihud");
        for (String name : categoriesNames) {
            ContentValues categoriesValues = new ContentValues();
            categoriesValues.put(DBContract.TestCategories.KEY_ID, key);
            categoriesValues.put(DBContract.TestCategories.KEY_PARENT_ID, "null");
            categoriesValues.put(DBContract.TestCategories.KEY_NAME, name);
            categoriesValues.put(DBContract.TestCategories.KEY_POSITION, 1);
            categoriesValues.put(DBContract.TestCategories.KEY_UPDATED, "2014-05-15");
            categoriesValues.put(DBContract.TestCategories.KEY_UPLOADED, "2014-05-15");
            key += 1;
            db.insert(DBContract.TestCategories.TABLE_NAME, null, categoriesValues);
        }
        int categoriesEnd = key;

//        TestCategories
//        ContentValues testCategoriesValues = new ContentValues();
//        testCategoriesValues.put(DBContract.TestCategories.KEY_ID, 6);
//        testCategoriesValues.put(DBContract.TestCategories.KEY_PARENT_ID, "null");
//        testCategoriesValues.put(DBContract.TestCategories.KEY_NAME, "Test");
//        testCategoriesValues.put(DBContract.TestCategories.KEY_POSITION, 1);
//        testCategoriesValues.put(DBContract.TestCategories.KEY_UPDATED, "2014-05-15");
//        testCategoriesValues.put(DBContract.TestCategories.KEY_UPLOADED, "2014-05-15");

        int testsStart = key;
//        Tests
        List<String> testNames = new ArrayList<>();
        testNames.add("We Are The Champions");
        testNames.add("The Show Must Go On");
        testNames.add("We Will Rock You");
        testNames.add("Killer Queen");
        testNames.add("Don't Stop Me Now");
        testNames.add("I'm Going Slightly Mad");
        testNames.add("Seven Seas of Rhye");
        testNames.add("I Want to Break Free");
        for (String name : testNames) {
            ContentValues testValues = new ContentValues();
            testValues.put(DBContract.Tests.KEY_ID, key);
            testValues.put(DBContract.Tests.KEY_CATEGORY_ID, random.nextInt(categoriesEnd
                    - categoriesStart + 1) + categoriesStart);
            testValues.put(DBContract.Tests.KEY_NAME, name);
            testValues.put(DBContract.Tests.KEY_DESCRIPTION, "Something");
            testValues.put(DBContract.Tests.KEY_UNITS, "Unitz");
            testValues.put(DBContract.Tests.KEY_DECIMALS, 2);
            testValues.put(DBContract.Tests.KEY_WEIGHT, 3);
            testValues.put(DBContract.Tests.KEY_FORMULA_F, "2");
            testValues.put(DBContract.Tests.KEY_FORMULA_M, "3");
            testValues.put(DBContract.Tests.KEY_POSITION, 0);
            testValues.put(DBContract.Tests.KEY_UPDATED, "2014-05-01");
            testValues.put(DBContract.Tests.KEY_UPLOADED, "2014-05-01");
            key += 1;
            db.insert(DBContract.Tests.TABLE_NAME, null, testValues);

        }
        int testsEnd = key;

        i = 0;
//        AppraisalTests
        while (i != 40) {
            ContentValues appraisalTestsValues = new ContentValues();
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_APPRAISAL_ID,
                    random.nextInt(appraisalEnd - appraisalStart + 1) + appraisalStart);
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_TEST_ID,
                    random.nextInt(testsEnd - testsStart + 1) + testsStart);
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_SCORE, 20);
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_NOTE, "Hello");
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_TRIAL_1, 2);
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_TRIAL_2, 3);
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_TRIAL_3, 20);
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_UPDATED, "2014-05-15");
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_UPLOADED, "2014-05-16");
            i += 1;
            db.insert(DBContract.AppraisalTests.TABLE_NAME, null, appraisalTestsValues);
        }

        //        AppraisalTests
//        ContentValues appraisalTestsValues = new ContentValues();
//        appraisalTestsValues.put(DBContract.AppraisalTests.KEY_APPRAISAL_ID, 4);
//        appraisalTestsValues.put(DBContract.AppraisalTests.KEY_TEST_ID, 7);
//        appraisalTestsValues.put(DBContract.AppraisalTests.KEY_SCORE, 20);
//        appraisalTestsValues.put(DBContract.AppraisalTests.KEY_NOTE, "Hello");
//        appraisalTestsValues.put(DBContract.AppraisalTests.KEY_TRIAL_1, 2);
//        appraisalTestsValues.put(DBContract.AppraisalTests.KEY_TRIAL_2, 3);
//        appraisalTestsValues.put(DBContract.AppraisalTests.KEY_TRIAL_3, 20);
//        appraisalTestsValues.put(DBContract.AppraisalTests.KEY_UPDATED, "2014-05-15");
//        appraisalTestsValues.put(DBContract.AppraisalTests.KEY_UPLOADED, "2014-05-16");

//        Tests
//        ContentValues testValues = new ContentValues();
//        testValues.put(DBContract.Tests.KEY_ID, 7);
//        testValues.put(DBContract.Tests.KEY_CATEGORY_ID, 6);
//        testValues.put(DBContract.Tests.KEY_NAME, "Skin");
//        testValues.put(DBContract.Tests.KEY_DESCRIPTION, "For some stuff");
//        testValues.put(DBContract.Tests.KEY_UNITS, "meters");
//        testValues.put(DBContract.Tests.KEY_DECIMALS, 2);
//        testValues.put(DBContract.Tests.KEY_WEIGHT, 3);
//        testValues.put(DBContract.Tests.KEY_FORMULA_F, "2");
//        testValues.put(DBContract.Tests.KEY_FORMULA_M, "3");
//        testValues.put(DBContract.Tests.KEY_POSITION, 0);
//        testValues.put(DBContract.Tests.KEY_UPDATED, "2014-05-01");
//        testValues.put(DBContract.Tests.KEY_UPLOADED, "2014-05-01");

//        Ratings
//        ContentValues ratingValues = new ContentValues();
//        ratingValues.put(DBContract.Ratings.KEY_TEST_ID, 7);
//        ratingValues.put(DBContract.Ratings.KEY_LABEL_ID, 5);
//        ratingValues.put(DBContract.Ratings.KEY_AGE, 25);
//        ratingValues.put(DBContract.Ratings.KEY_NORM_F, 10);
//        ratingValues.put(DBContract.Ratings.KEY_NORM_M, 20);
//        ratingValues.put(DBContract.Ratings.KEY_UPDATED, "2014-05-10");
//        ratingValues.put(DBContract.Ratings.KEY_UPLOADED, "2014-05-11");

//        Presets
//        ContentValues presetValues = new ContentValues();
//        presetValues.put(DBContract.Presets.KEY_ID, 8);
//        presetValues.put(DBContract.Presets.KEY_NAME, "PresetProovimiseks");
//        presetValues.put(DBContract.Presets.KEY_UPDATED, "2014-05-12");
//        presetValues.put(DBContract.Presets.KEY_UPLOADED, "2014-05-13");

//        PresetTests
//        ContentValues presetTestsValues = new ContentValues();
//        presetTestsValues.put(DBContract.PresetTests.KEY_TEST_ID, 11);
//        presetTestsValues.put(DBContract.PresetTests.KEY_PRESET_ID, 8);
//        presetTestsValues.put(DBContract.PresetTests.KEY_UPDATED, "2014-05-05");
//        presetTestsValues.put(DBContract.PresetTests.KEY_UPLOADED, "2014-05-05");

//        Adding to the database
//        db.insert(DBContract.Groups.TABLE_NAME, null, groupsValues);
//        db.insert(DBContract.Clients.TABLE_NAME, null, clientsValues);
//        db.insert(DBContract.Appraisers.TABLE_NAME, null, appraisersValues);
//        db.insert(DBContract.RatingLabels.TABLE_NAME, null, ratingLabelsValues);
//        db.insert(DBContract.TestCategories.TABLE_NAME, null, testCategoriesValues);
//        db.insert(DBContract.Tests.TABLE_NAME, null, testValues);
//        db.insert(DBContract.Ratings.TABEL_NAME, null, ratingValues);
//        db.insert(DBContract.Presets.TABLE_NAME, null, presetValues);
//        db.insert(DBContract.PresetTests.TABLE_NAME, null, presetTestsValues);
//        db.insert(DBContract.AppraisalTests.TABLE_NAME, null, appraisalTestsValues);
//        db.insert(DBContract.Appraisals.TABLE_NAME,null,appraisalsValues);
    }

    public void dropTables(SQLiteDatabase db) {
        db.execSQL(DBContract.Appraisals.DELETE_TABLE);
        db.execSQL(DBContract.AppraisalTests.DELETE_TABLE);
        db.execSQL(DBContract.Appraisers.DELETE_TABLE);
        db.execSQL(DBContract.Clients.DELETE_TABLE);
        db.execSQL(DBContract.Groups.DELETE_TABLE);
        db.execSQL(DBContract.Presets.DELETE_TABLE);
        db.execSQL(DBContract.PresetTests.DELETE_TABLE);
        db.execSQL(DBContract.RatingLabels.DELETE_TABLE);
        db.execSQL(DBContract.Ratings.DELETE_TABLE);
        db.execSQL(DBContract.TestCategories.DELETE_TABLE);
        db.execSQL(DBContract.Tests.DELETE_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion > 1) {
            dropTables(db);
            onCreate(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        switch(oldVersion) {
//            case 1:
//                ContentValues appraisalsValues2 = new ContentValues();
//                appraisalsValues2.put(DBContract.Appraisals.KEY_ID, 5);
//                appraisalsValues2.put(DBContract.Appraisals.KEY_APPRAISER_ID, 3);
//                appraisalsValues2.put(DBContract.Appraisals.KEY_CLIENT_ID, 2);
//                appraisalsValues2.put(DBContract.Appraisals.KEY_DATE, "2014-05-13");
//                appraisalsValues2.put(DBContract.Appraisals.KEY_UPDATED, "2014-05-13");
//                appraisalsValues2.put(DBContract.Appraisals.KEY_UPLOADED, "2014-05-14");
//                ContentValues groupsValues1 = new ContentValues();
//                groupsValues1.put(DBContract.Groups.KEY_ID, 2);
//                groupsValues1.put(DBContract.Groups.KEY_NAME, "Tartu musklid");
//                groupsValues1.put(DBContract.Groups.KEY_UPDATED, "2014-05-10");
//                groupsValues1.put(DBContract.Groups.KEY_UPLOADED, "2014-05-11");
//                ContentValues clientsValues1 = new ContentValues();
//                clientsValues1.put(DBContract.Clients.KEY_ID, 4);
//                clientsValues1.put(DBContract.Clients.KEY_FIRSTNAME, "Karl");
//                clientsValues1.put(DBContract.Clients.KEY_LASTNAME, "Lubja");
//                clientsValues1.put(DBContract.Clients.KEY_GROUP_ID, 2);
//                clientsValues1.put(DBContract.Clients.KEY_EMAIL, "klubja.@gmail.com");
//                clientsValues1.put(DBContract.Clients.KEY_GENDER, 1);
//                clientsValues1.put(DBContract.Clients.KEY_BIRTHDATE, "1995-05-10");
//                clientsValues1.put(DBContract.Clients.KEY_UPDATED, "2014-05-12");
//                clientsValues1.put(DBContract.Clients.KEY_UPLOADED, "2014-05-13");
//                ContentValues testCategoriesValues2 = new ContentValues();
//                testCategoriesValues2.put(DBContract.TestCategories.KEY_ID, 10);
//                testCategoriesValues2.put(DBContract.TestCategories.KEY_PARENT_ID, "null");
//                testCategoriesValues2.put(DBContract.TestCategories.KEY_NAME, "Test");
//                testCategoriesValues2.put(DBContract.TestCategories.KEY_POSITION, 2);
//                testCategoriesValues2.put(DBContract.TestCategories.KEY_UPDATED, "2014-05-15");
//                testCategoriesValues2.put(DBContract.TestCategories.KEY_UPLOADED, "2014-05-15");
//                ContentValues testValues2 = new ContentValues();
//                testValues2.put(DBContract.Tests.KEY_ID, 9);
//                testValues2.put(DBContract.Tests.KEY_CATEGORY_ID, 7);
//                testValues2.put(DBContract.Tests.KEY_NAME, "Skin");
//                testValues2.put(DBContract.Tests.KEY_DESCRIPTION, "For some stuff");
//                testValues2.put(DBContract.Tests.KEY_UNITS, "meters");
//                testValues2.put(DBContract.Tests.KEY_DECIMALS, 2);
//                testValues2.put(DBContract.Tests.KEY_WEIGHT, 3);
//                testValues2.put(DBContract.Tests.KEY_FORMULA_F, "2");
//                testValues2.put(DBContract.Tests.KEY_FORMULA_M, "3");
//                testValues2.put(DBContract.Tests.KEY_POSITION, 0);
//                testValues2.put(DBContract.Tests.KEY_UPDATED, "2014-05-01");
//                testValues2.put(DBContract.Tests.KEY_UPLOADED, "2014-05-01");
//                ContentValues appraisalTestsValues2 = new ContentValues();
//                appraisalTestsValues2.put(DBContract.AppraisalTests.KEY_APPRAISAL_ID, 5);
//                appraisalTestsValues2.put(DBContract.AppraisalTests.KEY_TEST_ID, 9);
//                appraisalTestsValues2.put(DBContract.AppraisalTests.KEY_SCORE, 20);
//                appraisalTestsValues2.put(DBContract.AppraisalTests.KEY_NOTE, "Hello");
//                appraisalTestsValues2.put(DBContract.AppraisalTests.KEY_TRIAL_1, 2);
//                appraisalTestsValues2.put(DBContract.AppraisalTests.KEY_TRIAL_2, 3);
//                appraisalTestsValues2.put(DBContract.AppraisalTests.KEY_TRIAL_3, 20);
//                appraisalTestsValues2.put(DBContract.AppraisalTests.KEY_UPDATED, "2014-05-15");
//                appraisalTestsValues2.put(DBContract.AppraisalTests.KEY_UPLOADED, "2014-05-16");
//                db.insert(DBContract.Groups.TABLE_NAME, null, groupsValues1);
//                db.insert(DBContract.Clients.TABLE_NAME, null, clientsValues1);
//                db.insert(DBContract.TestCategories.TABLE_NAME, null, testCategoriesValues2);
//                db.insert(DBContract.Tests.TABLE_NAME, null, testValues2);
//                db.insert(DBContract.AppraisalTests.TABLE_NAME, null, appraisalTestsValues2);
//        }
    }
}
