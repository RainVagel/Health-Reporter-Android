package rainvagel.healthreporter;

import android.media.Rating;
import android.provider.BaseColumns;

/**
 * Created by rainvagel on 25.09.16.
 */
public final class DBContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HealthReporterDB";

    private DBContract() {}

    public static abstract class Appraisers implements BaseColumns {
        private Appraisers(){}
        public static final String TABLE_NAME = "appraisers";
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " BLOB PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Groups implements BaseColumns {
        private Groups() {}
        public static final String TABLE_NAME = "groups";
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " BLOB PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOY NULL"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Clients implements BaseColumns {
        private Clients() {}
        public static final String TABLE_NAME = "clients";
        public static final String KEY_ID = "id";
        public static final String KEY_FIRSTNAME = "firstName";
        public static final String KEY_LASTNAME = "lastName";
        public static final String KEY_GROUP_ID = "groupId";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_GENDER = "gender";
        public static final String KEY_BIRTHDATE = "birthDate";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " BLOB PRIMARY KEY,"
                + KEY_FIRSTNAME + " TEXT,"
                + KEY_LASTNAME + " TEXT,"
                + KEY_GROUP_ID + " BLOB NOT NULL,"
                + KEY_EMAIL + " TEXT,"
                + KEY_GENDER + " TINYINT NOT NULL,"
                + KEY_BIRTHDATE + " DATE NOT NULL,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL," +
                "FOREIGN KEY (" + KEY_GROUP_ID + ") REFERENCES " + Groups.TABLE_NAME + " ("
                + Groups.KEY_ID + ")"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Appraisals implements BaseColumns {
        private Appraisals() {}
        public static final String TABLE_NAME = "appraisals";
        public static final String KEY_ID = "id";
        public static final String KEY_APPRAISER_ID = "appraiserId";
        public static final String KEY_CLIENT_ID = "clientId";
        public static final String KEY_DATE = "date";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " BLOB PRIMARY KEY,"
                + KEY_APPRAISER_ID + " BLOB NOT NULL,"
                + KEY_CLIENT_ID + " BLOB NOT NULL,"
                + KEY_DATE + " DATE NOT NULL,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL,"
                + "FOREIGN KEY (" + KEY_APPRAISER_ID + ") REFERENCES " + Appraisers.TABLE_NAME
                + " (" + Appraisers.KEY_ID + "),"
                + "FOREIGN KEY (" + KEY_CLIENT_ID + ") REFERENCES " + Clients.TABLE_NAME
                + " (" + Clients.KEY_ID + ")"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Presets implements BaseColumns {
        private Presets() {}
        public static final String TABLE_NAME = "presets";
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " BLOB PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class RatingLabels implements BaseColumns {
        private RatingLabels() {}
        public static final String TABLE_NAME = "rating_labels";
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_INTERPRETATION = "interpretation";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " BLOB PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_INTERPRETATION + " TEXT,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class TestCategories implements BaseColumns {
        private TestCategories() {}
        public static final String TABLE_NAME = "test_categories";
        public static final String KEY_ID = "id";
        public static final String KEY_PARENT_ID = "parentId";
        public static final String KEY_NAME = "name";
        public static final String KEY_POSITION = "position";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " BLOB PRIMARY KEY,"
                + KEY_PARENT_ID + " BLOB NOT NULL,"
                + KEY_NAME + " TEXT,"
                + KEY_POSITION + " INTEGER,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL,"
                + "FOREIGN KEY (" + KEY_PARENT_ID + ") REFERENCES " + TestCategories.TABLE_NAME
                + "(" + TestCategories.KEY_ID + ")"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Tests implements BaseColumns {
        private Tests() {}
        public static final String TABLE_NAME = "tests";
        public static final String KEY_ID = "id";
        public static final String KEY_CATEGORY_ID = "categoryId";
        public static final String KEY_NAME = "name";
        public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_UNITS = "units";
        public static final String KEY_DECIMALS = "decimals";
        public static final String KEY_WEIGHT = "weight";
        public static final String KEY_FORMULA_F = "formulaF";
        public static final String KEY_FORMULA_M = "formulaM";
        public static final String KEY_POSITION = "position";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_ID + " BLOB PRIMARY KEY,"
                + KEY_CATEGORY_ID + " BLOB NOT NULL,"
                + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_UNITS + " TEXT,"
                + KEY_DECIMALS + " INTEGER,"
                + KEY_WEIGHT + " REAL,"
                + KEY_FORMULA_F + " TEXT,"
                + KEY_FORMULA_M + " TEXT,"
                + KEY_POSITION + " INTEGER,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL,"
                + "FOREIGN KEY (" + KEY_CATEGORY_ID + ") REFERENCES " + TestCategories.TABLE_NAME
                + "( " + TestCategories.KEY_ID + ")"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class PresetTests implements BaseColumns {
        private PresetTests() {}
        public static final String TABLE_NAME = "preset_tests";
        public static final String KEY_TEST_ID = "testId";
        public static final String KEY_PRESET_ID = "presetId";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_TEST_ID + " BLOB NOT NULL,"
                + KEY_PRESET_ID + " BLOB NOT NULL,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL,"
                +  "FOREIGN KEY (" + KEY_TEST_ID + ") REFERENCES " + Tests.TABLE_NAME
                + " (" + Tests.KEY_ID + "),"
                + "FOREIGN KEY (" + KEY_PRESET_ID + ") REFERENCES " + Presets.TABLE_NAME
                + " (" + Presets.KEY_ID + ")"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Ratings implements BaseColumns {
        private Ratings() {}
        public static final String TABEL_NAME = "ratings";
        public static final String KEY_TEST_ID = "testId";
        public static final String KEY_LABEL_ID = "labelId";
        public static final String KEY_AGE = "age";
        public static final String KEY_NORM_F = "normF";
        public static final String KEY_NORM_M = "normM";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABEL_NAME + " ("
                + KEY_TEST_ID + " BLOB NOT NULL,"
                + KEY_LABEL_ID + " BLOB NOT NULL,"
                + KEY_AGE + " INTEGER NOT NULL,"
                + KEY_NORM_F + " REAL,"
                + KEY_NORM_M + " REAL,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL,"
                + "FOREIGN KEY (" + KEY_TEST_ID + ") REFERENCES " + Tests.TABLE_NAME
                + " (" + Tests.KEY_ID + "),"
                +  "FOREIGN KEY (" + KEY_LABEL_ID + ") REFERENCES " + RatingLabels.TABLE_NAME
                + " (" + RatingLabels.KEY_ID + ")"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABEL_NAME;
    }

    public static abstract class AppraisalTests implements BaseColumns {
        private AppraisalTests() {}
        public static final String TABLE_NAME = "appraisal_tests";
        public static final String KEY_APPRAISAL_ID = "appraisalId";
        public static final String KEY_TEST_ID = "testId";
        public static final String KEY_SCORE = "score";
        public static final String KEY_NOTE = "note";
        public static final String KEY_TRIAL_1 = "trial1";
        public static final String KEY_TRIAL_2 = "trial2";
        public static final String KEY_TRIAL_3 = "trial3";
        public static final String KEY_UPDATED = "updated";
        public static final String KEY_UPLOADED = "uploaded";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + KEY_APPRAISAL_ID + " BLOB NOT NULL,"
                + KEY_TEST_ID + " BLOB NOT NULL,"
                + KEY_SCORE + " REAL,"
                + KEY_NOTE + " TEXT,"
                + KEY_TRIAL_1 + " REAL,"
                + KEY_TRIAL_2 + " REAL,"
                + KEY_TRIAL_3 + " REAL,"
                + KEY_UPDATED + " DATETIME NOT NULL,"
                + KEY_UPLOADED + " DATETIME NOT NULL,"
                + "FOREIGN KEY (" + KEY_APPRAISAL_ID + ") REFERENCES " + Appraisals.TABLE_NAME
                + " (" + Appraisals.KEY_ID + "),"
                + "FOREIGN KEY (" + KEY_TEST_ID + ") REFERENCES " + Tests.TABLE_NAME
                + " (" + Tests.KEY_ID + ")"
                + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
