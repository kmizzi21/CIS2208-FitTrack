package com.example.fittrack;

public class Workout {

    private int id;
    private String exercise;
    private String setsReps;
    private String imageUri;
    private String date;

    public Workout(int id, String exercise, String setsReps, String imageUri, String date) {
        this.id = id;
        this.exercise = exercise;
        this.setsReps = setsReps;
        this.imageUri = imageUri;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getExercise() {
        return exercise;
    }

    public String getSetsReps() {
        return setsReps;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getDate() {
        return date;
    }
}