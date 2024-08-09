package com.example.project_1200308_1201738.DataBases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project_1200308_1201738.Models.User;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "phone TEXT," +
                "firstName TEXT," +
                "lastName TEXT," +
                "gender TEXT," +
                "password TEXT" +
                ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("phone", user.getPhoneNumber());
        values.put("firstName", user.getFirstName());
        values.put("lastName", user.getLastName());
        values.put("gender", user.getGender());
        values.put("password", user.getPassword());

        long result = db.insert("users", null, values);
        db.close();
        return result != -1;
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", new String[]{"id"}, "email=?", new String[]{email}, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);
        if (cursor != null) cursor.close();
        db.close();
        return exists;
    }

    public String getPasswordHash(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", new String[]{"password"}, "email=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String passwordHash = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            return passwordHash;
        }
        return null;
    }
    public boolean updateUser(String email, String firstName, String lastName, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        values.put("phone", phone);
        values.put("password", password);

        int rows = db.update("users", values, "email=?", new String[]{email});
        db.close();
        return rows > 0;
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("users", null, "email=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex("firstName"));
            @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex("lastName"));
            @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("gender"));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            return new User( email, phone, firstName, lastName, gender, password);
        }
        return null;
    }
}
