package rainvagel.healthreporter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rainvagel on 22.09.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    static final String dbName = "HealthReporterDB";

    static final String testsTable = "tests";
    static final String colId = "id";
    static final String colCategoryID = "categoryId";
    static final String colName = "name";
    static final String colDescription = "description";
    static final String colUnits = "units";
    static final String colDecimals = "decimals";
    static final String colWeight = "weight";
    static final String colFormulaF = "formulaF";
    static final String colFormulaM = "formulaM";
    static final String colPosition = "position";
    static final String colUpdated = "updated";
    static final String colUploaded = "uploaded";

    static final String ratingsTable = "ratings";
    static final String colTestId = "testId";
    static final String colLabelId = "labelId";
    static final String colAge = "age";
    static final String colNormF = "normF";
    static final String colNormM = "normM";
//    + colUpdated and colUploaded

    static final String ratingLabelsTable = "rating_labels";
//    + colName
    static final String colInterpretation = "interpretation";
//    colUpdated and colUploaded

    static final String presetTestsTable = "preset_tests";
//    + colTestId
    static final String colPresetId = "presetId";
//    + colUpdated and colUploaded

    static final String presetsTable = "presets";
//    +colId and colName and colUpdated and colUpgraded

    static final String testCategoriesTable = "test_categories";
//    + colId
    static final String colParenId = "parentId";
//    + colName and colPosition and colUpdated and colUploaded

    static final String apprisalTestsTable = "appraisal_tests";
    static final String colAppraisalId = "appraisalId";
//    + colTestId
    static final String colScore = "score";
    static final String colNote = "note";
    static final String colTrial1 = "trial1";
    static final String colTrial2 = "trial2";
    static final String colTrial3 = "trial3";
//    + colUpdated + colUploaded

    static final String appraisersTable = "appraisers";
//    +colId + colName + colUpdated + colUploaded

    static final String appraisalstTable = "appraisals";
    static final String colAppraiserId = "appraiserId";
    static final String colClientId = "clientId";
    static final String colDate = "date";
//    + colUpdated + colUploaded

    static final String clientsTable = "clients";
//    + colId
    static final String colFirstName = "firstName";
    static final String colLastName = "lastName";
    static final String colGroupId = "groupId";
    static final String colEmail = "email";
    static final String colGender = "gender";
    static final String colBirthDate = "birthDate";
//    + colUpdated + colUploaded

    static final String groupsTable = "groups";
//    +colName + colUpdated + colUploaded

    public DBHelper(Context context) {
        super(context, dbName, null, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
