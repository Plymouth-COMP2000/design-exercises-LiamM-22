package com.example.androiduidesignlab;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 3;

    // Table Names
    public static final String TABLE_RESERVATIONS = "reservations";
    public static final String TABLE_MENU = "menu";

    // Common column names
    public static final String KEY_ID = "id";

    // RESERVATIONS Table - column names
    public static final String KEY_RESERVATION_DETAILS = "reservation_details";

    // MENU Table - column names
    public static final String KEY_MENU_NAME = "name";
    public static final String KEY_MENU_PRICE = "price";
    public static final String KEY_MENU_IMAGE = "image";
    public static final String KEY_MENU_CATEGORY = "category";

    // Table Create Statements
    private static final String CREATE_TABLE_RESERVATIONS = "CREATE TABLE "
            + TABLE_RESERVATIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_RESERVATION_DETAILS + " TEXT)";

    private static final String CREATE_TABLE_MENU = "CREATE TABLE " + TABLE_MENU + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MENU_NAME + " TEXT,"
            + KEY_MENU_PRICE + " REAL," + KEY_MENU_IMAGE + " TEXT,"
            + KEY_MENU_CATEGORY + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESERVATIONS);
        db.execSQL(CREATE_TABLE_MENU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        onCreate(db);
    }

    // Adding new reservation
    public void addReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RESERVATION_DETAILS, reservation.getReservationDetails());
        db.insert(TABLE_RESERVATIONS, null, values);
        db.close();
    }

    // Getting all reservations
    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_RESERVATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation(cursor.getLong(0), cursor.getString(1));
                reservationList.add(reservation);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reservationList;
    }

    // Updating single reservation
    public int updateReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RESERVATION_DETAILS, reservation.getReservationDetails());

        return db.update(TABLE_RESERVATIONS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
    }

    // Deleting single reservation
    public void deleteReservation(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESERVATIONS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // Adding new menu item
    public void addMenuItem(Menu menu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MENU_NAME, menu.getName());
        values.put(KEY_MENU_PRICE, menu.getPrice());
        values.put(KEY_MENU_IMAGE, menu.getImage());
        values.put(KEY_MENU_CATEGORY, menu.getCategory());
        db.insert(TABLE_MENU, null, values);
        db.close();
    }

    // Getting all menu items
    public List<Menu> getAllMenuItems() {
        List<Menu> menuList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_MENU;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Menu menu = new Menu(cursor.getLong(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4));
                menuList.add(menu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return menuList;
    }

    // Getting all menu items by category
    public List<Menu> getMenuItemsByCategory(String category) {
        List<Menu> menuList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MENU + " WHERE " + KEY_MENU_CATEGORY + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{category});

        if (cursor.moveToFirst()) {
            do {
                Menu menu = new Menu(cursor.getLong(0), cursor.getString(1), cursor.getDouble(2), cursor.getString(3), cursor.getString(4));
                menuList.add(menu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return menuList;
    }

    // Updating single menu item
    public int updateMenuItem(Menu menu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MENU_NAME, menu.getName());
        values.put(KEY_MENU_PRICE, menu.getPrice());
        values.put(KEY_MENU_IMAGE, menu.getImage());
        values.put(KEY_MENU_CATEGORY, menu.getCategory());

        return db.update(TABLE_MENU, values, KEY_ID + " = ?",
                new String[]{String.valueOf(menu.getId())});
    }

    // Deleting single menu item
    public void deleteMenuItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENU, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}
