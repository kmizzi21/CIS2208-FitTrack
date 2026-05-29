package com.example.fittrack;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
// SQLITE DATABASE HELPER
// Handles all local database operations
public class WorkoutDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fittrack.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_WORKOUTS = "workouts";
    private static final String COL_ID = "id";
    private static final String COL_EXERCISE = "exercise";
    private static final String COL_SETS_REPS = "sets_reps";
    private static final String COL_IMAGE_URI = "image_uri";
    private static final String COL_DATE = "date";

    public WorkoutDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // SQLITE database creation
    // Creates workouts table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_WORKOUTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EXERCISE + " TEXT, " +
                COL_SETS_REPS + " TEXT, " +
                COL_IMAGE_URI + " TEXT, " +
                COL_DATE + " TEXT)";
        db.execSQL(createTable);
    }

    // Runs if the database version changes.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS);
        onCreate(db);
    }

    // CRUD - CREATE
    // Inserts new workout into database
    public boolean addWorkout(String exercise, String setsReps, String imageUri, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_EXERCISE, exercise);
        values.put(COL_SETS_REPS, setsReps);
        values.put(COL_IMAGE_URI, imageUri);
        values.put(COL_DATE, date);

        long result = db.insert(TABLE_WORKOUTS, null, values);
        return result != -1;
    }

    // CRUD - READ
    // Retrieves all workouts for RecyclerView
    public ArrayList<Workout> getAllWorkouts() {
        ArrayList<Workout> workouts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WORKOUTS + " ORDER BY " + COL_ID + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Workout workout = new Workout(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_EXERCISE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_SETS_REPS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE_URI)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE))
                );

                workouts.add(workout);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return workouts;
    }

    // CRUD - READ
    // Retrieves single workout by ID
    public Workout getWorkoutById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_WORKOUTS + " WHERE " + COL_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        Workout workout = null;

        if (cursor.moveToFirst()) {
            int workoutId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
            String exercise = cursor.getString(cursor.getColumnIndexOrThrow(COL_EXERCISE));
            String setsReps = cursor.getString(cursor.getColumnIndexOrThrow(COL_SETS_REPS));
            String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE_URI));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));

            workout = new Workout(workoutId, exercise, setsReps, imageUri, date);
        }

        cursor.close();
        return workout;
    }

    // CRUD - DELETE
    // Removes workout from database
    public void deleteWorkout(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORKOUTS, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    // CRUD - UPDATE
    // Updates existing workout information
    public boolean updateWorkout(int id, String exercise, String setsReps, String imageUri) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_EXERCISE, exercise);
        values.put(COL_SETS_REPS, setsReps);
        values.put(COL_IMAGE_URI, imageUri);

        int result = db.update(
                TABLE_WORKOUTS,
                values,
                COL_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        return result > 0;
    }

}