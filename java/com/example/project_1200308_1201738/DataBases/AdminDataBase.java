package com.example.project_1200308_1201738.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdminDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "admin_db";
    private static final int DATABASE_VERSION = 2;

    public AdminDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE admin (email TEXT PRIMARY KEY, password TEXT, FirstName TEXT, LastName TEXT, PhoneNumber TEXT, Gender TEXT, Country TEXT, City TEXT)");
        String email = "lanabatnij@example.com";
        String password = "Lana&&11"; // set a default password

        Log.d("AdminDataBase", "Static Admin Password: " + password);
        sqLiteDatabase.execSQL("INSERT INTO admin (email, password, FirstName, LastName, PhoneNumber, Gender, Country, City) VALUES ('" + email + "', '" + password + "', 'Lana', 'Batnij', '05123456789', 'Female', 'Palestine', 'Ramallah')");

        String CREATE_SPECIAL_OFFERS_TABLE = "CREATE TABLE special_offers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pizza_types TEXT," +
                "sizes TEXT," +
                "offer_period TEXT," +
                "total_price REAL)";
        sqLiteDatabase.execSQL(CREATE_SPECIAL_OFFERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS admin");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS special_offers");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database downgrade
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insertAdmin(String email, String password, String firstName, String lastName, String phoneNumber, String gender, String country, String city) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("FirstName", firstName);
        contentValues.put("LastName", lastName);
        contentValues.put("PhoneNumber", phoneNumber);
        contentValues.put("Gender", gender);
        contentValues.put("Country", country);
        contentValues.put("City", city);
        long result = sqLiteDatabase.insert("admin", null, contentValues);
        if (result == -1) {
            Log.e("AdminDataBase", "Failed to insert admin");
        } else {
            Log.d("AdminDataBase", "Inserted admin successfully");
        }
    }

    public Cursor getAllAdmins() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM admin", null);
    }

    public void addSpecialOffer(String pizzaTypes, String sizes, String offerPeriod, double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pizza_types", pizzaTypes);
        values.put("sizes", sizes);
        values.put("offer_period", offerPeriod);
        values.put("total_price", totalPrice);
        db.insert("special_offers", null, values);
        db.close();
    }

    public Cursor getAllSpecialOffers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM special_offers", null);
    }

    public Cursor getAdmin(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM admin WHERE email = ?", new String[]{email});
    }

    public void updateAdmin(String email, String firstName, String lastName, String phoneNumber, String newEmail) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", newEmail);
        contentValues.put("FirstName", firstName);
        contentValues.put("LastName", lastName);
        contentValues.put("PhoneNumber", phoneNumber);
        sqLiteDatabase.update("admin", contentValues, "email = ?", new String[]{email});
    }
}
