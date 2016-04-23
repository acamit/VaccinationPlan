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

    public static long insertIntoLogin(String email, String password ,String loginId ,String status, int number_of_children, Context mContext) {

        VaccinationDBHelper helper = new VaccinationDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        long rowId;
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.Login.COLUMN_EMAIL, email);
        values.put(DatabaseContract.Login.COLUMN_LOGIN_ID , loginId);
        values.put(DatabaseContract.Login.COLUMN_PASSWORD, password);
        values.put(DatabaseContract.Login.COLUMN_STATUS , status);
        values.put(DatabaseContract.Login.COLUMN_NUMBER_OF_CHILDREN, number_of_children);
        rowId = db.insert(DatabaseContract.Login.TABLE_NAME, null, values);

        return rowId;
    }

    /*For already existing children*/
    public static boolean insertIntoChildDetails(JSONArray arr , Context mContext) throws JSONException {
        /*Call insert into vaccine details also for every child for already existing user*/
        String loginId;
        String name;
        String child_id;
        String date_of_birth;
        String mother;
        String place_of_birth;
        String place_of_birth_pin;
        String blood_group;
        String hospitalId;
        String gender;
        String location;
        String location_pin;

        JSONObject vaccineStatus;

        JSONObject obj;

        VaccinationDBHelper helper = new VaccinationDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        for(int i =0;i<arr.length();i++){

            obj = arr.getJSONObject(i);
            loginId = obj.getString("loginId");
            name = obj.getString("name");
            child_id = obj.getString("child_id");
            date_of_birth = obj.getString("date_of_birth");
            mother = obj.getString("mother");
            place_of_birth = obj.getString("place_of_birth");
            place_of_birth_pin = obj.getString("place_of_birth_pin");
            blood_group  = obj.getString("blood_group");
            hospitalId  = obj.getString("hospitalId");
            gender = obj.getString("gender");
            location = obj.getString("current_location");
            location_pin = obj.getString("current_location_pin");
            vaccineStatus = obj.getJSONObject("vaccinestatus");

            long rowId;
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ChildDetails.COLUMN_CHILD_ID , child_id);
            values.put(DatabaseContract.ChildDetails.COLUMN_LOGIN_DETAILS_ID , loginId);
            values.put(DatabaseContract.ChildDetails.COLUMN_NAME , name);
            values.put(DatabaseContract.ChildDetails.COLUMN_DOB , date_of_birth);
            values.put(DatabaseContract.ChildDetails.COLUMN_GENDER , gender);
            values.put(DatabaseContract.ChildDetails.COLUMN_HOSPITAL, hospitalId);
            values.put(DatabaseContract.ChildDetails.COLUMN_MOTHER, mother);
            values.put(DatabaseContract.ChildDetails.COLUMN_LOCATION , location);
            values.put(DatabaseContract.ChildDetails.COLUMN_LOCATION_PIN , location_pin);
            values.put(DatabaseContract.ChildDetails.COLUMN_POB , place_of_birth);
            values.put(DatabaseContract.ChildDetails.COLUMN_POB_PIN , place_of_birth_pin);
            values.put(DatabaseContract.ChildDetails.COLUMN_BLOOD_GROUP, blood_group);
            values.put(DatabaseContract.ChildDetails.COLUMN_UPDATE_STATUS, "1");

            rowId = db.insert(DatabaseContract.ChildDetails.TABLE_NAME, null, values);
            if(rowId!=-1){

                rowId = insertIntoVaccineStatus(vaccineStatus,""+rowId,child_id ,mContext);
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

    /*For a new Child*/
    public static boolean insertIntoChildDetails(Child child , Context mContext){
        String name;
        String child_id;
        String date_of_birth;
        String mother;
        String place_of_birth;
        String place_of_birth_pin;
        String blood_group;
        String gender;
        String location;
        String location_pin;


        VaccinationDBHelper helper = new VaccinationDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();



            name = child.name;
            child_id = child.child_id;
            date_of_birth = child.date_of_birth;
            mother = child.mother;
            place_of_birth = child.place_of_birth;
            place_of_birth_pin = child.place_of_birthPin;
            blood_group  = child.blood_group;
            gender = child.gender;
            location = child.curr_location;
            location_pin = child.curr_locationPin;

            long rowId;
            ContentValues values = new ContentValues();

            values.put(DatabaseContract.ChildDetails.COLUMN_NAME , name);
            values.put(DatabaseContract.ChildDetails.COLUMN_DOB , date_of_birth);
            values.put(DatabaseContract.ChildDetails.COLUMN_GENDER , gender);
            values.put(DatabaseContract.ChildDetails.COLUMN_MOTHER, mother);
            values.put(DatabaseContract.ChildDetails.COLUMN_LOCATION , location);
            values.put(DatabaseContract.ChildDetails.COLUMN_LOCATION_PIN , location_pin);
            values.put(DatabaseContract.ChildDetails.COLUMN_POB , place_of_birth);
            values.put(DatabaseContract.ChildDetails.COLUMN_POB_PIN , place_of_birth_pin);
            values.put(DatabaseContract.ChildDetails.COLUMN_BLOOD_GROUP, blood_group);
            values.put(DatabaseContract.ChildDetails.COLUMN_CHILD_ID , child_id);
            values.put(DatabaseContract.ChildDetails.COLUMN_UPDATE_STATUS, "1");

            rowId = db.insert(DatabaseContract.ChildDetails.TABLE_NAME, null, values);
            if(rowId!=-1){

                rowId = insertIntoVaccineStatus(child_id , ""+rowId,mContext);
                if(rowId !=-1){
                    return true;
                }else
                    return false;

            }
            else
                return  false;

    }

    /*Create an entry for already registered child*/
    public static long insertIntoVaccineStatus(JSONObject obj ,String  rowId ,String child_id,  Context mContext) throws JSONException {
        VaccinationDBHelper helper = new VaccinationDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();

        Iterator<String> keys = obj.keys();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ChildVaccinationStatus.CHILD_ID , child_id);
        values.put(DatabaseContract.ChildVaccinationStatus.CHILD_ROW_ID, rowId);
        while(keys.hasNext()){
            String key = keys.next();
            int value = obj.getInt(key);
            values.put(key , value);
        }

        return  db.insert(DatabaseContract.ChildVaccinationStatus.TABLE_NAME, null, values);

    }

    /*Create an entry for newly registered Child*/
    public static long insertIntoVaccineStatus(String child_id , String rowId, Context mContext){
        VaccinationDBHelper helper = new VaccinationDBHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ChildVaccinationStatus.CHILD_ID , child_id);
        values.put(DatabaseContract.ChildVaccinationStatus.CHILD_ROW_ID, rowId);

        return  db.insert(DatabaseContract.ChildVaccinationStatus.TABLE_NAME, null, values);
    }



}
