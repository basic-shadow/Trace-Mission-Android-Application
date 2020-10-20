package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView workspaceRecView;
    private WSAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new WSAdapter(this);
        workspaceRecView = findViewById(R.id.WorkspaceRecView);

        workspaceRecView.setAdapter(adapter);
        workspaceRecView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<WS_items> ws_items = new ArrayList<>();
        ws_items.add(new WS_items("Projects", 1, R.drawable.general_project_icon));
        ws_items.add(new WS_items("Tasks", 2, R.drawable.tasks_icon));
        ws_items.add(new WS_items("Goals", 3, R.drawable.goals));
        adapter.setWs_items(ws_items);

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
 }
