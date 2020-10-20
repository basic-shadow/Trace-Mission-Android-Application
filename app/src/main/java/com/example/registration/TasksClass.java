package com.example.registration;

import java.text.SimpleDateFormat;

public class TasksClass {
    private String name;
    private String admin;
    private String date;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TasksClass(String name, String admin, int id) {
        this.name = name;
        this.admin = admin;
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dtr = new SimpleDateFormat("yyyy/MM/dd");
        this.date = dtr.format(date);
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return "ID -> " + getId() + ":\t\t\t" + getName() + "\n" + "Admin:\t\t\t" +getAdmin();
    }
}
