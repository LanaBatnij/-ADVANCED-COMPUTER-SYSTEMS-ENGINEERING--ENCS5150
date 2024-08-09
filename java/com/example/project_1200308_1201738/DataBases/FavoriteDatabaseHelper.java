package com.example.project_1200308_1201738.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.project_1200308_1201738.Models.PizzaDetails;

public class FavoriteDatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "favorites_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FAVORITES = "favorites";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_SIZES = "sizes";
    private static final String COLUMN_PRICES = "prices";

    public FavoriteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_SIZES + " TEXT, " +
                COLUMN_PRICES + " TEXT)";
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        onCreate(db);
    }

    public void insertFavorite(PizzaDetails pizza) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pizza.getName());
        values.put(COLUMN_DESCRIPTION, pizza.getDescription());
        values.put(COLUMN_CATEGORY, pizza.getCategory());
        values.put(COLUMN_SIZES, String.join(",", pizza.getSizes()));
        values.put(COLUMN_PRICES, doubleArrayToString(pizza.getPrices()));
        db.insert(TABLE_FAVORITES, null, values);
        db.close();
    }
    public Cursor getAllFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null);
    }

    private String doubleArrayToString(double[] array) {
        StringBuilder result = new StringBuilder();
        for (double value : array) {
            result.append(value).append(",");
        }
        return result.toString().substring(0, result.length() - 1); // Remove the trailing comma
    }
}
