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
        mDbHelper.insertRows();
        showRows();

    }

    private void showRows() {

            Cursor cursor = mDbHelper.readAllHabits();

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


}
