package com.example.cyncyn.plainolnotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

    //Constants for db name and version
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 6;

    //Constants for identifying table and columns
    public static final String TABLE_NOTES = "notes";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_TEXT = "noteText";
    public static final String NOTE_CREATED = "noteCreated";
    public static final String NOTE_QUANTITY = "noteQnty";
    public static final String NOTE_EXPIRY = "noteExp";
    public static final String NOTE_CHECK = "noteCheck";
    public static final String NOTE_CHECK2 = "noteCheck2";
    public static final String NOTE_EMAIL = "noteEmail";

    public static final String[] ALL_COLUMNS =
            {NOTE_ID, NOTE_TEXT, NOTE_QUANTITY, NOTE_CREATED, NOTE_EXPIRY, NOTE_CHECK, NOTE_CHECK2, NOTE_EMAIL};

    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NOTES + " (" +
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NOTE_TEXT + " TEXT, " +
                    NOTE_QUANTITY + " TEXT, " +
                    NOTE_EXPIRY + " TEXT, " +
                    NOTE_CHECK + " INTEGER, " +
                    NOTE_CHECK2 + " INTEGER, " +
                    NOTE_EMAIL + " TEXT, " +
                    NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }
}

