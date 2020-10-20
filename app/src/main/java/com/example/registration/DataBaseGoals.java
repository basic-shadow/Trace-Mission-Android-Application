package com.example.registration;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseGoals extends SQLiteOpenHelper {

    public static final String GOALS_TABLE = "GOALS_TABLE";
    public static final String GOALS_DAYS = "GOALS_DAYS";
    public static final String GOALS_EDITED_TEXT = "GOALS_EDITED_TEXT";

    public DataBaseGoals(@Nullable Context context) {
        super(context, "goals.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + GOALS_TABLE + " (" + GOALS_DAYS + " TEXT, "
                + GOALS_EDITED_TEXT + " TEXT " +" )";
        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addOne(GoalsClass goalsClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(GOALS_DAYS, goalsClass.getDay());
        cv.put(GOALS_EDITED_TEXT, goalsClass.getTodo_list());

        long num = db.insert(GOALS_TABLE, null, cv);
        if (num == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<GoalsClass> getEveryList() {
        ArrayList<GoalsClass> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + GOALS_TABLE;

        SQLiteDatabase db1 = this.getReadableDatabase();

        Cursor cursor = db1.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String days = cursor.getString(0);
                String editedText = cursor.getString(1);
                GoalsClass goalsClass = new GoalsClass(days);
                goalsClass.setTodo_list(editedText);

                returnList.add(goalsClass);

            } while (cursor.moveToNext());

        }

        cursor.close();
        db1.close();
        return returnList;
    }

    public void updateGoalsEditText(String daysName, String text) {
        SQLiteDatabase db = this.getWritableDatabase();

        String daysNameSql = "\'" + daysName + "\'";
        String textSql = "\'" + text + "\'";

        String update = "UPDATE " + GOALS_TABLE + " SET " + GOALS_EDITED_TEXT + " = "
                + textSql + " WHERE " + GOALS_DAYS + " LIKE " + daysNameSql;

        db.execSQL(update);
        db.close();
    }

    public String getEditedText(String daysName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectText = "SELECT " + GOALS_EDITED_TEXT + " FROM " + GOALS_TABLE
                + " WHERE " + GOALS_DAYS + " = ?";

        Cursor cursor = db.rawQuery(selectText, new  String[] {daysName});
        if (cursor.moveToFirst())
            return cursor.getString(0);

        cursor.close();
        db.close();
        return "";
    }

}
