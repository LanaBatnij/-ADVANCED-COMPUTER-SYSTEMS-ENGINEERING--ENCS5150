package com.example.project_1200308_1201738.DataBases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.project_1200308_1201738.Models.Pizza;

public class PizzaDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pizza_db";
    private static final int DATABASE_VERSION = 2; // Increment version to force upgrade
    private static final String TABLE_PIZZAS = "pizzas";
    private static final String TABLE_ORDERS = "orders";
    private static final String TABLE_FAVORITES = "favorites";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_SIZES = "sizes";
    private static final String COLUMN_PRICES = "prices";
    private static final String COLUMN_IS_FAVORITE = "is_favorite";

    private static final String COLUMN_PIZZA_ID = "pizza_id";
    private static final String COLUMN_SIZE = "size";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DATE_TIME = "date_time";

    public PizzaDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        String CREATE_PIZZA_TABLE = "CREATE TABLE " + TABLE_PIZZAS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_SIZES + " TEXT, " +
                COLUMN_PRICES + " TEXT, " +
                COLUMN_IS_FAVORITE + " INTEGER DEFAULT 0)";
        db.execSQL(CREATE_PIZZA_TABLE);

        String CREATE_FAVORITES_TABLE = "CREATE TABLE " + TABLE_FAVORITES + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pizza_id INTEGER," +
                "FOREIGN KEY(pizza_id) REFERENCES pizzas(id)" +
                ")";
        db.execSQL(CREATE_FAVORITES_TABLE);

        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PIZZA_ID + " INTEGER, " +
                COLUMN_SIZE + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_DATE_TIME + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_PIZZA_ID + ") REFERENCES " + TABLE_PIZZAS + "(id))";
        db.execSQL(CREATE_ORDERS_TABLE);

        Log.d("PizzaDatabaseHelper", "Tables created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PIZZAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!isTableExists(db, TABLE_FAVORITES)) {
            createTables(db);
        }
    }

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void addFavorite(int pizzaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pizza_id", pizzaId);
        long result = db.insert(TABLE_FAVORITES, null, values);
        if (result != -1) {
            Log.d("PizzaDatabaseHelper", "Favorite added: pizza_id=" + pizzaId);
        } else {
            Log.e("PizzaDatabaseHelper", "Failed to add favorite: pizza_id=" + pizzaId);
        }
        db.close();
    }

    public Cursor getAllFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM favorites INNER JOIN pizzas ON favorites.pizza_id = pizzas.id", null);
    }

    // Method to remove a favorite
    public void removeFavorite(int pizzaId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("favorites", "pizza_id = ?", new String[]{String.valueOf(pizzaId)});
        db.close();
    }


    public void insertPizza(Pizza pizza) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, pizza.getName());
        db.insert(TABLE_PIZZAS, null, values);
        db.close();
    }

    public void updatePizza(String name, String description, String category, String[] sizes, double[] prices) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_CATEGORY, category);
        values.put(COLUMN_SIZES, String.join(",", sizes));
        values.put(COLUMN_PRICES, doubleArrayToString(prices));
        db.update(TABLE_PIZZAS, values, COLUMN_NAME + " = ?", new String[]{name});
        db.close();
    }

    public void UpdateInitialPizzas() {
        updatePizza("Margarita", "Classic pizza with tomato sauce, mozzarella cheese, and fresh basil.", "veggies",
                new String[]{"Small", "Medium", "Large"}, new double[]{20, 30, 40});
        updatePizza("Neapolitan", "Traditional pizza with tomato sauce, mozzarella cheese, and anchovies.", "others",
                new String[]{"Small", "Medium", "Large"}, new double[]{30, 35, 45});
        updatePizza("Hawaiian", "Pizza topped with ham and pineapple.", "others",
                new String[]{"Small", "Medium", "Large"}, new double[]{20, 35, 40});
        updatePizza("Pepperoni", "Pizza topped with pepperoni slices and mozzarella cheese.", "beef",
                new String[]{"Small", "Medium", "Large"}, new double[]{30, 40, 50});
        updatePizza("New York Style", "Thin crust pizza with tomato sauce and mozzarella cheese.", "others",
                new String[]{"Small", "Medium", "Large"}, new double[]{35, 45, 55});
        updatePizza("Calzone", "Folded pizza filled with ricotta cheese, ham, and mozzarella.", "others",
                new String[]{"Small", "Medium", "Large"}, new double[]{20, 25, 35});
        updatePizza("Tandoori Chicken Pizza", "Pizza topped with tandoori chicken, onions, and green peppers.", "chicken",
                new String[]{"Small", "Medium", "Large"}, new double[]{35, 45, 55});
        updatePizza("BBQ Chicken Pizza", "Pizza topped with BBQ sauce, chicken, and red onions.", "chicken",
                new String[]{"Small", "Medium", "Large"}, new double[]{45, 55, 70});
        updatePizza("Seafood Pizza", "Pizza topped with shrimp, calamari, and mozzarella cheese.", "others",
                new String[]{"Small", "Medium", "Large"}, new double[]{6.99, 8.99, 10.99});
        updatePizza("Vegetarian Pizza", "Pizza topped with bell peppers, onions, mushrooms, and olives.", "veggies",
                new String[]{"Small", "Medium", "Large"}, new double[]{90, 100, 115});
        updatePizza("Buffalo Chicken Pizza", "Pizza topped with buffalo chicken, mozzarella cheese, and blue cheese.", "chicken",
                new String[]{"Small", "Medium", "Large"}, new double[]{50, 60, 70});
        updatePizza("Mushroom Truffle Pizza", "Pizza topped with truffle oil, mushrooms, and mozzarella cheese.", "veggies",
                new String[]{"Small", "Medium", "Large"}, new double[]{30, 40, 50});
        updatePizza("Pesto Chicken Pizza", "Pizza topped with pesto sauce, chicken, and sun-dried tomatoes.", "chicken",
                new String[]{"Small", "Medium", "Large"}, new double[]{40, 50, 60});
    }

    public Cursor getAllPizzas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PIZZAS, null);
    }

    private String doubleArrayToString(double[] array) {
        StringBuilder sb = new StringBuilder();
        for (double value : array) {
            sb.append(value).append(",");
        }
        sb.deleteCharAt(sb.length() - 1); // Remove the last comma
        return sb.toString();
    }

    public void logAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERS, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int pizzaId = cursor.getInt(cursor.getColumnIndex("pizza_id"));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex("size"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String dateTime = cursor.getString(cursor.getColumnIndex("date_time"));
                Log.d("PizzaDatabaseHelper", "Order: id=" + id + ", pizzaId=" + pizzaId + ", size=" + size + ", price=" + price + ", dateTime=" + dateTime);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void insertOrder(int pizzaId, String size, double price, String dateTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PIZZA_ID, pizzaId);
        values.put(COLUMN_SIZE, size);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DATE_TIME, dateTime);
        long result = db.insert(TABLE_ORDERS, null, values);
        db.close();
        if (result == -1) {
            Log.e("PizzaDatabaseHelper", "Failed to insert order");
        } else {
            Log.d("PizzaDatabaseHelper", "Order inserted successfully: size=" + size + " date_time=" + dateTime + " price=" + price + " pizza_id=" + pizzaId);
            logAllOrders(); // Log all orders after insertion
        }
    }

    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Log all pizzas for debugging
        Cursor pizzaCursor = db.rawQuery("SELECT * FROM " + TABLE_PIZZAS, null);
        if (pizzaCursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = pizzaCursor.getInt(pizzaCursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = pizzaCursor.getString(pizzaCursor.getColumnIndex("name"));
                Log.d("PizzaDatabaseHelper", "Pizza: id=" + id + ", name=" + name);
            } while (pizzaCursor.moveToNext());
        } else {
            Log.d("PizzaDatabaseHelper", "No pizzas found");
        }
        pizzaCursor.close();

        // Log all orders for debugging
        logAllOrders();

        // Execute the join query
        String query = "SELECT orders.*, pizzas.name AS pizza_name FROM orders INNER JOIN pizzas ON orders.pizza_id = pizzas.id";
        Log.d("PizzaDatabaseHelper", "Executing query: " + query);

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") int pizzaId = cursor.getInt(cursor.getColumnIndex("pizza_id"));
                @SuppressLint("Range") String size = cursor.getString(cursor.getColumnIndex("size"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String dateTime = cursor.getString(cursor.getColumnIndex("date_time"));
                @SuppressLint("Range") String pizzaName = cursor.getString(cursor.getColumnIndex("pizza_name"));
                Log.d("PizzaDatabaseHelper", "Order: id=" + id + ", pizzaId=" + pizzaId + ", size=" + size + ", price=" + price + ", dateTime=" + dateTime + ", pizzaName=" + pizzaName);
            } while (cursor.moveToNext());
        } else {
            Log.d("PizzaDatabaseHelper", "No orders found");
        }

        return cursor;
    }
}
