package dk.lalan.surfbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import java.util.ArrayList;
import java.util.Date;

import models.SurfLocation;

/**
 * Created by lundtoft on 02/10/15.
 */
public class Database {

    private final DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public Database(Context context){
        dbHelper = DatabaseHelper.getInstance(context);
    }

    public void open(){
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException e){
            e.printStackTrace();
        }
    }

    public void close(){
        dbHelper.close();
    }

    public long addLocation(String name, int idealDirection, String level, double lat, double lon){
        open();
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(dbHelper.SURF_TIME, date.toString());
        values.put(dbHelper.SURF_NAME, name);
        values.put(dbHelper.SURF_IDEAL_DIRECTION, idealDirection);
        values.put(dbHelper.SURF_LEVEL, level);
        values.put(dbHelper.SURF_LAT, lat);
        values.put(dbHelper.SURF_LON, lon);

        long insertId = db.insert(dbHelper.TABLE_SURF, null, values);
        updateLocation(insertId, 0, 0.0, 0.0, 0.0, "");
        close();
        return insertId;
    }

    public void updateLocation(long id, int direction, double wind, double temp, double waveHeight, String desc){
        open();

        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(dbHelper.SURF_TIME, date.toString());
        values.put(dbHelper.SURF_DIRECTION, direction);
        values.put(dbHelper.SURF_WIND, wind);
        values.put(dbHelper.SURF_TEMP, temp);
        values.put(dbHelper.SURF_WAVE_HEIGHT, waveHeight);
        values.put(dbHelper.SURF_DESCRIPTION, desc);

        db.update(dbHelper.TABLE_SURF, values, "id=" + id, null);
        close();
    }

    public void clearDB(){
        open();

        db.delete(dbHelper.TABLE_SURF, null, null);

        close();
    }

    public void removeLocation(long id){
        open();

        db.delete(dbHelper.TABLE_SURF, "id=" + id, null);

        close();
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
            long id = Long.parseLong(cursor.getString(cursor.getColumnIndex(dbHelper.SURF_ID)));
            locations.add(new SurfLocation(id, longitude, latitude, windSpeed, temperature, waveHeight, name, description, level, windDir, surfDir));
        }

        cursor.close();
        close();
        return locations;
    }


}
