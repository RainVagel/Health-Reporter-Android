package rainvagel.healthreporter.DBClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rainvagel on 23.11.16.
 */

public class DBQueries {

    public void insertGroupToDB(Context context, String groupNameString, String formattedDate){
        DBHelper dbHelper = new DBHelper(context);
        String uuid = UUID.randomUUID().toString();
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.Groups.KEY_ID, uuid);
        contentValues.put(DBContract.Groups.KEY_NAME,groupNameString);
        contentValues.put(DBContract.Groups.KEY_UPDATED,formattedDate);
        contentValues.put(DBContract.Groups.KEY_UPLOADED,"0000-00-00");
        sqLiteDatabase.insert(DBContract.Groups.TABLE_NAME,null,contentValues);

        sqLiteDatabase.close();
        dbHelper.close();
    }

    public String insertClientToDB(Context context, String group, String firstNameString, String lastNameString,
                                 String emailString, int gender, String birthYear, String birthMonth,
                                 String birthDay, String formattedDate) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        String uuid = UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(DBContract.Clients.KEY_ID, uuid);
        values.put(DBContract.Clients.KEY_GROUP_ID, group);
        values.put(DBContract.Clients.KEY_FIRSTNAME, firstNameString);
        values.put(DBContract.Clients.KEY_LASTNAME, lastNameString);
        values.put(DBContract.Clients.KEY_EMAIL, emailString);
        values.put(DBContract.Clients.KEY_GENDER, gender);
        values.put(DBContract.Clients.KEY_BIRTHDATE, birthYear + "-" + birthMonth + "-" + birthDay);
        values.put(DBContract.Clients.KEY_UPDATED, formattedDate);
        values.put(DBContract.Clients.KEY_UPLOADED, "0000-00-00");
        sqLiteDatabase.insert(DBContract.Clients.TABLE_NAME, null, values);

        sqLiteDatabase.close();
        dbHelper.close();

        return uuid;
    }

    public void editClientInDB(Context context, String clientId, String firstName, String lastName,
                               String clientBirthDate, String email, String clientGender,
                               String clientGroupId, String clientUpdated) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBContract.Clients.KEY_FIRSTNAME, firstName);
        contentValues.put(DBContract.Clients.KEY_LASTNAME, lastName);
        contentValues.put(DBContract.Clients.KEY_BIRTHDATE, clientBirthDate);
        contentValues.put(DBContract.Clients.KEY_EMAIL, email);
        contentValues.put(DBContract.Clients.KEY_GENDER, clientGender);
        contentValues.put(DBContract.Clients.KEY_GROUP_ID, clientGroupId);
        contentValues.put(DBContract.Clients.KEY_UPDATED, clientUpdated);
        sqLiteDatabase.update(DBContract.Clients.TABLE_NAME, contentValues,
                DBContract.Clients.KEY_ID + "=" + clientId, null);
        sqLiteDatabase.close();
        dbHelper.close();
    }

    public void insertCategoryToDB(Context context, String parentID, String name, int position,
                                   String currentDate) {
        DBHelper mydb = new DBHelper(context);

        SQLiteDatabase db = mydb.getWritableDatabase();

        String uuid = UUID.randomUUID().toString();

        ContentValues values = new ContentValues();
        values.put(DBContract.TestCategories.KEY_ID, uuid);
        values.put(DBContract.TestCategories.KEY_PARENT_ID, parentID);
        values.put(DBContract.TestCategories.KEY_NAME, name);
        values.put(DBContract.TestCategories.KEY_POSITION, position);
        values.put(DBContract.TestCategories.KEY_UPDATED, currentDate);
        values.put(DBContract.TestCategories.KEY_UPLOADED, "null");

        db.insert(DBContract.TestCategories.TABLE_NAME, null, values);
        db.close();
        mydb.close();
    }

    public DBTransporter getGroupsFromDB(Context context) {
        ArrayList<String> groupID = new ArrayList<>();
        Map<String, String> groups = new HashMap<>();
        Map<String, String> groupsReversed = new HashMap<>();
        ArrayList<String> groupNames = new ArrayList<>();

        DBHelper mydb = new DBHelper(context);
        String[] columns = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME};
        Cursor cursor = mydb.getReadableDatabase().query(DBContract.Groups.TABLE_NAME,
                columns, null, null, null, null, null);
        int idIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String groupId = cursor.getString(idIndex);
            groupID.add(groupId);
            groups.put(groupId, cursor.getString(nameIndex));
            groupsReversed.put(cursor.getString(nameIndex), groupId);
            groupNames.add(cursor.getString(nameIndex));
        }
        cursor.close();
        mydb.close();

        return new DBTransporter(groupID, null, groups, groupsReversed, null, groupNames);
    }

    public ArrayList<String> getClientDetailsFromDB(Context context, String clientID) {
        ArrayList<String> details = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME,
                DBContract.Clients.KEY_GROUP_ID, DBContract.Clients.KEY_EMAIL, DBContract.Clients.KEY_BIRTHDATE,
                DBContract.Clients.KEY_GENDER, DBContract.Clients.KEY_UPDATED};
        Cursor cursor = dbHelper.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns,
                null, null, null, null, null);

        int clientIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int genderIndex = cursor.getColumnIndex(DBContract.Clients.KEY_GENDER);
        int groupIndex = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int emailIndex = cursor.getColumnIndex(DBContract.Clients.KEY_EMAIL);
        int birthDateIndex = cursor.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);
        int updatedIndex = cursor.getColumnIndex(DBContract.Clients.KEY_UPDATED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (clientID.equals(cursor.getString(clientIndex))) {
                details.add(0, cursor.getString(firstNameIndex));
                details.add(1, cursor.getString(lastNameIndex));
                details.add(2, cursor.getString(birthDateIndex));
                details.add(3, cursor.getString(genderIndex));
                details.add(4, cursor.getString(groupIndex));
                details.add(5, cursor.getString(emailIndex));
                details.add(6, cursor.getString(updatedIndex));
            }
        }
        cursor.close();
        dbHelper.close();
        return details;
    }

    public ArrayList<String> getGroupDetailsFromDB(Context context, String groupID) {
        ArrayList<String> details = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        String[] columnsGroups = {DBContract.Groups.KEY_ID, DBContract.Groups.KEY_NAME,
                DBContract.Groups.KEY_UPDATED};
        Cursor cursor = dbHelper.getReadableDatabase().query(DBContract.Groups.TABLE_NAME, columnsGroups,
                null, null, null, null, null);
        int groupIndex = cursor.getColumnIndex(DBContract.Groups.KEY_ID);
        int groupNameIndex = cursor.getColumnIndex(DBContract.Groups.KEY_NAME);
        int groupUpdatedIndex = cursor.getColumnIndex(DBContract.Groups.KEY_UPDATED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (groupID.equals(cursor.getString(groupIndex))) {
                details.add(0, cursor.getString(groupNameIndex));
                details.add(1, cursor.getString(groupUpdatedIndex));
            }
        }
        cursor.close();
        dbHelper.close();
        return details;
    }

    public DBTransporter getClientToGroupFromDB(Context context) {
        DBHelper mydb = new DBHelper(context);

        ArrayList<String> clientIDs = new ArrayList<>();
        ArrayList<String> groupIDs = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        Map<String, String> clientIdGroupId = new HashMap<>();
        Map<String, String> namesGroupKeys = new HashMap<>();
        Map<String, String> namesClientKeys = new HashMap<>();

        String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME,
                DBContract.Clients.KEY_LASTNAME, DBContract.Clients.KEY_GROUP_ID};
        Cursor cursor = mydb.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns,
                null,null,null,null,null);

        int rowIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        final int groupIndex  = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);

        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            //Log.v("ClientActivity", "Made it here");
            clientIDs.add(cursor.getString(rowIndex));
            groupIDs.add(cursor.getString(groupIndex));
            //groupids contains exact amount of groupids as clientids
            names.add(cursor.getString(firstNameIndex)+ " " + cursor.getString(lastNameIndex));

            clientIdGroupId.put(cursor.getString(rowIndex),cursor.getString(groupIndex));

            namesGroupKeys.put(cursor.getString(firstNameIndex)+ " " + cursor.getString(lastNameIndex),
                    (cursor.getString(groupIndex)));

            namesClientKeys.put(cursor.getString(firstNameIndex)+ " "+ cursor.getString(lastNameIndex),
                    (cursor.getString(rowIndex)));
        }

        cursor.close();
        mydb.close();
        return new DBTransporter(groupIDs, clientIDs, namesClientKeys, namesGroupKeys,
                clientIdGroupId, names);
    }

    public void deleteEntryFromDB(Context context, String table, String field, String id) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.delete(table, field + "=" + id, null);
        sqLiteDatabase.close();
        dbHelper.close();
    }

    public DBClientsTransporter getClientsFromDB(Context context) {
        ArrayList<String> clientID = new ArrayList<>();
        Map<String, String> clientIdToFirstName = new HashMap<>();
        Map<String, String> clientIdToLastName = new HashMap<>();
        Map<String, String> clientIdToEmail = new HashMap<>();
        Map<String, String> clientIdToGender = new HashMap<>();
        Map<String, String> clientIdToGroupId = new HashMap<>();
        Map<String, String> clientIdToBirthDate = new HashMap<>();
        Map<String, String> clientIdToUpdated = new HashMap<>();

        DBHelper dbHelper = new DBHelper(context);
        String[] columns = {DBContract.Clients.KEY_ID, DBContract.Clients.KEY_FIRSTNAME, DBContract.Clients.KEY_LASTNAME,
                DBContract.Clients.KEY_GROUP_ID, DBContract.Clients.KEY_EMAIL, DBContract.Clients.KEY_BIRTHDATE,
                DBContract.Clients.KEY_GENDER, DBContract.Clients.KEY_UPDATED};
        Cursor cursor = dbHelper.getReadableDatabase().query(DBContract.Clients.TABLE_NAME, columns,
                null, null, null, null, null);

        String clientIDWorkable;
        int clientIndex = cursor.getColumnIndex(DBContract.Clients.KEY_ID);
        int firstNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_FIRSTNAME);
        int lastNameIndex = cursor.getColumnIndex(DBContract.Clients.KEY_LASTNAME);
        int genderIndex = cursor.getColumnIndex(DBContract.Clients.KEY_GENDER);
        int groupIndex = cursor.getColumnIndex(DBContract.Clients.KEY_GROUP_ID);
        int emailIndex = cursor.getColumnIndex(DBContract.Clients.KEY_EMAIL);
        int birthDateIndex = cursor.getColumnIndex(DBContract.Clients.KEY_BIRTHDATE);
        int updatedIndex = cursor.getColumnIndex(DBContract.Clients.KEY_UPDATED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            clientIDWorkable = cursor.getString(clientIndex);
            clientID.add(clientIDWorkable);
            clientIdToFirstName.put(clientIDWorkable, cursor.getString(firstNameIndex));
            clientIdToLastName.put(clientIDWorkable, cursor.getString(lastNameIndex));
            clientIdToGender.put(clientIDWorkable, cursor.getString(genderIndex));
            clientIdToGroupId.put(clientIDWorkable, cursor.getString(groupIndex));
            clientIdToEmail.put(clientIDWorkable, cursor.getString(emailIndex));
            clientIdToBirthDate.put(clientIDWorkable, cursor.getString(birthDateIndex));
            clientIdToUpdated.put(clientIDWorkable, cursor.getString(updatedIndex));
        }
        cursor.close();
        dbHelper.close();
        return new DBClientsTransporter(clientID,clientIdToFirstName,clientIdToLastName,clientIdToEmail,
                clientIdToGender, clientIdToGroupId, clientIdToBirthDate, clientIdToUpdated);
    }

    public DBAppraisalsTransporter getAppraisalsFromDB(Context context) {
        ArrayList<String> appraisalID = new ArrayList<>();
        Map<String, String> appraisalIdToAppraiserId = new HashMap<>();
        Map<String, String> appraisalIdToClientId = new HashMap<>();
        Map<String, String> appraisalIdToAppraisalDate = new HashMap<>();
        Map<String, String> appraisalIdToUpdated = new HashMap<>();
        Map<String, String> appraisalIdToUploaded = new HashMap<>();

        DBHelper dbHelper = new DBHelper(context);
        String[] columns = {DBContract.Appraisals.KEY_ID, DBContract.Appraisals.KEY_APPRAISER_ID,
                DBContract.Appraisals.KEY_CLIENT_ID, DBContract.Appraisals.KEY_DATE,
                DBContract.Appraisals.KEY_UPDATED, DBContract.Appraisals.KEY_UPLOADED};
        Cursor cursor = dbHelper.getReadableDatabase().query(DBContract.Appraisals.TABLE_NAME,
                columns, null, null, null, null, null);

        String appraisalIDWorkable;
        int appraisalIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_ID);
        int appraiserIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_APPRAISER_ID);
        int clientIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_CLIENT_ID);
        int dateIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_DATE);
        int updatedIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_UPDATED);
        int uploadedIndex = cursor.getColumnIndex(DBContract.Appraisals.KEY_UPLOADED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            appraisalIDWorkable = cursor.getString(appraisalIndex);
            appraisalID.add(appraisalIDWorkable);
            appraisalIdToAppraiserId.put(appraisalIDWorkable, cursor.getString(appraiserIndex));
            appraisalIdToClientId.put(appraisalIDWorkable, cursor.getString(clientIndex));
            appraisalIdToAppraisalDate.put(appraisalIDWorkable, cursor.getString(dateIndex));
            appraisalIdToUpdated.put(appraisalIDWorkable, cursor.getString(updatedIndex));
            appraisalIdToUploaded.put(appraisalIDWorkable, cursor.getString(uploadedIndex));
        }
        cursor.close();
        dbHelper.close();
        return new DBAppraisalsTransporter(appraisalID, appraisalIdToAppraiserId, appraisalIdToUpdated,
                appraisalIdToClientId, appraisalIdToAppraisalDate, appraisalIdToUploaded);
    }

    public DBAppraisalTestsTransporter getAppraisalTestsFromDB(Context context) {
        ArrayList<String> appraisalID = new ArrayList<>();
        ArrayList<String> testID = new ArrayList<>();
        Map<String, String> appraisalIdToTestId = new HashMap<>();
        Map<String, String> appraisalIdToTestScores = new HashMap<>();
        Map<String, String> appraisalIdToNote = new HashMap<>();
        Map<String, String> appraisalIdToTrial1 = new HashMap<>();
        Map<String, String> appraisalIdToTrial2 = new HashMap<>();
        Map<String, String> appraisalIdToTrial3 = new HashMap<>();
        Map<String, String> appraisalIdToUpdated = new HashMap<>();
        Map<String, String> appraisalIdToUploaded = new HashMap<>();

        DBHelper dbHelper = new DBHelper(context);
        String[] columns = {DBContract.AppraisalTests.KEY_APPRAISAL_ID, DBContract.AppraisalTests.KEY_TEST_ID,
                DBContract.AppraisalTests.KEY_SCORE, DBContract.AppraisalTests.KEY_NOTE,
                DBContract.AppraisalTests.KEY_TRIAL_1, DBContract.AppraisalTests.KEY_TRIAL_2,
                DBContract.AppraisalTests.KEY_TRIAL_3, DBContract.AppraisalTests.KEY_UPDATED,
                DBContract.AppraisalTests.KEY_UPLOADED};
        Cursor cursor = dbHelper.getReadableDatabase().query(DBContract.AppraisalTests.TABLE_NAME,
                columns, null, null, null, null, null);

        String appraisalIDWorkable;
        String testIDWorkable;
        int appraisalIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_APPRAISAL_ID);
        int testIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TEST_ID);
        int scoreIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_SCORE);
        int noteIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_NOTE);
        int trial1Index = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_1);
        int trial2Index = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_2);
        int trial3Index = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_TRIAL_3);
        int updatedIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_UPDATED);
        int uploadedIndex = cursor.getColumnIndex(DBContract.AppraisalTests.KEY_UPLOADED);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            appraisalIDWorkable = cursor.getString(appraisalIndex);
            testIDWorkable = cursor.getString(testIndex);
            appraisalID.add(appraisalIDWorkable);
            testID.add(testIDWorkable);
            appraisalIdToTestId.put(appraisalIDWorkable, testIDWorkable);
            appraisalIdToTestScores.put(appraisalIDWorkable, cursor.getString(scoreIndex));
            appraisalIdToNote.put(appraisalIDWorkable, cursor.getString(noteIndex));
            appraisalIdToTrial1.put(appraisalIDWorkable, cursor.getString(trial1Index));
            appraisalIdToTrial2.put(appraisalIDWorkable, cursor.getString(trial2Index));
            appraisalIdToTrial3.put(appraisalIDWorkable, cursor.getString(trial3Index));
            appraisalIdToUpdated.put(appraisalIDWorkable, cursor.getString(updatedIndex));
            appraisalIdToUploaded.put(appraisalIDWorkable, cursor.getString(uploadedIndex));
        }
        cursor.close();
        dbHelper.close();
        return new DBAppraisalTestsTransporter(appraisalID, testID, appraisalIdToTestId, appraisalIdToTestScores,
                appraisalIdToNote, appraisalIdToTrial1, appraisalIdToTrial2, appraisalIdToTrial3, appraisalIdToUpdated,
                appraisalIdToUploaded);
    }

    public DBCategoriesTransporter getCategoriesFromDB(Context context) {
        ArrayList<String> categoriesID = new ArrayList<>();
        Map<String, String> categoriesIdToParentId = new HashMap<>();
        Map<String, String> categoriesIdToName = new HashMap<>();
        Map<String, String> categoriesIdToPosition = new HashMap<>();
        Map<String, String> categoriesIdToUpdated = new HashMap<>();
        Map<String, String> categoriesIdToUploaded = new HashMap<>();

        DBHelper dbHelper = new DBHelper(context);
        String[] columns = {DBContract.TestCategories.KEY_ID, DBContract.TestCategories.KEY_PARENT_ID,
                DBContract.TestCategories.KEY_NAME, DBContract.TestCategories.KEY_POSITION,
                DBContract.TestCategories.KEY_UPDATED, DBContract.TestCategories.KEY_UPLOADED};
        Cursor cursor = dbHelper.getReadableDatabase().query(DBContract.TestCategories.TABLE_NAME,
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
        dbHelper.close();
        cursor.close();
        return new DBCategoriesTransporter(categoriesID, categoriesIdToParentId, categoriesIdToName,
                categoriesIdToPosition, categoriesIdToUpdated, categoriesIdToUploaded);
    }

    public DBTestsTransporter getTestsFromDB(Context context) {
        ArrayList<String> testID = new ArrayList<>();
        Map<String, String> testIdToCategoryId = new HashMap<>();
        Map<String, String> testIdToName = new HashMap<>();
        Map<String, String> testIdToDescription = new HashMap<>();
        Map<String, String> testIdToUnits = new HashMap<>();
        Map<String, String> testIdToDecimals = new HashMap<>();
        Map<String, String> testIdToWeight = new HashMap<>();
        Map<String, String> testIdToFormulaF = new HashMap<>();
        Map<String, String> testIdToFormulaM = new HashMap<>();
        Map<String, String> testIdToPosition = new HashMap<>();
        Map<String, String> testIdToUpdated = new HashMap<>();
        Map<String, String> testIdToUploaded = new HashMap<>();

        DBHelper dbHelper = new DBHelper(context);
        String[] columns = {DBContract.Tests.KEY_ID, DBContract.Tests.KEY_CATEGORY_ID, DBContract.Tests.KEY_NAME,
                DBContract.Tests.KEY_DESCRIPTION, DBContract.Tests.KEY_UNITS, DBContract.Tests.KEY_DECIMALS,
                DBContract.Tests.KEY_WEIGHT, DBContract.Tests.KEY_FORMULA_F, DBContract.Tests.KEY_FORMULA_M,
                DBContract.Tests.KEY_POSITION, DBContract.Tests.KEY_UPDATED, DBContract.Tests.KEY_UPLOADED};
        Cursor cursor = dbHelper.getReadableDatabase().query(DBContract.Tests.TABLE_NAME, columns,
                null, null, null, null, null);

        String testIDWorkable;
        int testIndex = cursor.getColumnIndex(DBContract.Tests.KEY_ID);
        int categoryIndex = cursor.getColumnIndex(DBContract.Tests.KEY_CATEGORY_ID);
        int nameIndex = cursor.getColumnIndex(DBContract.Tests.KEY_NAME);
        int descriptionIndex = cursor.getColumnIndex(DBContract.Tests.KEY_DESCRIPTION);
        int unitsIndex = cursor.getColumnIndex(DBContract.Tests.KEY_DECIMALS);
        int weightIndex = cursor.getColumnIndex(DBContract.Tests.KEY_WEIGHT);
        int formulaFIndex = cursor.getColumnIndex(DBContract.Tests.KEY_FORMULA_F);
        int formulaMIndex = cursor.getColumnIndex(DBContract.Tests.KEY_FORMULA_M);
        int positionIndex = cursor.getColumnIndex(DBContract.Tests.KEY_POSITION);
        int updatedIndex = cursor.getColumnIndex(DBContract.Tests.KEY_UPDATED);
        int uploadedIndex = cursor.getColumnIndex(DBContract.Tests.KEY_UPLOADED);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            testIDWorkable = cursor.getString(testIndex);
            testID.add(testIDWorkable);
            testIdToCategoryId.put(testIDWorkable, cursor.getString(categoryIndex));
            testIdToName.put(testIDWorkable, cursor.getString(nameIndex));
            testIdToDescription.put(testIDWorkable, cursor.getString(descriptionIndex));
            testIdToUnits.put(testIDWorkable, cursor.getString(unitsIndex));
            testIdToWeight.put(testIDWorkable, cursor.getString(weightIndex));
            testIdToFormulaF.put(testIDWorkable, cursor.getString(formulaFIndex));
            testIdToFormulaM.put(testIDWorkable, cursor.getString(formulaMIndex));
            testIdToPosition.put(testIDWorkable, cursor.getString(positionIndex));
            testIdToUpdated.put(testIDWorkable, cursor.getString(updatedIndex));
            testIdToUploaded.put(testIDWorkable, cursor.getString(uploadedIndex));
        }
        cursor.close();
        dbHelper.close();
        return new DBTestsTransporter(testID, testIdToCategoryId, testIdToName, testIdToDescription,
                testIdToUnits, testIdToDecimals, testIdToWeight, testIdToFormulaF, testIdToFormulaM,
                testIdToPosition, testIdToUpdated, testIdToUploaded);
    }
}
