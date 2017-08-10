/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.habitTracker;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.android.habitTracker.data.HabitDbHelper;

import static java.sql.Types.NULL;

/**
 * Allows user to create a new pet or edit an existing one.
 */
public class EditorActivity extends AppCompatActivity {

    /**
     * EditText field to enter the pet's name
     */
    private EditText mNameEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mDateEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mTimeEditText;

    /**
     * EditText field to enter the pet's weight
     */
    private EditText mDurationEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = (EditText) findViewById(R.id.edit_activity_name);
        mDateEditText = (EditText) findViewById(R.id.edit_date);
        mTimeEditText = (EditText) findViewById(R.id.edit_time);
        mDurationEditText = (EditText) findViewById(R.id.edit_pet_weight);
    }


    /**
     * Get user input from editor and save new pet into database.
     */
    private void insertActivity() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String dateString = mDateEditText.getText().toString().trim();
        String timeString = mTimeEditText.getText().toString().trim();
        String durationString = mDurationEditText.getText().toString().trim();
        int durationInt = NULL;
        if (!durationString.isEmpty()) {
            durationInt = Integer.parseInt(dateString);
        }


        // Create database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        mDbHelper.insertActivity(nameString, dateString, timeString, durationInt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertActivity();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}