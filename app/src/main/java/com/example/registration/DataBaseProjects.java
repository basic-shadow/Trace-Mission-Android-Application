package com.example.registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseProjects extends SQLiteOpenHelper {

    public static final String PROJECT_TABLE = "PROJECT_TABLE";
    public static final String PROJECT_ID = "ID";
    public static final String PROJECT_NAME = "PROJECT_NAME";
    public static final String PROJECT_ADMIN = "PROJECT_ADMIN";
    public static final String PROJECT_DATE = "PROJECT_DATE";
    public static final String PROJECT_PIN_CODE = "PROJECT_PIN_CODE";
    public static final String PROJECT_DIRECTORY = "PROJECT_DIRECTORY";
    public static final String PROJECT_FILES = "PROJECT_FILES";
    public static final String PROJECT_EDITED_TEXT = "PROJECT_EDITED_TEXT";

    public DataBaseProjects(@Nullable Context context) {
        super(context, "project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + PROJECT_TABLE + " (" + PROJECT_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROJECT_NAME + " TEXT, "
                + PROJECT_ADMIN + " TEXT, " + PROJECT_PIN_CODE + " TEXT, " + PROJECT_DATE + " TEXT, "
                + PROJECT_DIRECTORY + " TEXT, " + PROJECT_FILES + " TEXT, " + PROJECT_EDITED_TEXT
                + " TEXT " + " )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addOne(ProjectsClass projectsClass, String directory_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PROJECT_NAME, projectsClass.getName());
        cv.put(PROJECT_ADMIN, projectsClass.getAdmin());
        cv.put(PROJECT_PIN_CODE, projectsClass.getPin_code());
        cv.put(PROJECT_DATE, projectsClass.getDates());
        cv.put(PROJECT_DIRECTORY, directory_name);

        long num = db.insert(PROJECT_TABLE, null, cv);
        if (num == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addOne(ProjectsClass projectsClass, String directory_name, String fileName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PROJECT_NAME, projectsClass.getName());
        cv.put(PROJECT_ADMIN, projectsClass.getAdmin());
        cv.put(PROJECT_PIN_CODE, projectsClass.getPin_code());
        cv.put(PROJECT_DATE, projectsClass.getDates());
        cv.put(PROJECT_DIRECTORY, directory_name);
        cv.put(PROJECT_FILES, fileName);

        long num = db.insert(PROJECT_TABLE, null, cv);
        if (num == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<ProjectsClass> getEveryList() {
        List<ProjectsClass> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + PROJECT_TABLE;

        SQLiteDatabase db1 = this.getReadableDatabase();

        Cursor cursor = db1.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int projectID = cursor.getInt(0);

                String projectName = cursor.getString(1);
                String adminName = cursor.getString(2);
                String pin_code = cursor.getString(3);
                //String date_str = cursor.getString(4);

                ProjectsClass projectsClass = new ProjectsClass(projectName, adminName, projectID, pin_code,
                        R.drawable.project_view_foreground);

                returnList.add(projectsClass);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db1.close();
        return returnList;
    }

    public List<ProjectsDirectoryClass> getDirectoryList() {
        List<ProjectsDirectoryClass> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + PROJECT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String projectName = cursor.getString(1);

                String directoryName = cursor.getString(5);
                String fileName = cursor.getString(6);
                ProjectsDirectoryClass projectsDirectoryClass = new ProjectsDirectoryClass(projectName, directoryName,
                        fileName, R.drawable.directory_project, R.drawable.assignment);

                returnList.add(projectsDirectoryClass);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean containsProject(ProjectsClass projectsClass) {
        String queryString = "SELECT * FROM " + PROJECT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int projectId = cursor.getInt(0);
                String projectNameInDb = cursor.getString(1);
                if (projectId == projectsClass.getId())
                    break;
                if (projectNameInDb.equals(projectsClass.getName())) {
                    cursor.close();
                    db.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean containsDir(ProjectsClass projectsClass, String dirName) {
        String queryString = "SELECT * FROM " + PROJECT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String projectNameInDb = cursor.getString(1);
                String projectDirNameInDb = cursor.getString(5);
                if (id == projectsClass.getId())
                    break;
                if (projectNameInDb.equals(projectsClass.getName()) &&
                        projectDirNameInDb.equals(dirName)) {
                    cursor.close();
                    db.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean containsFile(ProjectsClass projectsClass, String dirName, String fileName) {
        String queryString = "SELECT * FROM " + PROJECT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String projectNameInDb = cursor.getString(1);
                String projectDirNameInDb = cursor.getString(5);
                String projectFileNameInDb = cursor.getString(6);
                if (id == projectsClass.getId())
                    break;
                if (projectNameInDb.equals(projectsClass.getName()) &&
                        projectDirNameInDb.equals(dirName) &&
                        projectFileNameInDb.equals(fileName)) {
                    cursor.close();
                    db.close();
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return false;
    }

    public boolean removeOne(ProjectsClass projectsClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "\'" + projectsClass.getName() + "\'";
        String queryString = "DELETE FROM " + PROJECT_TABLE + " WHERE " + PROJECT_NAME +
                " = " + sql;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else
            return false;
    }

    public boolean removeOne(String projectDirectory) {

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "\'" + projectDirectory + "\'";
        String queryString = "DELETE FROM " + PROJECT_TABLE + " WHERE " + PROJECT_DIRECTORY +
                " = " + sql;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return false;
        } else
            return true;
    }

    public boolean removeAssignment(String assignment) {

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "\'" + assignment + "\'";
        String queryString = "DELETE FROM " + PROJECT_TABLE + " WHERE " + PROJECT_FILES +
                " = " + sql;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return false;
        } else
            return true;
    }

    public void changeID(ArrayList<ProjectsClass> projectsClasses) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        for (int i = 0; i < projectsClasses.size(); i++) {
            cv.put(PROJECT_ID, i + 1);
            db1.update(PROJECT_TABLE, cv, PROJECT_NAME + "= ? ", new String[]{projectsClasses.get(i).getName()});

        }
    }

    public void updateFilesText(String fileName, String text) {
        SQLiteDatabase db = this.getWritableDatabase();

        String fileNameSql = "\'" + fileName + "\'";
        String textSql = "\'" + text + "\'";

        String update = "UPDATE " + PROJECT_TABLE + " SET " + PROJECT_EDITED_TEXT + " = "
                + textSql + " WHERE " + PROJECT_FILES + " LIKE " + fileNameSql;

        db.execSQL(update);
        db.close();
    }

    public String getEditedText(String dirName, String fileName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectText = "SELECT " + PROJECT_EDITED_TEXT + " FROM " + PROJECT_TABLE
                + " WHERE " + PROJECT_FILES + " = ? " + " AND " + PROJECT_DIRECTORY + " = ?";

        String[] query = {fileName, dirName};

        Cursor cursor = db.rawQuery(selectText, query);
        if (cursor.moveToFirst())
            return cursor.getString(0);

        cursor.close();
        db.close();
        return "";
    }

    public boolean checkPin_code(String projectName, String pin_code) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectText = "SELECT " + PROJECT_PIN_CODE + " FROM " + PROJECT_TABLE
                + " WHERE " + PROJECT_NAME + " = ? " + " AND " + PROJECT_PIN_CODE + " = ?";

        String[] query = {projectName, pin_code};

        Cursor cursor = db.rawQuery(selectText, query);
        if (cursor.moveToFirst()) {
            cursor.getString(0);
            cursor.close();
            db.close();
            return true;
        }

        cursor.close();
        db.close();
        return false;
    }
}
