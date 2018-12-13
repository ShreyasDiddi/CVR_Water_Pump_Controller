package com.a6studios.cvr_waterpumpcontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper  extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "pumps_db";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Pump.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Pump.TABLE_NAME);

    }

    public long insertPump(String title,String content) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Pump.COLUMN_LABEL, title);
        values.put(Pump.COLUMN_PHONENUMBER, content);


        long id = db.insert(Pump.TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public Pump getPump (long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Pump.TABLE_NAME,
                new String[]{Pump.COLUMN_ID,Pump.COLUMN_LABEL,Pump.COLUMN_PHONENUMBER},
                Pump.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Pump pump = new Pump(
                cursor.getInt(cursor.getColumnIndex(Pump.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Pump.COLUMN_LABEL)),
                cursor.getString(cursor.getColumnIndex(Pump.COLUMN_PHONENUMBER)));

        cursor.close();

        return pump;
    }

    public List<Pump> getAllPumps() {
        List<Pump> blogs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Pump.TABLE_NAME ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Pump pump = new Pump(
                        cursor.getInt(cursor.getColumnIndex(Pump.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Pump.COLUMN_LABEL)),
                        cursor.getString(cursor.getColumnIndex(Pump.COLUMN_PHONENUMBER)));

                blogs.add(pump);
            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        return blogs;
    }

    public int updatePump(Pump b) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Pump.COLUMN_LABEL, b.getLabel());
        values.put(Pump.COLUMN_PHONENUMBER, b.getPhno());

        // updating row
        return db.update(Pump.TABLE_NAME, values, Pump.COLUMN_ID + " = ?",
                new String[]{String.valueOf(b.getId())});
    }

    public void deletePump(Pump b) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(b.TABLE_NAME, b.COLUMN_ID + " = ?",
                new String[]{String.valueOf(b.getId())});
        db.close();
    }

}
