package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.TreeSet;

public class Assignments_Activity extends AppCompatActivity {

    private ArrayList<ProjectsDirectoryClass> projectsDirectoryClasses;
    private ArrayList<ProjectsClass> projectsClasses;
    private AssignmentAdapter adapter;
    private RecyclerView assignmentRecView;
    private FloatingActionButton addButton;
    private RelativeLayout layout;
    private EditText textName;
    private DataBaseProjects dataBaseProjects;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button button;
    private String dirName;
    ProjectsClass new_project;

    private TreeSet<String> assignmentSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViews();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = 0;
                        while (i < projectsDirectoryClasses.size()) {
                            if (projectsDirectoryClasses.get(i).getDirectoryName().equals(dirName)) {
                                break;
                            }
                            i++;
                        }
                        int k = 0;
                        while(k < projectsClasses.size()) {
                            if (projectsClasses.get(k).getName().equals(projectsDirectoryClasses.get(i).getProjectsClassName())) {
                                break;
                            }
                            k++;
                        }
                        new_project = new ProjectsClass(projectsClasses.get(k).getName(),
                                projectsClasses.get(k).getAdmin(), -1, projectsClasses.get(k).getPin_code(),
                                R.drawable.assignment);
                        boolean success = dataBaseProjects.addOne(new_project, dirName, textName.getText().toString());

                        Toast.makeText(getApplicationContext(), "Success " + success, Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                });
            }
        });
        addButton.setOnLongClickListener(new View.OnLongClickListener() {
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
        assignmentRecView = findViewById(R.id.AssignmentsRecView);
        dataBaseProjects = new DataBaseProjects(this);
        projectsDirectoryClasses = (ArrayList<ProjectsDirectoryClass>) dataBaseProjects.getDirectoryList();
        projectsClasses = (ArrayList<ProjectsClass>) dataBaseProjects.getEveryList();
        assignmentSize = new TreeSet<>();
        for(int i = 0; i < projectsDirectoryClasses.size(); i++) {
            assignmentSize.add(projectsDirectoryClasses.get(i).getFileName());
        }

        Bundle bundle = getIntent().getExtras();
        dirName = bundle.getString("dirNameForFile");
        adapter = new AssignmentAdapter(this, dirName, dataBaseProjects);

        assignmentRecView.setLayoutManager(new LinearLayoutManager(this));
        assignmentRecView.setAdapter(adapter);
        adapter.setProject_directory_items(projectsDirectoryClasses);

        layout = findViewById(R.id.register_assignment);
        addButton = findViewById(R.id.assignmentsAddBtn);
        textName = findViewById(R.id.name_assignment);
        button = findViewById(R.id.add_assignment_btn);
        swipeRefreshLayout = findViewById(R.id.swipe_in_assignments);

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