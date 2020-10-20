package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Assignment_Edit_Text extends AppCompatActivity {
    private EditText editText;
    private TextView fileName, dirName, projectName;
    private FloatingActionButton addButton;

    private DataBaseProjects dataBaseProjects;
    private String dirToEdit, projToEdit, fileToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment__edit__text);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editText = findViewById(R.id.assignment_edit_text);
        fileName = findViewById(R.id.file_name_inFile);
        dirName = findViewById(R.id.directory_name_inFile);
        projectName = findViewById(R.id.project_name_inFile);
        addButton = findViewById(R.id.assignment_text_add);
        dataBaseProjects = new DataBaseProjects(this);

        Bundle bundle = getIntent().getExtras();
        dirToEdit = bundle.getString("passDirName");
        projToEdit = bundle.getString("passProjectName");
        fileToEdit = bundle.getString("passFileName");
        fileName.setText(fileToEdit);
        dirName.setText(dirToEdit);
        projectName.setText(projToEdit);

        editText.setText(dataBaseProjects.getEditedText(dirToEdit, fileToEdit));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = Assignment_Edit_Text.this.getCurrentFocus();
                String text = editText.getText().toString();
                dataBaseProjects.updateFilesText(fileToEdit, text);
                InputMethodManager imm = (InputMethodManager) Assignment_Edit_Text.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String text = editText.getText().toString();
                dataBaseProjects.updateFilesText(fileToEdit, text);
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}