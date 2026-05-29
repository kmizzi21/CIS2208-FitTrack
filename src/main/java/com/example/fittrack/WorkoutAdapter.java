package com.example.fittrack;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// RECYCLERVIEW ADAPTER for workout history list
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private Context context;
    private ArrayList<Workout> workoutList;

    public WorkoutAdapter(Context context, ArrayList<Workout> workoutList) {
        this.context = context;
        this.workoutList = workoutList;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_workout, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workoutList.get(position);

        holder.textExercise.setText(workout.getExercise());
        holder.textDate.setText(workout.getDate());

        // INTENT NAVIGATION
        // Opens workout details screen
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WorkoutDetailsActivity.class);
            intent.putExtra("workout_id", workout.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {

        TextView textExercise, textDate;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            textExercise = itemView.findViewById(R.id.textExercise);
            textDate = itemView.findViewById(R.id.textDate);
        }
    }
}