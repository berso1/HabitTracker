package com.example.berso.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
                    HabitEntry.COLUMN_DATE + " DATETIME," +
                    HabitEntry.COLUMN_HOUR + " DATETIME, " +
                    HabitEntry.COLUMN_INTENCITY + " INT DEFAULT 0 );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

    //Constructor
    public HabitDbHelper(Context context) {
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

    public Cursor readAllHabits() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT,
                HabitEntry.COLUMN_DATE,
                HabitEntry.COLUMN_HOUR,
                HabitEntry.COLUMN_INTENCITY};

        // Perform a query on the habits table
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        return cursor;
    }

    public void insertRows() {

        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

// Delete row before inserting new rows
        db.execSQL( "DELETE FROM habits;");

        //insert 5 rows into the table
        for (int i = 0; i < 5; i++) {
            //define de date and time format i want in my rows
            SimpleDateFormat dateStamp = new SimpleDateFormat("YYYY-MM-dd");
            SimpleDateFormat timeStamp = new SimpleDateFormat("HH:mm");
            Date date = new Date();

            //create a content with the values to insert
            ContentValues values = new ContentValues();
            values.put(HabitEntry.COLUMN_HABIT, "Habit #" + i);
            values.put(HabitEntry.COLUMN_DATE, dateStamp.format(date));
            values.put(HabitEntry.COLUMN_HOUR, timeStamp.format(date));
            values.put(HabitEntry.COLUMN_INTENCITY, 2);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

            //test for error
            if (newRowId == -1) {
                Log.v("MainActivity.insertRows", "Error during insert");
            }
        }
    }
}
