package dk.lalan.noteapp_group_5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lundtoft on 14/09/15.
 */
public class Database {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public Database(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public long addNote(String note){
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(dbHelper.NOTE_TEXT, note);
        values.put(dbHelper.NOTE_TIME, date.toString());

        long insertId = db.insert(dbHelper.TABLE_NOTES, null, values);
        close();
        return insertId;
    }

    public List<String> getAllNotes(){
        List<String> notes = new ArrayList<String>();

        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.query(dbHelper.TABLE_NOTES, null, null, null, null, null, null); //Velkommen til null helvedet!

        while (cursor.moveToNext()) {
            String note = cursor.getString(cursor.getColumnIndex(dbHelper.NOTE_TEXT));
            notes.add(note);
        }

        cursor.close();
        close();
        return notes;
    }

    public void clearDB(){
        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.delete(dbHelper.TABLE_NOTES, null, null);

        close();
    }

    static class DatabaseHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "notes";
        private static final String TABLE_NOTES = "notes";

        private static final String NOTE_ID = "id";
        private static final String NOTE_TEXT = "notetext";
        private static final String NOTE_TIME = "datetime";

        private Context context;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE notes (" + NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NOTE_TEXT + " VARCHAR(500), " + NOTE_TIME + " VARCHAR(100))";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
            onCreate(db);
        }
    }
}
