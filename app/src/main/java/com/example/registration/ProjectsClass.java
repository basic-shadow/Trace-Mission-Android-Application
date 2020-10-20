package com.example.registration;

import android.content.SharedPreferences;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ProjectsClass {
    private String name;
    private String admin;
    private int id;
    private String pin_code;
    private String dates;

    private int drawable;

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getDates() {
        return dates;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }


    public ProjectsClass(String name, String admin, int id, String pin_code, int drawable) {
        this.name = name;
        this.admin = admin;
        this.id = id;
        this.pin_code = pin_code;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtr = new SimpleDateFormat("yyyy/MM/dd");
        this.dates = dtr.format(date);
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "ID->" + getId() + " | PROJECT: " + getName() + "\n Date: " + getDates();
    }
}
