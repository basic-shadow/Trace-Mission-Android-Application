package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AppLoginEntrance extends AppCompatActivity {
    private EditText phone, password;
    private ArrayList<AccountsClass> accountsClasses;
    private DataBaseRegisterLogin dataBaseRegisterLogin;
    private Button loginBtn;
    private TextView registerBtn;
    private TextView incorrectEntrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_login_entrance);

        phone = findViewById(R.id.phone_number_entrance);
        password = findViewById(R.id.password_entrance);
        dataBaseRegisterLogin = new DataBaseRegisterLogin(this);
        accountsClasses = new ArrayList<>();
        accountsClasses = (ArrayList<AccountsClass>) dataBaseRegisterLogin.getEveryList();
        loginBtn = findViewById(R.id.sign_in_btn);
        registerBtn = findViewById(R.id.sign_up_btn);
        incorrectEntrance = findViewById(R.id.wrong_entrance);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataBaseRegisterLogin.getLogin(phone.getText().toString(), password.getText().toString())) {
                    Intent intent = new Intent(AppLoginEntrance.this, MainActivity.class);
                    AppLoginEntrance.this.startActivity(intent);
                    finish();
                }
                else {
                    incorrectEntrance.setVisibility(View.VISIBLE);
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppLoginEntrance.this, Register_Account_Activity.class);
                AppLoginEntrance.this.startActivity(intent);
                finish();
            }
        });

    }
}