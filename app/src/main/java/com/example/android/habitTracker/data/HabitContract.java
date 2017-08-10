package com.example.android.habitTracker.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Activity app.
 */
public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private HabitContract() {
    }

    /**
     * Inner class that defines constant values for the database table.
     * Each entry in the table represents a single activity.
     */
    public static final class HabitEntry implements BaseColumns {

        /**
         * Name of database table
         */
        public final static String TABLE_NAME = "habit";

        /**
         * Unique ID number for the activity (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the activity.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_ACTIVITY_NAME = "activity";

        /**
         * Date of the Activity.
         * <p>
         * Type: TEXT, Format YYYY-MM-DD
         */
        public final static String COLUMN_ACTIVITY_DATE = "date";

        /**
         * Time of activity.
         * <p>
         * Type: TEXT ; Format: HH:MM:SS
         */
        public final static String COLUMN_ACTIVITY_TIME = "time";

        /**
         * Duration of the activity.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_ACTIVITY_DURATION = "duration";

    }

}