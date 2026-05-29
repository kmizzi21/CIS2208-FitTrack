package com.example.fittrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// HOME FRAGMENT
// Displays home screen UI and navigations buttons
public class HomeFragment extends Fragment {

    Button btnAddWorkout, btnViewHistory;
    TextView textFitnessTip;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    // Fragment UI setup
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnAddWorkout = view.findViewById(R.id.btnAddWorkout);
        btnViewHistory = view.findViewById(R.id.btnViewHistory);
        textFitnessTip = view.findViewById(R.id.textFitnessTip);
        loadFitnessTip();

        // INTENT NAVIGATION
        // Opens Add Workout screen
        btnAddWorkout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddWorkoutActivity.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        });

        // INTENT NAVIGATION
        // Opens Workout History screen
        btnViewHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HistoryActivity.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left
            );
        });

        return view;
    }


    // HTTP GET request section - retrieves motiviational fitness tip
    private void loadFitnessTip() {
       // Background thread prevents blocking the UI
        new Thread(() -> {
            try {
                URL url = new URL("https://zenquotes.io/api/random");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                String result = response.toString();

                String quote = result;

                int quoteStart = result.indexOf("\"q\":\"");
                int quoteEnd = result.indexOf("\",\"a\"");

                if (quoteStart != -1 && quoteEnd != -1) {
                    quote = result.substring(quoteStart + 5, quoteEnd);
                }

                String finalQuote = quote;

                requireActivity().runOnUiThread(() -> {
                    if (finalQuote.length() < 80) {
                        textFitnessTip.setText(finalQuote);
                    } else {
                        textFitnessTip.setText("Stay consistent. Small progress every day adds up.");
                    }
                });
                textFitnessTip.setOnClickListener(v -> loadFitnessTip());
            } catch (Exception e) {
                requireActivity().runOnUiThread(() -> {
                    textFitnessTip.setText("Stay consistent. Small progress every day adds up.");
                });
            }
        }).start();
    }

}