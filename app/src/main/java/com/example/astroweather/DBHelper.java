package com.example.astroweather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    // Table name
    public static final String TABLE_NAME = "WEATHER_DATA";

    // Columns
    public static final String _ID = "_id";
    public static final String CITY_NAME = "CITY_NAME";
    public static final String WEATHER_INFO = "WEATHER_INFO";

    // DB info
    public static final String DB_NAME = "WEATHER";

    //DB version
    public static final int DB_VERSION = 1;

    //Create tabe query
    public static final String CREATE_TABLE =
            "create table" + " " + TABLE_NAME + "(" +
            _ID + " " + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            CITY_NAME + " " + "TEXT," +
            WEATHER_INFO + " " + "TEXT" + ");";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
