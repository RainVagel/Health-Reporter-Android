package rainvagel.healthreporter.DBClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
        ArrayList<String> groupUUID = new ArrayList<>();
        for (String group : groupName) {
            String uuid = UUID.randomUUID().toString();
            groupUUID.add(uuid);
            ContentValues groupsValues = new ContentValues();
            groupsValues.put(DBContract.Groups.KEY_ID, uuid);
            groupsValues.put(DBContract.Groups.KEY_NAME, group);
            groupsValues.put(DBContract.Groups.KEY_UPDATED, "2014-05-10");
            groupsValues.put(DBContract.Groups.KEY_UPLOADED, "2014-05-11");
            db.insert(DBContract.Groups.TABLE_NAME, null, groupsValues);

        }

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
        ArrayList<String> clientsArray = new ArrayList<>();
        for (String firstName : clientNameFirst) {
            for (String lastName : clientNameLast) {
                String uuid = UUID.randomUUID().toString();
                clientsArray.add(uuid);
                ContentValues clientsValues = new ContentValues();
                clientsValues.put(DBContract.Clients.KEY_ID, uuid);
                clientsValues.put(DBContract.Clients.KEY_FIRSTNAME, firstName);
                clientsValues.put(DBContract.Clients.KEY_LASTNAME, lastName);
                clientsValues.put(DBContract.Clients.KEY_GROUP_ID, groupUUID.get(random.nextInt(groupUUID.size() - 1
                        - 0 + 1) + 0));
                clientsValues.put(DBContract.Clients.KEY_EMAIL, firstName + "." + lastName + "@gmail.com");
                clientsValues.put(DBContract.Clients.KEY_GENDER, random.nextInt(2-1+1)+1);
                clientsValues.put(DBContract.Clients.KEY_BIRTHDATE, "1995-05-10");
                clientsValues.put(DBContract.Clients.KEY_UPDATED, "2014-05-12");
                clientsValues.put(DBContract.Clients.KEY_UPLOADED, "2014-05-13");
                db.insert(DBContract.Clients.TABLE_NAME, null, clientsValues);
            }
        }

//        Appraisers
        ContentValues appraisersValues = new ContentValues();
        String appraiserUUID = UUID.randomUUID().toString();
        appraisersValues.put(DBContract.Appraisers.KEY_ID, appraiserUUID);
        appraisersValues.put(DBContract.Appraisers.KEY_NAME,"Karl Lubja");
        appraisersValues.put(DBContract.Appraisers.KEY_UPDATED, "2014-05-10");
        appraisersValues.put(DBContract.Appraisers.KEY_UPLOADED, "2014-05-09");
        db.insert(DBContract.Appraisers.TABLE_NAME, null, appraisersValues);


//        Appraisals
        int i = 0;
        ArrayList<String> appraisalsUUID = new ArrayList<>();
        while (i != 400) {
            String uuid = UUID.randomUUID().toString();
            ContentValues appraisalValues = new ContentValues();
            appraisalsUUID.add(uuid);
            appraisalValues.put(DBContract.Appraisals.KEY_ID, uuid);
            appraisalValues.put(DBContract.Appraisals.KEY_APPRAISER_ID, appraiserUUID);
            appraisalValues.put(DBContract.Appraisals.KEY_CLIENT_ID, clientsArray.get(random.nextInt(clientsArray.size() - 1
                    - 0 + 1) + 0));
            appraisalValues.put(DBContract.Appraisals.KEY_DATE, "2014-05-13");
            appraisalValues.put(DBContract.Appraisals.KEY_UPDATED, "2014-05-13");
            appraisalValues.put(DBContract.Appraisals.KEY_UPLOADED, "2014-05-14");
            db.insert(DBContract.Appraisals.TABLE_NAME,null,appraisalValues);
            i += 1;
        }

