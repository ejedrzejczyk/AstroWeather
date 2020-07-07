package com.example.astroweather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c){
        context = c;
    }

    public DBManager open(){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public long insert(String cityName, String JSONString){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.CITY_NAME, cityName);
        contentValues.put(DBHelper.WEATHER_INFO, JSONString);

        long newRowID = database.insert(DBHelper.TABLE_NAME, null, contentValues);
        return newRowID;
    }

    public Cursor fetch(String cityName){
        String[] res = {DBHelper._ID,
                        DBHelper.CITY_NAME,
                        DBHelper.WEATHER_INFO};

        String where = DBHelper.CITY_NAME + "=" + "'" + cityName + "'";

        Cursor cursor = database.query(DBHelper.TABLE_NAME, res, where, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    public void delete(String cityName){
        database.delete(DBHelper.TABLE_NAME, DBHelper.CITY_NAME + "=" + cityName, null);
    }

    public int update(String cityName, String JSONContent){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.WEATHER_INFO, JSONContent);

        return database.update(DBHelper.TABLE_NAME, contentValues, DBHelper.CITY_NAME + "=" + "'" + cityName + "'", null);
    }
}
