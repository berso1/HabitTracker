package com.example.berso.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.example.berso.habittracker.data.HabitDbHelper;

import com.example.berso.habittracker.data.HabitContract.HabitEntry;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new HabitDbHelper(this);
        insertRows();
        showRows();

    }

    private void showRows() {

            // Create and/or open a database to read from it
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    HabitEntry._ID,
                    HabitEntry.COLUMN_HABIT,
                    HabitEntry.COLUMN_DATE,
                    HabitEntry.COLUMN_HOUR,
                    HabitEntry.COLUMN_INTENCITY };

            // Perform a query on the habits table
            Cursor cursor = db.query(
                    HabitEntry.TABLE_NAME,   // The table to query
                    projection,            // The columns to return
                    null,                  // The columns for the WHERE clause
                    null,                  // The values for the WHERE clause
                    null,                  // Don't group the rows
                    null,                  // Don't filter by row groups
                    null);                   // The sort order

            TextView displayView = (TextView) findViewById(R.id.display_text_view);

            try {
                // Create a header in the Text View
                displayView.setText("The habits table contains " + cursor.getCount() + " Habits.\n\n");
                displayView.append(HabitEntry._ID + " |   " +
                        HabitEntry.COLUMN_HABIT + "     |     " +
                        HabitEntry.COLUMN_DATE + "          | " +
                        HabitEntry.COLUMN_HOUR + "    |     " +
                        HabitEntry.COLUMN_INTENCITY + "\n");

                // Figure out the index of each column
                int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
                int habitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT);
                int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DATE);
                int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HOUR);
                int intencityColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_INTENCITY);

                // Iterate through all the returned rows in the cursor
                while (cursor.moveToNext()) {
                    // Use that index to extract the String or Int value of the word
                    // at the current row the cursor is on.
                    int currentID = cursor.getInt(idColumnIndex);
                    String currentHabit = cursor.getString(habitColumnIndex);
                    String currentDate = cursor.getString(dateColumnIndex);
                    String currentHour = cursor.getString(timeColumnIndex);
                    int currentIntencity = cursor.getInt(intencityColumnIndex);
                    // Display the values from each column of the current row in the cursor in the TextView
                    displayView.append(("\n" + currentID + "   | " +
                            currentHabit + " | " +
                            currentDate + " | " +
                            currentHour + " | " +
                            currentIntencity));
                }

            } finally {
                // Always close the cursor when you're done reading from it. This releases all its
                // resources and makes it invalid.
                cursor.close();
            }
        }

    private void insertRows() {

        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

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
