package com.example.android.vaccinationplan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amit on 6/4/16.
 */
public class VaccinationDBHelper extends SQLiteOpenHelper{


    static final String DATABASE_NAME = "vaccination.db";
    /*MUST BE INCREMENTED MANUALLY WITH EVERY VERSION OF DATABASE SCHEMA*/
    private static final int DATABASE_VERSION = 1;

    
    public VaccinationDBHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOGIN_TABLE = "CREATE TABLE" + DatabaseContract.Login.TABLE_NAME + " (" +
                DatabaseContract.Login._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DatabaseContract.Login.COLUMN_EMAIL+ " TEXT NOT NULL , " +
                DatabaseContract.Login.COLUMN_PASSWORD + " TEXT NOT NULL , " +
                DatabaseContract.Login.COLUMN_NUMBER_OF_CHILDEREN+ " INTEGER NOT NULL , "  +
                "UNIQUE (" + DatabaseContract.Login.COLUMN_EMAIL + ";";


        db.execSQL(SQL_CREATE_LOGIN_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

}
