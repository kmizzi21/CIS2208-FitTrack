package com.example.fittrack;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

// Main Activity
// Hosts the HomeFragment and toolbar navigation
public class MainActivity extends AppCompatActivity {

    // Main activity that displays the HomeFragment.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            // Loads HomeFragment into MainActivity
            // FRAGMENT IMPLEMENTATION
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment())
                    .commit();
        }
    }
}