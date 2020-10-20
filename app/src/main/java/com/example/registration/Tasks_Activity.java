package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Tasks_Activity extends AppCompatActivity {
    private EditText tasks_name, admin_name;
    private Button addTasks_btn;
    private DataBaseTasks dataBaseTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_tasks_name);
        initViews();
        dataBaseTasks = new DataBaseTasks(Tasks_Activity.this);

        addTasks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksClass tasksClass = new TasksClass(tasks_name.getText().toString(),
                        admin_name.getText().toString(), -1);

                boolean success = dataBaseTasks.addOne(tasksClass);
                Toast.makeText(getApplicationContext(), "Success " + success, Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }

    private void initViews() {
        admin_name = findViewById(R.id.Admin_name);
        tasks_name = findViewById(R.id.Enter_Tasks_Name);
        addTasks_btn = findViewById(R.id.add_Tasks_button);
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

}