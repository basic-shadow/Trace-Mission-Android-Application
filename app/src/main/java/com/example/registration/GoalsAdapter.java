package com.example.registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {
    private ArrayList<GoalsClass> goals_items;
    private Context context;
    private DataBaseGoals dataBaseGoals;


    public ArrayList<GoalsClass> getGoals_items(ArrayList<GoalsClass> goalsClasses) {
        return goals_items;
    }

    public void setGoals_items(ArrayList<GoalsClass> goals_items) {
        this.goals_items = goals_items;
    }

    public GoalsAdapter(Context context) {
        this.context = context;
        dataBaseGoals = new DataBaseGoals(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_day_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dayWeek.setText(goals_items.get(position).getDay());
        holder.editText.setText(dataBaseGoals.getEditedText(goals_items.get(position).getDay()));
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayToEdit = goals_items.get(position).getDay();
                Intent intent = new Intent(context, Goals_Days_Edit.class);
                intent.putExtra("DayToEdit", dayToEdit);
                context.startActivity(intent);
                notifyDataSetChanged();
                
            }
        });
    }

    @Override
    public int getItemCount() {
        return goals_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView dayWeek;
        private TextView editText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.days_layout);
            dayWeek = itemView.findViewById(R.id.num_day);
            editText = itemView.findViewById(R.id.todo_day);
        }
    }

}
