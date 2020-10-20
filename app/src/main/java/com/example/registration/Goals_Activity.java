package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Goals_Activity extends AppCompatActivity {
    private RecyclerView GoalsRecView;
    private GoalsAdapter adapter;
    private ArrayList<GoalsClass> goalsClasses;
    private DataBaseGoals dataBaseGoals;
    private boolean MyPREFERENCES = false;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals__activtity);

        swipeRefreshLayout = findViewById(R.id.refresh_in_goals);
        initViews();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void initViews() {
        dataBaseGoals = new DataBaseGoals(this);
        adapter = new GoalsAdapter(this);
        if (isInsertedBeforeToDB()) {
            goalsClasses = dataBaseGoals.getEveryList();
        } else {
            goalsClasses = new ArrayList<>();
            goalsClasses.add(new GoalsClass("Monday"));
            goalsClasses.add(new GoalsClass("Tuesday"));
            goalsClasses.add(new GoalsClass("Wednesday"));
            goalsClasses.add(new GoalsClass("Thursday"));
            goalsClasses.add(new GoalsClass("Friday"));
            goalsClasses.add(new GoalsClass("Saturday"));
            goalsClasses.add(new GoalsClass("Sunday"));
            for (int i = 0; i < goalsClasses.size(); i++) {
                dataBaseGoals.addOne(goalsClasses.get(i));
            }
            setInsertedToDB();
        }
        GoalsRecView = (RecyclerView) findViewById(R.id.GoalsRecView);
        adapter.setGoals_items(goalsClasses);
        GoalsRecView.setAdapter(adapter);


//        GoalsRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setInsertedToDB() {
        SharedPreferences sharedpreferences = getSharedPreferences(String.valueOf(MyPREFERENCES), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("key", true);
        editor.apply();
    }

    public boolean isInsertedBeforeToDB() {
        SharedPreferences sharedpreferences = getSharedPreferences(String.valueOf(MyPREFERENCES), Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean("key", false);
    }
}