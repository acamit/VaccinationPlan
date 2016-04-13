package com.example.android.vaccinationplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;

/**
 * Created by amit on 10/4/16.
 */
public class DatabaseOperations {

    public static long insertIntoLogin(String email, String password, String token, int number_of_children, Context mContext) {

        VaccinationDBHelper helper = new VaccinationDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        long rowId;
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Login.COLUMN_EMAIL, email);
        values.put(DatabaseContract.Login.COLUMN_PASSWORD, password);
        values.put(DatabaseContract.Login.COLUMN_NUMBER_OF_CHILDEREN, number_of_children);
        rowId = db.insert(DatabaseContract.Login.TABLE_NAME, null, values);
        return rowId;
    }

    public static void insertIntoChildDetails(JSONArray arr) {

    }

    public static void insertIntoChildDetails(ChildDetails child){

    }

}
