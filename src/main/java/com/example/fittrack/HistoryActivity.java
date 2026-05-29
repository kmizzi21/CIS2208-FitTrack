package com.example.fittrack;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// HISTORY ACTIVITY
// Displays workout history using RecyclerView
public class HistoryActivity extends AppCompatActivity {


    TextView textEmpty;
    RecyclerView recyclerWorkouts;
    WorkoutDatabaseHelper dbHelper;
    WorkoutAdapter adapter;
    ArrayList<Workout> workoutList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.historyToolbar);
        textEmpty = findViewById(R.id.textEmpty);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerWorkouts = findViewById(R.id.recyclerWorkouts);
        dbHelper = new WorkoutDatabaseHelper(this);

        workoutList = dbHelper.getAllWorkouts();

        // HCI FEATURE
        // Displays message when no workouts exist
        if (workoutList.isEmpty()) {
            textEmpty.setVisibility(View.VISIBLE);
            recyclerWorkouts.setVisibility(View.GONE);
        } else {
            textEmpty.setVisibility(View.GONE);
            recyclerWorkouts.setVisibility(View.VISIBLE);
        }

        adapter = new WorkoutAdapter(this, workoutList);
        recyclerWorkouts.setLayoutManager(new LinearLayoutManager(this));
        recyclerWorkouts.setAdapter(adapter);
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