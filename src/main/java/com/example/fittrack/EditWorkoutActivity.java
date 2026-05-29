package com.example.fittrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

// EDIT WORKOUT ACTIVITY
// Allows user to modify existing workouts
public class EditWorkoutActivity extends AppCompatActivity {

    EditText editExercise, editSetsReps;
    ImageView imagePreview;
    Button btnChangeImage, btnUpdateWorkout;

    WorkoutDatabaseHelper dbHelper;
    int workoutId;

    Uri selectedImageUri = null;
    String currentImageUri = "";

    ActivityResultLauncher<String[]> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);

        Toolbar toolbar = findViewById(R.id.editToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editExercise = findViewById(R.id.editExercise);
        editSetsReps = findViewById(R.id.editSetsReps);
        imagePreview = findViewById(R.id.imagePreview);
        btnChangeImage = findViewById(R.id.btnChangeImage);
        btnUpdateWorkout = findViewById(R.id.btnUpdateWorkout);

        dbHelper = new WorkoutDatabaseHelper(this);
        workoutId = getIntent().getIntExtra("workout_id", -1);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.OpenDocument(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri;

                        getContentResolver().takePersistableUriPermission(
                                uri,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );

                        imagePreview.setImageURI(uri);
                    }
                }
        );

        loadWorkoutData();

        btnChangeImage.setOnClickListener(v -> {
            imagePickerLauncher.launch(new String[]{"image/*"});
        });

        btnUpdateWorkout.setOnClickListener(v -> {
            updateWorkout();
        });
    }

    private void loadWorkoutData() {
        Workout workout = dbHelper.getWorkoutById(workoutId);

        if (workout == null) {
            Toast.makeText(this, "Workout not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editExercise.setText(workout.getExercise());
        editSetsReps.setText(workout.getSetsReps());

        currentImageUri = workout.getImageUri();

        if (currentImageUri != null && !currentImageUri.isEmpty()) {
            imagePreview.setImageURI(Uri.parse(currentImageUri));
        }
    }

    // SQLITE UPDATE
    // Updates workout information in database
    private void updateWorkout() {
        String exercise = editExercise.getText().toString().trim();
        String setsReps = editSetsReps.getText().toString().trim();

        if (exercise.isEmpty() || setsReps.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageToSave;

        if (selectedImageUri != null) {
            imageToSave = selectedImageUri.toString();
        } else {
            imageToSave = currentImageUri;
        }

        boolean updated = dbHelper.updateWorkout(workoutId, exercise, setsReps, imageToSave);

        if (updated) {
            Toast.makeText(this, "Workout updated", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, WorkoutDetailsActivity.class);
            intent.putExtra("workout_id", workoutId);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error updating workout", Toast.LENGTH_SHORT).show();
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