package com.example.registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseTasks extends SQLiteOpenHelper {

    public static final String TASKS_TABLE = "TASKS_TABLE";
    public static final String TASKS_ID = "ID";
    public static final String TASKS_NAME = "TASKS_NAME";
    public static final String TASKS_ADMIN = "TASKS_ADMIN";
    public static final String TASKS_DATE = "TASKS_DATE";
    public static final String TASKS_EDITED_TEXT = "TASKS_EDITED_TEXT";

    public DataBaseTasks(@Nullable Context context) {
        super(context, "task.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TASKS_TABLE + " (" + TASKS_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TASKS_NAME + " TEXT, "
                + TASKS_ADMIN + " TEXT, " + TASKS_DATE + " TEXT, " + TASKS_EDITED_TEXT + " TEXT " +" )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addOne(TasksClass tasksClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TASKS_NAME, tasksClass.getName());
        cv.put(TASKS_ADMIN, tasksClass.getAdmin());
        cv.put(TASKS_DATE, tasksClass.getDate());

        long num = db.insert(TASKS_TABLE, null, cv);
        if (num == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<TasksClass> getEveryList() {
        List<TasksClass> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TASKS_TABLE;

        SQLiteDatabase db1 = this.getReadableDatabase();

        Cursor cursor = db1.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int tasksID = cursor.getInt(0);

                String taskName = cursor.getString(1);
                String adminName = cursor.getString(2);
                //String date_str = cursor.getString(3);

                TasksClass tasksClass = new TasksClass(taskName, adminName, tasksID);

                returnList.add(tasksClass);


            } while (cursor.moveToNext());
        }
        cursor.close();
        db1.close();
        return returnList;
    }

    public boolean removeOne(TasksClass tasksClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TASKS_TABLE + " WHERE " + TASKS_ID +
                " = " + tasksClass.getId();


        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else
            return false;
    }

    public void changeID(ArrayList<TasksClass> tasksClasses) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < tasksClasses.size(); i++) {
            cv.put(TASKS_ID, i + 1);
            db1.update(TASKS_TABLE, cv, TASKS_NAME + "= ? ", new String[] {tasksClasses.get(i).getName()});

        }
        db1.close();
    }

    public void updateTasksText(String taskName, String text) {
        SQLiteDatabase db = this.getWritableDatabase();

        String taskNameSql = "\'" + taskName + "\'";
        String textSql = "\'" + text + "\'";

        String update = "UPDATE " + TASKS_TABLE + " SET " + TASKS_EDITED_TEXT + " = "
                + textSql + " WHERE " + TASKS_NAME + " LIKE " + taskNameSql;

        db.execSQL(update);
        db.close();
    }

    public String getEditedText(String taskName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectText = "SELECT " + TASKS_EDITED_TEXT + " FROM " + TASKS_TABLE
                + " WHERE " + TASKS_NAME + " = ?";

        Cursor cursor = db.rawQuery(selectText, new  String[] {taskName});
        if (cursor.moveToFirst())
            return cursor.getString(0);

        cursor.close();
        db.close();
        return "";
    }
}
