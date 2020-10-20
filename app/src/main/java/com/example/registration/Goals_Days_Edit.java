package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Goals_Days_Edit extends AppCompatActivity {
    private EditText editText;
    private TextView day_week_Name;
    private FloatingActionButton addButton;
    private String day_Name;

    private DataBaseGoals dataBaseGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals__days__edit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        day_Name = extras.getString("DayToEdit");
        editText = findViewById(R.id.Goals_editable_text);
        day_week_Name = findViewById(R.id.Goals_days_name);
        addButton = findViewById(R.id.Goals_add_Text);
        dataBaseGoals = new DataBaseGoals(this);

        day_week_Name.setText(day_Name);
        editText.setText(dataBaseGoals.getEditedText(day_Name));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = Goals_Days_Edit.this.getCurrentFocus();
                String text = editText.getText().toString();
                dataBaseGoals.updateGoalsEditText(day_Name, text);
                InputMethodManager trr = (InputMethodManager) Goals_Days_Edit.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                trr.hideSoftInputFromWindow(view.getWindowToken(), 0);

                finish();

            }
        });

    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                String text = editText.getText().toString();
                dataBaseGoals.updateGoalsEditText(day_Name, text);
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
