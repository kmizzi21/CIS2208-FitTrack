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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// ADD WORKOUT ACTIVITY
// Allows user to create and save workouts
public class AddWorkoutActivity extends AppCompatActivity {


    EditText editExercise, editSetsReps;
    Button btnUploadImage, btnSaveWorkout;
    ImageView imagePreview;

    Uri selectedImageUri = null;
    WorkoutDatabaseHelper dbHelper;

    // ADVANCED INTENT
    // Opens device storage to select image
    ActivityResultLauncher<String[]> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.addToolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        editExercise = findViewById(R.id.editExercise);
        editSetsReps = findViewById(R.id.editSetsReps);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        btnSaveWorkout = findViewById(R.id.btnSaveWorkout);
        imagePreview = findViewById(R.id.imagePreview);

        dbHelper = new WorkoutDatabaseHelper(this);

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

        btnUploadImage.setOnClickListener(v -> {
            imagePickerLauncher.launch(new String[]{"image/*"});
        });

        // SQLITE INSERT
        // Saves workout data locally
        btnSaveWorkout.setOnClickListener(v -> {
            saveWorkout();
        });
    }

    private void saveWorkout() {
        String exercise = editExercise.getText().toString().trim();
        String setsReps = editSetsReps.getText().toString().trim();

        if (exercise.isEmpty() || setsReps.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String imageUriString = selectedImageUri != null ? selectedImageUri.toString() : "";
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        boolean saved = dbHelper.addWorkout(exercise, setsReps, imageUriString, date);

        if (saved) {
            Toast.makeText(this, "Workout saved", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HistoryActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Error saving workout", Toast.LENGTH_SHORT).show();
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