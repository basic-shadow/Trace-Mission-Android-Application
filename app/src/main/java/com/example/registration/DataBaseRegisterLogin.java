package com.example.registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseRegisterLogin extends SQLiteOpenHelper {

    public static final String LOGIN_TABLE = "TASKS_TABLE";
    public static final String LOGIN_FIRSTNAME = "LOGIN_FIRSTNAME";
    public static final String LOGIN_LASTNAME = "LOGIN_LASTNAME";
    public static final String LOGIN_PHONE = "LOGIN_PHONE";
    public static final String LOGIN_PASSWORD = "LOGIN_PASSWORD";

    public DataBaseRegisterLogin(@Nullable Context context) {
        super(context, "accounts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + LOGIN_TABLE + " (" + LOGIN_FIRSTNAME
                + " TEXT, " + LOGIN_LASTNAME + " TEXT, "
                + LOGIN_PHONE + " TEXT, " + LOGIN_PASSWORD + " TEXT " + " )";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addOne(AccountsClass accountsClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(LOGIN_FIRSTNAME, accountsClass.getFirstName());
        cv.put(LOGIN_LASTNAME, accountsClass.getLastName());
        cv.put(LOGIN_PHONE, accountsClass.getPhone());
        cv.put(LOGIN_PASSWORD, accountsClass.getPassword());

        long num = db.insert(LOGIN_TABLE, null, cv);
        if (num == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<AccountsClass> getEveryList() {
        List<AccountsClass> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + LOGIN_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                String firstName = cursor.getString(0);
                String lastName = cursor.getString(1);
                String phone = cursor.getString(2);
                String password = cursor.getString(3);

                AccountsClass accountsClass = new AccountsClass(firstName, lastName, phone, password);
                returnList.add(accountsClass);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean removeOne(AccountsClass accountsClass) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + LOGIN_TABLE + " WHERE " + LOGIN_FIRSTNAME +
                " = " + accountsClass.getFirstName() + " AND " + LOGIN_LASTNAME + " = " + accountsClass.getLastName();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        } else
            return false;
    }

    public boolean getLogin(String phone, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectText = "SELECT " + LOGIN_PHONE + " FROM " + LOGIN_TABLE
                + " WHERE " + LOGIN_PHONE + " = ? " + " AND " + LOGIN_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(selectText, new  String[] {phone, password});
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
