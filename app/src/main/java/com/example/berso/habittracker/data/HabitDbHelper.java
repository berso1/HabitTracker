package com.example.berso.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.berso.habittracker.data.HabitContract.HabitEntry;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by berso on 5/31/17.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Habits.db";

    //String to create Habits table
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HabitEntry.TABLE_NAME + " (" +
                    HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    HabitEntry.COLUMN_HABIT + " TEXT NOT NULL," +
                    HabitEntry.COLUMN_DATE + " DATETIME,"+
                    HabitEntry.COLUMN_HOUR + " DATETIME, "+
                    HabitEntry.COLUMN_INTENCITY + " INT DEFAULT 0 );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;
    //Constructor
    public HabitDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    //onCreate method only called if the database dont exist
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    //onUpgradeMethod only called if database changes, and need a new version to work
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
