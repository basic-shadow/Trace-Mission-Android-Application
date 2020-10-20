package com.example.registration;

public class GoalsClass {
    private String day;
    private String todo_list;

    public String getTodo_list() {
        return todo_list;
    }

    public void setTodo_list(String todo_list) {
        this.todo_list = todo_list;
    }

    public GoalsClass(String day) {
        this.day = day;
        this.todo_list = "○ Enter Text";
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
