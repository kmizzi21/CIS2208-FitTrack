package com.example.fittrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// WORKOUT DETAILS ACTIVITY
// Displays full workout information
public class WorkoutDetailsActivity extends AppCompatActivity {

    TextView textExerciseDetails, textSetsRepsDetails, textDateDetails;
    ImageView imageDetails;

    // HCI FEATURE
    // Confirmation dialog before deleting workout
    Button btnDelete, btnEdit;

    WorkoutDatabaseHelper dbHelper;
    int workoutId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_details);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.detailsToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        textExerciseDetails = findViewById(R.id.textExerciseDetails);
        textSetsRepsDetails = findViewById(R.id.textSetsRepsDetails);
        textDateDetails = findViewById(R.id.textDateDetails);
        imageDetails = findViewById(R.id.imageDetails);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);

        dbHelper = new WorkoutDatabaseHelper(this);

        workoutId = getIntent().getIntExtra("workout_id", -1);

        if (workoutId != -1) {
            loadWorkout();
        }

        btnDelete.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Delete Workout")
                    .setMessage("Are you sure you want to delete this workout?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        dbHelper.deleteWorkout(workoutId);
                        Toast.makeText(this, "Workout deleted", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // INTENT NAVIGATION
        // Opens Edit Workout screen
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditWorkoutActivity.class);
            intent.putExtra("workout_id", workoutId);
            startActivity(intent);
        });

    }

    private void loadWorkout() {
        Workout workout = dbHelper.getWorkoutById(workoutId);

        if (workout == null) {
            Toast.makeText(this, "Workout not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        textExerciseDetails.setText("Exercise: " + workout.getExercise());
        textSetsRepsDetails.setText("Sets/Reps: " + workout.getSetsReps());
        textDateDetails.setText("Date: " + workout.getDate());

        if (workout.getImageUri() != null && !workout.getImageUri().isEmpty()) {
            imageDetails.setImageURI(Uri.parse(workout.getImageUri()));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        overridePendingTransition(
                R.anim.slide_in_left,
                R.anim.slide_out_right
        );
        return true;
    }

}