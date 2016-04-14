package com.example.android.vaccinationplan;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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

    public static boolean insertIntoChildDetails(JSONArray arr , Context mContext) throws JSONException {
        /*Call insert into vaccine details also for every child*/

        String name;
        String child_id;
        String date_of_birth;
        String mother;
        String place_of_birth;
        String blood_group;
        String hospitalId;
        String gender;
        String location;
        JSONObject vaccineStatus;

        JSONObject obj;

        VaccinationDBHelper helper = new VaccinationDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        for(int i =0;i<arr.length();i++){


            obj = arr.getJSONObject(i);
            name = obj.getString("name");
            child_id = obj.getString("child_id");
            date_of_birth = obj.getString("date_of_birth");
            mother = obj.getString("mother");
            place_of_birth = obj.getString("place_of_birth");
            blood_group  = obj.getString("blood_group");
            hospitalId  = obj.getString("hospitalId");
            gender = obj.getString("gender");
            location = obj.getString("location");
            vaccineStatus = obj.getJSONObject("vaccinestatus");

            long rowId;
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ChildDetails.COLUMN_NAME , name);
            values.put(DatabaseContract.ChildDetails.COLUMN_DOB , date_of_birth);
            values.put(DatabaseContract.ChildDetails.COLUMN_GENDER , gender);
            values.put(DatabaseContract.ChildDetails.COLUMN_HOSPITAL, hospitalId);
            values.put(DatabaseContract.ChildDetails.COLUMN_MOTHER, mother);
            values.put(DatabaseContract.ChildDetails.COLUMN_LOCATION , location);
            values.put(DatabaseContract.ChildDetails.COLUMN_POB , place_of_birth);
            values.put(DatabaseContract.ChildDetails.COLUMN_BLOOD_GROUP, blood_group);
            values.put(DatabaseContract.ChildDetails.COLUMN_CHILD_ID , child_id);

            rowId = db.insert(DatabaseContract.ChildDetails.TABLE_NAME, null, values);
            if(rowId!=-1){

                rowId = insertIntoVaccineStatus(vaccineStatus,child_id ,mContext);
                if(rowId !=-1){
                    return true;
                }else
                    return false;
            }
            else
                return  false;
        }

        return  true;

    }



    public static void insertIntoChildDetails(ChildDetails child){

    }

    public static long insertIntoVaccineStatus(JSONObject obj ,String child_id,  Context mContext) throws JSONException {
        VaccinationDBHelper helper = new VaccinationDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        Iterator<String> keys = obj.keys();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ChildVaccinationStatus.CHILD_ID , child_id);
        while(keys.hasNext()){
            String key = keys.next();
            int value = obj.getInt(key);
            values.put(key , value);
        }

        return  db.insert(DatabaseContract.ChildVaccinationStatus.TABLE_NAME, null, values);

    }

}