//        RatingLabels
        List<String> ratingLabelsName = new ArrayList<>();
        ratingLabelsName.add("Very bad");
        ratingLabelsName.add("Poor");
        ratingLabelsName.add("Fair");
        ratingLabelsName.add("Good");
        ratingLabelsName.add("Super");
        List<String> ratingLabelsUUID = new ArrayList<>();
        i = 0;
        while (i <= 50) {
            String uuid = UUID.randomUUID().toString();
            ratingLabelsUUID.add(uuid);
            ContentValues ratingLabelsValues = new ContentValues();
            ratingLabelsValues.put(DBContract.RatingLabels.KEY_ID, uuid);
            ratingLabelsValues.put(DBContract.RatingLabels.KEY_NAME, ratingLabelsName.get(
                    random.nextInt(4 - 1)));
            ratingLabelsValues.put(DBContract.RatingLabels.KEY_INTERPRETATION, "Something in here");
            ratingLabelsValues.put(DBContract.RatingLabels.KEY_UPDATED, "2014-05-13");
            ratingLabelsValues.put(DBContract.RatingLabels.KEY_UPLOADED, "2014-05-13");
            i += 1;
            db.insert(DBContract.RatingLabels.TABLE_NAME, null, ratingLabelsValues);
        }

//        TestCategories
        List<String> testCategoriesUUID = new ArrayList<>();
        List<String> categoriesNames = new ArrayList<>();
        categoriesNames.add("Insener");
        categoriesNames.add("Gagarini");
        categoriesNames.add("Hüperboloid");
        categoriesNames.add("Tühjad");
        categoriesNames.add("Pihud");
        for (String name : categoriesNames) {
            String uuid = UUID.randomUUID().toString();
            testCategoriesUUID.add(uuid);
            ContentValues categoriesValues = new ContentValues();
            categoriesValues.put(DBContract.TestCategories.KEY_ID, uuid);
            categoriesValues.put(DBContract.TestCategories.KEY_PARENT_ID, "null");
            categoriesValues.put(DBContract.TestCategories.KEY_NAME, name);
            categoriesValues.put(DBContract.TestCategories.KEY_POSITION, 1);
            categoriesValues.put(DBContract.TestCategories.KEY_UPDATED, "2014-05-15");
            categoriesValues.put(DBContract.TestCategories.KEY_UPLOADED, "2014-05-15");
            db.insert(DBContract.TestCategories.TABLE_NAME, null, categoriesValues);
        }

//        Tests
        List<String> testNames = new ArrayList<>();
        List<String> testsUUID = new ArrayList<>();
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
            String uuid = UUID.randomUUID().toString();
            testsUUID.add(uuid);
            testValues.put(DBContract.Tests.KEY_ID, uuid);
            testValues.put(DBContract.Tests.KEY_CATEGORY_ID, testCategoriesUUID.get(random.nextInt(testCategoriesUUID.size() - 1
                    - 0 + 1) + 0));
            testValues.put(DBContract.Tests.KEY_NAME, name);
            testValues.put(DBContract.Tests.KEY_DESCRIPTION, "Something");
            testValues.put(DBContract.Tests.KEY_UNITS, "2");
            testValues.put(DBContract.Tests.KEY_DECIMALS, 2);
            testValues.put(DBContract.Tests.KEY_WEIGHT, (random.nextDouble()));
            testValues.put(DBContract.Tests.KEY_FORMULA_F, "2");
            testValues.put(DBContract.Tests.KEY_FORMULA_M, "3");
            testValues.put(DBContract.Tests.KEY_POSITION, 0);
            testValues.put(DBContract.Tests.KEY_UPDATED, "2014-05-01");
            testValues.put(DBContract.Tests.KEY_UPLOADED, "2014-05-01");
            db.insert(DBContract.Tests.TABLE_NAME, null, testValues);

        }

        i = 0;
//        AppraisalTests
        List<String> appraisalTestsUUID = new ArrayList<>();
        while (i != 400) {
            String uuid = UUID.randomUUID().toString();
            appraisalTestsUUID.add(uuid);
            ContentValues appraisalTestsValues = new ContentValues();
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_APPRAISAL_ID,
                    appraisalsUUID.get(random.nextInt(appraisalTestsUUID.size() - 1 - 0 + 1) + 0));
            appraisalTestsValues.put(DBContract.AppraisalTests.KEY_TEST_ID,
                    testsUUID.get(random.nextInt(testsUUID.size() - 1 - 0 + 1) + 0));
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

