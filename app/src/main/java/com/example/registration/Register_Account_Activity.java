package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Register_Account_Activity extends AppCompatActivity {
    private EditText firstName, lastName, phoneNumber, password;
    private Button registerBtn;
    private ArrayList<AccountsClass> accountsClasses;
    private DataBaseRegisterLogin dataBaseRegisterLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = findViewById(R.id.txtFirstName);
        lastName = findViewById(R.id.txtLastName);
        phoneNumber = findViewById(R.id.txtPhoneNumber);
        password = findViewById(R.id.password_txt);
        registerBtn = findViewById(R.id.button_register);
        accountsClasses = new ArrayList<>();
        dataBaseRegisterLogin = new DataBaseRegisterLogin(this);
        accountsClasses = (ArrayList<AccountsClass>) dataBaseRegisterLogin.getEveryList();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountsClass accountsClass = new AccountsClass(firstName.getText().toString(),
                        lastName.getText().toString(), phoneNumber.getText().toString(), password.getText().toString());
                dataBaseRegisterLogin.addOne(accountsClass);
                Intent intent = new Intent(Register_Account_Activity.this, MainActivity.class);
                Register_Account_Activity.this.startActivity(intent);
                finish();
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
}