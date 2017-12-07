package com.example.deric.geonote.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Deric on 17/12/07.
 */

public class NotesDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Notes.db";
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_DATETIME = "datetime";

    private static final String[] COLUMNS = {COLUMN_ID, COLUMN_NOTE, COLUMN_LATITUDE,
            COLUMN_LONGITUDE, COLUMN_DATETIME};


    private static NotesDatabaseHelper sInstance;

    public static synchronized NotesDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new NotesDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private NotesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOTE + " TEXT, " +
                COLUMN_LATITUDE + " REAL, " +
                COLUMN_LONGITUDE + " REAL, " +
                COLUMN_DATETIME + " TEXT );";

        // create books table
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // create fresh books table
        this.onCreate(db);
    }

    public void addGeoNote(GeoNote note){
        //for logging
        Log.d("addGeoNote", note.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, note.getNote());
        values.put(COLUMN_LATITUDE, note.getLatitude());
        values.put(COLUMN_LONGITUDE, note.getLongitude());
        values.put(COLUMN_DATETIME, note.getDateTime());

        // 3. insert
        db.insert(TABLE_NAME, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }


    public GeoNote getGeoNote(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_NAME, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        GeoNote geoNote = cursorToGeoNote(cursor);

        //log
        Log.d("getGeoNote("+id+")", geoNote.toString());

        // 5. return book
        return geoNote;
    }

    public List<GeoNote> getAllGeoNotes() {
        List<GeoNote> geoNotes = new LinkedList<GeoNote>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        GeoNote geoNote = null;
        if (cursor.moveToFirst()) {
            do {
                geoNote = cursorToGeoNote(cursor);

                // Add book to books
                geoNotes.add(geoNote);
            } while (cursor.moveToNext());
        }

        Log.d("getAllGeoNotes()", geoNotes.toString());

        // return books
        return geoNotes;
    }

    public int updateGeoNote(GeoNote geoNote) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, geoNote.getNote());
        values.put(COLUMN_LATITUDE, geoNote.getLatitude());
        values.put(COLUMN_LONGITUDE, geoNote.getLongitude());
        values.put(COLUMN_DATETIME, geoNote.getDateTime());

        // 3. updating row
        int i = db.update(TABLE_NAME, //table
                values, // column/value
                COLUMN_ID + " = ?", // selections
                new String[] { String.valueOf(geoNote.getId()) }); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteGeoNote(GeoNote geoNote) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_NAME, //table name
                COLUMN_ID + " = ?",  // selections
                new String[] { String.valueOf(geoNote.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteGeoNote", geoNote.toString());
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NotesDatabaseHelper.TABLE_NAME, null, null);
    }

    private GeoNote cursorToGeoNote(Cursor cursor) {
        GeoNote note = new GeoNote();
        note.setId(Integer.parseInt(cursor.getString(0)));
        note.setNote(cursor.getString(1));
        note.setLatitude(cursor.getDouble(2));
        note.setLongitude(cursor.getDouble(3));
        note.setDateTime(cursor.getString(4));
        return note;
    }

}