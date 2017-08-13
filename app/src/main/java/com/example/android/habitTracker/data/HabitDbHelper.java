package com.example.android.habitTracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.habitTracker.data.HabitContract.HabitEntry;

/**
 * Database helper for Activity app. Manages database creation and version management.
 */
public class HabitDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "activity.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link HabitDbHelper}.
     *
     * @param context of the app
     */
    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the table
        String SQL_CREATE_ACTIVITY_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_ACTIVITY_NAME + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_ACTIVITY_DATE + " TEXT NOT NULL DEFAULT CURRENT_DATE, "
                + HabitEntry.COLUMN_ACTIVITY_TIME + " TEXT NOT NULL DEFAULT CURRENT_TIME, "
                + HabitEntry.COLUMN_ACTIVITY_DURATION + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ACTIVITY_TABLE);
        Log.v(LOG_TAG, SQL_CREATE_ACTIVITY_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to be done here.
    }

    public boolean insertActivity(String nameString, String dateString, String timeString, int durationInt) {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        nameString = nameString.trim();
        dateString = dateString.trim();
        timeString = timeString.trim();

        // Gets the database in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_ACTIVITY_NAME, nameString);
        if (!dateString.isEmpty()) {
            values.put(HabitEntry.COLUMN_ACTIVITY_DATE, dateString);
        }
        if (!timeString.isEmpty()) {
            values.put(HabitEntry.COLUMN_ACTIVITY_TIME, timeString);
        }
        values.put(HabitEntry.COLUMN_ACTIVITY_DURATION, durationInt);

        // Insert a new row in the database, returning the ID of that new row.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        Log.v(LOG_TAG, "New row ID " + newRowId);

        // Show a toast message depending on whether or not the insertion was successful
        return newRowId != -1;
    }

    public Cursor getDatabaseCursor() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_ACTIVITY_NAME,
                HabitEntry.COLUMN_ACTIVITY_DATE,
                HabitEntry.COLUMN_ACTIVITY_TIME,
                HabitEntry.COLUMN_ACTIVITY_DURATION};

        // Perform a query on the table
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

    public String parseCursor(Cursor cursor) {

        StringBuilder tableDisplayString = new StringBuilder();

        try {
            // Create a header in the Text View that looks like this:
            //
            // The <name of table> table contains <number of rows in Cursor> <name of table, plural>
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            tableDisplayString.append("The activity table contains " + cursor.getCount() + " activities.\n\n");
            tableDisplayString.append(
                    HabitEntry._ID + " - " +
                            HabitEntry.COLUMN_ACTIVITY_NAME + " - " +
                            HabitEntry.COLUMN_ACTIVITY_DATE + " - " +
                            HabitEntry.COLUMN_ACTIVITY_TIME + " - " +
                            HabitEntry.COLUMN_ACTIVITY_DURATION + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ACTIVITY_NAME);
            int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ACTIVITY_DATE);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ACTIVITY_TIME);
            int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_ACTIVITY_DURATION);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                String currentTime = cursor.getString(timeColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                tableDisplayString.append("\n" +
                        currentID + " - " +
                        currentName + " - " +
                        currentDate + " - " +
                        currentTime + " - " +
                        currentDuration);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
            return tableDisplayString.toString();
        }
    }
}

