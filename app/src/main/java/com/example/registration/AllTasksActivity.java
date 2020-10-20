package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AllTasksActivity extends AppCompatActivity {

    private ListView list_Tasks;
    private FloatingActionButton add_Tasks;
    private ArrayAdapter<TasksClass> TasksArrayAdapter;
    private Button remover;
    private DataBaseTasks dbTasks;
    private TextView removeShow;
    private ArrayList<TasksClass> all_Tasks;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_tasks);

        swipeRefreshLayout = findViewById(R.id.refresh_in_tasks);
        initViews();

        list_Tasks.setAdapter(TasksArrayAdapter);

        add_Tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllTasksActivity.this, Tasks_Activity.class);
                AllTasksActivity.this.startActivity(intent);
            }
        });

        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeShow.setVisibility(View.VISIBLE);
                list_Tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TasksClass tasksClass = (TasksClass) parent.getItemAtPosition(position);

                        dbTasks.removeOne(tasksClass);

                        TasksArrayAdapter.notifyDataSetChanged();

                        Toast.makeText(AllTasksActivity.this, "Deleted: " + tasksClass.toString(), Toast.LENGTH_SHORT).show();
                        removeShow.setVisibility(View.GONE);
                    }
                });

            }
        });
        list_Tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String taskName = ((TasksClass) parent.getItemAtPosition(position)).getName();
                Intent intent = new Intent(AllTasksActivity.this, TaskName_Activity.class);
                intent.putExtra("Task_Name", taskName);
                AllTasksActivity.this.startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TasksArrayAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

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

    private void initViews() {
        list_Tasks = findViewById(R.id.list_tasks);
        add_Tasks = findViewById(R.id.btn_addTasks2);
        remover = findViewById(R.id.remove_Tasks);
        removeShow = findViewById(R.id.txtRemover);

        dbTasks = new DataBaseTasks(this);

        all_Tasks = (ArrayList<TasksClass>) dbTasks.getEveryList();


        TasksArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, all_Tasks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}