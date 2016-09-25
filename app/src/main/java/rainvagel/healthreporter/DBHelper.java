package rainvagel.healthreporter;

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
        db.execSQL(DBContract.Clients.CREATE_TABEL);
        db.execSQL(DBContract.Appraisers.CREATE_TABLE);
        db.execSQL(DBContract.Appraisals.CREATE_TABLE);
        db.execSQL(DBContract.TestCategories.CREATE_TABLE);
        db.execSQL(DBContract.Tests.CREATE_TABEL);
        db.execSQL(DBContract.RatingLabels.CREATE_TABLE);
        db.execSQL(DBContract.Ratings.CREATE_TABLE);
        db.execSQL(DBContract.Presets.CREATE_TABLE);
        db.execSQL(DBContract.PresetTests.CREATE_TABLE);
        db.execSQL(DBContract.AppraisalTests.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO
    }
}
