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
    private static final int DATABASE_VERSION = 10;

    public static final String TABLE_RESERVATIONS = "reservations";
    public static final String TABLE_MENU = "menu";

    public static final String KEY_ID = "id";

    public static final String KEY_RESERVATION_USERNAME = "username";
    public static final String KEY_RESERVATION_DATE = "date";
    public static final String KEY_RESERVATION_TIME = "time";
    public static final String KEY_RESERVATION_GUESTS = "guests";
    public static final String KEY_RESERVATION_DETAILS = "reservation_details";

    public static final String KEY_MENU_NAME = "name";
    public static final String KEY_MENU_PRICE = "price";
    public static final String KEY_MENU_IMAGE = "image";
    public static final String KEY_MENU_CATEGORY = "category";

    private static final String CREATE_TABLE_RESERVATIONS = "CREATE TABLE "
            + TABLE_RESERVATIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_RESERVATION_USERNAME + " TEXT,"
            + KEY_RESERVATION_DATE + " TEXT,"
            + KEY_RESERVATION_TIME + " TEXT,"
            + KEY_RESERVATION_GUESTS + " INTEGER,"
            + KEY_RESERVATION_DETAILS + " TEXT)";

    private static final String CREATE_TABLE_MENU = "CREATE TABLE " + TABLE_MENU + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_MENU_NAME + " TEXT,"
            + KEY_MENU_PRICE + " REAL,"
            + KEY_MENU_IMAGE + " TEXT,"
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

    public void addReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RESERVATION_USERNAME, reservation.getUsername());
        values.put(KEY_RESERVATION_DATE, reservation.getDate());
        values.put(KEY_RESERVATION_TIME, reservation.getTime());
        values.put(KEY_RESERVATION_GUESTS, reservation.getGuests());
        values.put(KEY_RESERVATION_DETAILS, reservation.getReservationDetails());
        db.insert(TABLE_RESERVATIONS, null, values);
        db.close();
    }

    private List<Reservation> getReservationsFromCursor(Cursor cursor) {
        List<Reservation> reservationList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Reservation reservation = new Reservation(
                        cursor.getLong(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESERVATION_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESERVATION_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESERVATION_TIME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_RESERVATION_GUESTS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_RESERVATION_DETAILS))
                );
                reservationList.add(reservation);
            } while (cursor.moveToNext());
        }
        return reservationList;
    }

    public List<Reservation> getReservationsForUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESERVATIONS, null, KEY_RESERVATION_USERNAME + " = ?", new String[]{username}, null, null, null);
        List<Reservation> reservations = getReservationsFromCursor(cursor);
        cursor.close();
        db.close();
        return reservations;
    }

    public List<Reservation> getReservationsByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RESERVATIONS, null, KEY_RESERVATION_DATE + " = ?", new String[]{date}, null, null, null);
        List<Reservation> reservations = getReservationsFromCursor(cursor);
        cursor.close();
        db.close();
        return reservations;
    }

    public int updateReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RESERVATION_USERNAME, reservation.getUsername());
        values.put(KEY_RESERVATION_DATE, reservation.getDate());
        values.put(KEY_RESERVATION_TIME, reservation.getTime());
        values.put(KEY_RESERVATION_GUESTS, reservation.getGuests());
        values.put(KEY_RESERVATION_DETAILS, reservation.getReservationDetails());

        return db.update(TABLE_RESERVATIONS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
    }

    public void deleteReservation(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESERVATIONS, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

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

    public List<Menu> getMenuItemsByCategory(String category) {
        List<Menu> menuList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_MENU + " WHERE " + KEY_MENU_CATEGORY + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{category});

        if (cursor.moveToFirst()) {
            do {
                Menu menu = new Menu(
                        cursor.getLong(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_MENU_NAME)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_MENU_PRICE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_MENU_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_MENU_CATEGORY))
                );
                menuList.add(menu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return menuList;
    }

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

    public void deleteMenuItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENU, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
