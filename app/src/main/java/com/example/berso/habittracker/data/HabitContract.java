package com.example.berso.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by berso on 5/31/17.
 */

public class HabitContract {

    private HabitContract() {
    }

    //Main table to track an habit
    public static class HabitEntry implements BaseColumns {

        //Table Name
        public static final String TABLE_NAME = "Habits";

        //Columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT = "habit";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_HOUR = "hour";
        public static final String COLUMN_INTENCITY = "intencity";
    }
}
