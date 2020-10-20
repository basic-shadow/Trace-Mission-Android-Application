package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskName_Activity extends AppCompatActivity {
    private EditText editText;
    private TextView taskName;
    private FloatingActionButton addButton;
    private String Task_Name;

    private DataBaseTasks dataBaseTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        Task_Name = extras.getString("Task_Name");          // Task_Name received from AllTasksActivity to Insert Editable_Text
        editText = findViewById(R.id.Tasks_editable_text);
        taskName = findViewById(R.id.Tasks_name);                      // taskName TextView from .xml
        addButton = findViewById(R.id.Tasks_add_Text);
        dataBaseTasks = new DataBaseTasks(this);

        taskName.setText(Task_Name);
        editText.setText(dataBaseTasks.getEditedText(Task_Name));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = TaskName_Activity.this.getCurrentFocus();
                String text = editText.getText().toString();
                dataBaseTasks.updateTasksText(Task_Name, text);
                InputMethodManager imm = (InputMethodManager) TaskName_Activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });


    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String text = editText.getText().toString();
                dataBaseTasks.updateTasksText(Task_Name, text);
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}