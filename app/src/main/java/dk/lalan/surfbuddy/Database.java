package dk.lalan.surfbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

import models.SurfLocation;

/**
 * Created by lundtoft on 02/10/15.
 */
public class Database {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public Database(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public void open() throws SQLiteException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public long addLocation(String name, String description, int idealDirection, String level, double lat, double lon){
        try {
            open();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(dbHelper.SURF_TIME, date.toString());
        values.put(dbHelper.SURF_NAME, name);
        values.put(dbHelper.SURF_DESCRIPTION, description);
        values.put(dbHelper.SURF_IDEAL_DIRECTION, idealDirection);
        values.put(dbHelper.SURF_LEVEL, level);
        values.put(dbHelper.SURF_LAT, lat);
        values.put(dbHelper.SURF_LON, lon);

        long insertId = db.insert(dbHelper.TABLE_SURF, null, values);
        close();
        return insertId;
    }

    public void updateLocation(long id, int direction, double wind, double temp, double waveHeight){
        try {
            open();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(dbHelper.SURF_TIME, date.toString());
        values.put(dbHelper.SURF_DIRECTION, direction);
        values.put(dbHelper.SURF_WIND, wind);
        values.put(dbHelper.SURF_TEMP, temp);
        values.put(dbHelper.SURF_WAVE_HEIGHT, waveHeight);

        db.update(dbHelper.TABLE_SURF, values, "id=" + id, null);
    }

    public ArrayList<SurfLocation> getAllLocations(){
        ArrayList<SurfLocation> locations = new ArrayList<>();

        try {
            open();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        Cursor cursor = db.query(dbHelper.TABLE_SURF, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            double longitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(dbHelper.SURF_LON)));
            double latitude = Double.parseDouble(cursor.getString(cursor.getColumnIndex(dbHelper.SURF_LAT)));
            double windSpeed = Double.parseDouble(cursor.getString(cursor.getColumnIndex(dbHelper.SURF_WIND)));
            double temperature = Double.parseDouble(cursor.getString(cursor.getColumnIndex(dbHelper.SURF_TEMP)));
            double waveHeight = Double.parseDouble(cursor.getString(cursor.getColumnIndex(dbHelper.SURF_WAVE_HEIGHT)));
            String name = cursor.getString(cursor.getColumnIndex(dbHelper.SURF_NAME));
            String description = cursor.getString(cursor.getColumnIndex(dbHelper.SURF_DESCRIPTION));
            String level = cursor.getString(cursor.getColumnIndex(dbHelper.SURF_LEVEL));
            int windDir = Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.SURF_DIRECTION)));
            int surfDir = Integer.parseInt(cursor.getString(cursor.getColumnIndex(dbHelper.SURF_IDEAL_DIRECTION)));
            locations.add(new SurfLocation(longitude, latitude, windSpeed, temperature, waveHeight, name, description, level, windDir, surfDir));
        }

        cursor.close();
        close();
        return locations;
    }

    static class DatabaseHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "surfbuddy";
        private static final String TABLE_SURF = "surf";

        private static final String SURF_ID = "id";
        private static final String SURF_NAME = "name";
        private static final String SURF_DESCRIPTION = "description";
        private static final String SURF_TIME = "datetime";
        private static final String SURF_DIRECTION = "direction";
        private static final String SURF_WIND = "wind";
        private static final String SURF_TEMP = "temp";
        private static final String SURF_IDEAL_DIRECTION = "idealdirection";
        private static final String SURF_WAVE_HEIGHT = "waveheight";
        private static final String SURF_LEVEL = "level";
        private static final String SURF_LAT = "lat";
        private static final String SURF_LON = "lon";

        private static volatile DatabaseHelper instance = null;

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
}
