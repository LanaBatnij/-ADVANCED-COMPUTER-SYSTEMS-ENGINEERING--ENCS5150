// OrderDatabaseHelper.java
package com.example.project_1200308_1201738.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OrderDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "orders_db";
    private static final int DATABASE_VERSION = 4;
    private static final String TABLE_ORDERS = "orders";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PIZZA_ID = "pizza_id";
    private static final String COLUMN_SIZE = "size";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DATE_TIME = "date_time";

    public OrderDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createOrdersTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
            createOrdersTable(db);
        }
    }

    private void createOrdersTable(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PIZZA_ID + " INTEGER, " +
                COLUMN_SIZE + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_DATE_TIME + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_PIZZA_ID + ") REFERENCES pizzas(id))";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    public void insertOrder(int pizzaId, String size, double price, String dateTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PIZZA_ID, pizzaId);
        values.put(COLUMN_SIZE, size);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DATE_TIME, dateTime);
        db.insert(TABLE_ORDERS, null, values);
        db.close();
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
    }
}
