package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Project_Directories extends AppCompatActivity {
    private RecyclerView projectsDirectoryRecView;
    private ProjectsDirectoryAdapter adapter;
    private DataBaseProjects dataBaseProjects;
    private FloatingActionButton addProjects;
    private ArrayList<ProjectsDirectoryClass> projectsDirectoryClasses;
    private int toPrintSize;
    private Button button;
    private EditText dirName, assignmentName;
    private RelativeLayout layout;
    private ArrayList<ProjectsClass> projectsClasses;
    private String projectNameIs;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProjectsClass projectsClass;
    private static final String TAG = "Project_Directories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project__directories);

        initViews();

        addProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = 0;
                        for (int j = 0; j < projectsClasses.size(); j++) {
                            if (projectsClasses.get(j).getName().equals(projectNameIs)) {
                                i = j;
                                break;
                            }
                        }
                        projectsClass = new ProjectsClass(projectNameIs,
                                projectsClasses.get(i).getAdmin(), -1,
                                projectsClasses.get(i).getPin_code(),
                                R.drawable.directory_project);
                        boolean success = dataBaseProjects.addOne(projectsClass, dirName.getText().toString(), assignmentName.getText().toString());

                        finish();
                        startActivity(getIntent());
                        Toast.makeText(getApplicationContext(), "Success " + success, Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0, 0);
                    }
                });
            }
        });
        addProjects.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (layout.getVisibility() == View.VISIBLE) {
                    layout.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                recreate();
                overridePendingTransition(0, 0);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void initViews() {
        projectsDirectoryRecView = findViewById(R.id.ProjectDirectsRecView);
        Bundle bundle = getIntent().getExtras();
        projectNameIs = bundle.getString("projectNameToGet");

        dataBaseProjects = new DataBaseProjects(this);
        projectsDirectoryClasses = (ArrayList<ProjectsDirectoryClass>) dataBaseProjects.getDirectoryList();
        projectsClasses = (ArrayList<ProjectsClass>) dataBaseProjects.getEveryList();
        toPrintSize = 0;
        for (int i = 0; i < projectsDirectoryClasses.size(); i++) {
            if (projectsDirectoryClasses.get(i).getProjectsClassName().equals(projectNameIs))
                toPrintSize++;
        }

        adapter = new ProjectsDirectoryAdapter(this, projectNameIs, dataBaseProjects);


        addProjects = findViewById(R.id.add_directory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        projectsDirectoryRecView.setAdapter(adapter);
        projectsDirectoryRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setProject_directory_items(projectsDirectoryClasses);
        layout = findViewById(R.id.add_directory_to_project);
        button = findViewById(R.id.add_directory_btn);
        dirName = findViewById(R.id.name_directory);
        swipeRefreshLayout = findViewById(R.id.swipe_in_directory);
        assignmentName = findViewById(R.id.name_assignment_in_directory);

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

    @Override
    public void onBackPressed() {
        adapter.notifyDataSetChanged();
        super.onBackPressed();
    }
}