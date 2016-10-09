package rainvagel.healthreporter;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by rainvagel on 27.09.16.
 */
public class RainTestMain extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHelper helper = new DBHelper(this);
        Cursor res = helper.getReadableDatabase().rawQuery("select * from tests", null);
        Log.v("DBTest", res.toString());
    }
//    Greenhouse testimiseks
}
