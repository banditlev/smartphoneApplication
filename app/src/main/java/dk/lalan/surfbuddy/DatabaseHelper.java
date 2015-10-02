package dk.lalan.surfbuddy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lundtoft on 02/10/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "surfbuddy";
    public static final String TABLE_SURF = "surf";

    public static final String SURF_ID = "id";
    public static final String SURF_NAME = "name";
    public static final String SURF_DESCRIPTION = "description";
    public static final String SURF_TIME = "datetime";
    public static final String SURF_DIRECTION = "direction";
    public static final String SURF_WIND = "wind";
    public static final String SURF_TEMP = "temp";
    public static final String SURF_IDEAL_DIRECTION = "idealdirection";
    public static final String SURF_WAVE_HEIGHT = "waveheight";
    public static final String SURF_LEVEL = "level";
    public static final String SURF_LAT = "lat";
    public static final String SURF_LON = "lon";

    public static volatile DatabaseHelper instance = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_SURF+" (" + SURF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SURF_NAME + " VARCHAR(500), " + SURF_DESCRIPTION + " VARCHAR(1000), " + SURF_TIME + " VARCHAR(100), " + SURF_DIRECTION + " INTEGER, " + SURF_WIND + " DOUBLE, " + SURF_TEMP + " DOUBLE, " + SURF_IDEAL_DIRECTION + " INTEGER, " + SURF_WAVE_HEIGHT + " DOUBLE, " + SURF_LEVEL + " VARCHAR(500), " + SURF_LAT + " DOUBLE, " + SURF_LON + " DOUBLE)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SURF);
        onCreate(db);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }

        return instance;
    }
}
