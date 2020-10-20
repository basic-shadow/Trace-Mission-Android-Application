package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.TreeSet;

public class Projects_Activity extends AppCompatActivity {
    private RecyclerView projectsRecView;
    private ProjectsAdapter adapter;
    private ArrayList<ProjectsClass> projectsClasses;
    private EditText project_name, admin_name, directory_name, pin_code, assignment_name;
    private RelativeLayout layout, pin_code_layout;
    private FloatingActionButton addProjects;
    private Button addBtn;
    private DataBaseProjects dataBaseProjects;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProjectsClass projectsClass;
    private EditText pin_code_in_projects;
    private Button enter_pin_code_btn;
    private TextView textViewPinCode;

    private TreeSet<String> project_uniqueNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        initViews();

        addProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View v) {
                        projectsClass = new ProjectsClass(project_name.getText().toString(),
                                admin_name.getText().toString(), -1, pin_code.getText().toString(),
                                R.drawable.directory_project);
                        projectsClasses.add(projectsClass);
                        boolean success = dataBaseProjects.addOne(projectsClass, directory_name.getText().toString(), assignment_name.getText().toString());
                        adapter.setProject_items(projectsClasses);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Success " + success, Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
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
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initViews() {
        pin_code_in_projects = findViewById(R.id.pin_code_in_projects);
        enter_pin_code_btn = findViewById(R.id.check_pin_code_btn);
        pin_code_layout = findViewById(R.id.pin_code_entrance_layout);
        textViewPinCode = findViewById(R.id.show_incorrect_pin_code);
        projectsRecView = findViewById(R.id.ProjectsRecView);
        dataBaseProjects = new DataBaseProjects(this);
        projectsClasses = (ArrayList<ProjectsClass>) dataBaseProjects.getEveryList();

        project_uniqueNames = new TreeSet<>();
        for (int i = 0; i < projectsClasses.size(); i++) {
            project_uniqueNames.add(projectsClasses.get(i).getName());
        }

        adapter = new ProjectsAdapter(this, dataBaseProjects, pin_code_layout, pin_code_in_projects, enter_pin_code_btn, textViewPinCode);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layout = findViewById(R.id.register_projects);
        directory_name = findViewById(R.id.project_directoryName);

        project_name = findViewById(R.id.Enter_Projects_Name);
        admin_name = findViewById(R.id.Enter_Admin);
        pin_code = findViewById(R.id.Pin_Code);
        addBtn = findViewById(R.id.add_curr_project);
        addProjects = findViewById(R.id.btn_addProjects);
        assignment_name = findViewById(R.id.Enter_Assignment_Name);
        projectsRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setProject_items(projectsClasses);
        projectsRecView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        swipeRefreshLayout = findViewById(R.id.refresh_in_projects);
        projectsRecView.setHasFixedSize(true);

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

    @Override
    public void onBackPressed() {
        adapter.notifyDataSetChanged();
        super.onBackPressed();
    }
}