//        Ratings
        List<Integer> ratingsAge = new ArrayList<>();
        List<Integer> ratingsNormF = new ArrayList<>();
        List<Integer> ratingsNormM = new ArrayList<>();
        ratingsAge.add(0);
        ratingsAge.add(10);
        ratingsAge.add(20);
        ratingsAge.add(30);
        ratingsAge.add(40);
        ratingsAge.add(50);
        ratingsAge.add(60);
        ratingsAge.add(70);
        ratingsNormF.add(20);
        ratingsNormF.add(30);
        ratingsNormF.add(100);
        ratingsNormF.add(10);
        ratingsNormF.add(15);
        ratingsNormF.add(25);
        ratingsNormF.add(35);
        ratingsNormF.add(70);
        ratingsNormM.add(10);
        ratingsNormM.add(25);
        ratingsNormM.add(50);
        ratingsNormM.add(75);
        ratingsNormM.add(100);
        ratingsNormM.add(30);
        ratingsNormM.add(15);
        ratingsNormM.add(88);
        for (String uuid : testsUUID) {
            ContentValues values = new ContentValues();
            values.put(DBContract.Ratings.KEY_TEST_ID,
                    uuid);
            values.put(DBContract.Ratings.KEY_LABEL_ID,
                    ratingLabelsUUID.get(random.nextInt(ratingLabelsUUID.size())));
            values.put(DBContract.Ratings.KEY_AGE,
                    ratingsAge.get(random.nextInt(ratingsAge.size())));
            values.put(DBContract.Ratings.KEY_NORM_F,
                    ratingsNormF.get(random.nextInt(ratingsNormF.size())));
            values.put(DBContract.Ratings.KEY_NORM_M,
                    ratingsNormM.get(random.nextInt(ratingsNormM.size())));
            values.put(DBContract.Ratings.KEY_UPDATED, "2014-05-15");
            values.put(DBContract.Ratings.KEY_UPLOADED, "2014-07-20");
            db.insert(DBContract.Ratings.TABEL_NAME, null, values);
        }

//        Presets
        List<String> presetNames = new ArrayList<>();
        List<String> presetUUID = new ArrayList<>();
        presetNames.add("Basic Physical");
        presetNames.add("Advanced Student Physical");
        presetNames.add("Masters Student Physical");
        presetNames.add("For Your Eyes Only");
        for (String name : presetNames) {
            String uuid = UUID.randomUUID().toString();
            presetUUID.add(uuid);
            ContentValues presetValues = new ContentValues();
            presetValues.put(DBContract.Presets.KEY_ID, uuid);
            presetValues.put(DBContract.Presets.KEY_NAME, name);
            presetValues.put(DBContract.Presets.KEY_UPDATED, "2014-05-12");
            presetValues.put(DBContract.Presets.KEY_UPLOADED, "2014-05-12");
            db.insert(DBContract.Presets.TABLE_NAME, null, presetValues);

        }

//        PresetTests
        i = 0;
        List<String> presetTestsUUID = new ArrayList<>();
        while (i <= 250) {
            String uuid = UUID.randomUUID().toString();
            presetTestsUUID.add(uuid);
            ContentValues presetTestsValues = new ContentValues();
            presetTestsValues.put(DBContract.PresetTests.KEY_TEST_ID, testsUUID.get(random.nextInt(testsUUID.size() - 1
                    - 0 + 1) + 0));
            presetTestsValues.put(DBContract.PresetTests.KEY_PRESET_ID, presetUUID.get(random.nextInt(
                    presetUUID.size() - 1 - 0 + 1) + 0));
            presetTestsValues.put(DBContract.PresetTests.KEY_UPDATED, "2014-05-12");
            presetTestsValues.put(DBContract.PresetTests.KEY_UPLOADED, "2014-05-12");
            db.insert(DBContract.PresetTests.TABLE_NAME, null, presetTestsValues);
            i += 1;
        }

//        Adding to the database
//        db.insert(DBContract.Ratings.TABEL_NAME, null, ratingValues);
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
//      TODO
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        TODO
    }
}
