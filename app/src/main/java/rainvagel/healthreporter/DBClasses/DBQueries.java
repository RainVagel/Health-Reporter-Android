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
        return new DBTransporter(groupIDs, clientIDs, namesClientKeys, namesGroupKeys,
                clientIdGroupId, names);
    }
}
