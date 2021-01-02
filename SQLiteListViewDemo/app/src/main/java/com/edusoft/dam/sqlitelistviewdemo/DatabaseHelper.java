package com.edusoft.dam.sqlitelistviewdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "Cafetero";
    private static final String COL1 = "Id"; //esta es columna 0
    private static final String COL2 = "Name"; //esta es columna 1

    public DatabaseHelper(@Nullable Context context) { //recibirá el Activity
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean addData(String item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);

        Log.d(TAG, "addData: " + item + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);


        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Cursor getData() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        return cursor;

    }

    public Cursor getItemId(String name) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String query = "SELECT " + COL1 + " from " + TABLE_NAME + " WHERE " + COL2 + " = '" + name + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        return cursor;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COL2
                + " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" + " AND "
                + COL2 + " = '" + oldName + "'";

        Log.d(TAG, "updateName:  query : " + query);
        Log.d(TAG, "updateName: setting name to " + newName  );
        sqLiteDatabase.execSQL(query);

        //UPDATE TABLE SET COL2 = 'EDU' WHERE COL1 = 'ID' AND COL2 = 'OLDNAME'
    }

    public void deleName(Integer id, String name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'"
                + " AND " + COL2 + " = '" + name + "'";

        //DELETE FROM TABLE WHERE COL1 = 'ID' AND COL2 = 'NAME'
        Log.d(TAG, "deleName: query: " + query);
        Log.d(TAG, "deleName: Deleting" + name + "from database.");
        db.execSQL(query);

    }

}
