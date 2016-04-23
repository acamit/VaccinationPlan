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
    private static final int DATABASE_VERSION = 3;

    
    public VaccinationDBHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOGIN_TABLE = "CREATE TABLE " + DatabaseContract.Login.TABLE_NAME + "(" +
                DatabaseContract.Login._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DatabaseContract.Login.COLUMN_LOGIN_ID + " varchar(50) NOT NULL , " +
                DatabaseContract.Login.COLUMN_EMAIL+ " TEXT NOT NULL UNIQUE , " +
                DatabaseContract.Login.COLUMN_PASSWORD + " TEXT NOT NULL , " +
                DatabaseContract.Login.COLUMN_STATUS + " varchar(1) DEFAULT 0 , " +
                DatabaseContract.Login.COLUMN_NUMBER_OF_CHILDREN+ " INTEGER NOT NULL);";


        final String SQL_CREATE_CHILD_DETAILS_TABLE = "CREATE TABLE "+DatabaseContract.ChildDetails.TABLE_NAME+"(" +
                DatabaseContract.ChildDetails._ID+" INTEGER PRIMARY KEY AUTOINCREMENT ," +
                DatabaseContract.ChildDetails.COLUMN_CHILD_ID + " varchar(50) DEFAULT NULL, "+
                DatabaseContract.ChildDetails.COLUMN_LOGIN_DETAILS_ID+" INTEGER NULL," +
                DatabaseContract.ChildDetails.COLUMN_NAME+" varchar(50) NOT NULL," +
                DatabaseContract.ChildDetails.COLUMN_DOB+" date NOT NULL," +
                DatabaseContract.ChildDetails.COLUMN_GENDER+ " varchar(6) DEFAULT NULL, "+
                DatabaseContract.ChildDetails.COLUMN_MOTHER+" varchar(100) NOT NULL," +
                DatabaseContract.ChildDetails.COLUMN_LOCATION+" varchar(10) NOT NULL," +
                DatabaseContract.ChildDetails.COLUMN_LOCATION_PIN+" varchar(10) DEFAULT NULL," +
                DatabaseContract.ChildDetails.COLUMN_POB+" varchar(50) DEFAULT NULL," +
                DatabaseContract.ChildDetails.COLUMN_POB_PIN+" varchar(10) DEFAULT NULL," +
                DatabaseContract.ChildDetails.COLUMN_BLOOD_GROUP+" varchar(3) NOT NULL," +
                DatabaseContract.ChildDetails.COLUMN_HOSPITAL+" varchar(10) DEFAULT NULL," +
                DatabaseContract.ChildDetails.COLUMN_UPDATE_STATUS+" INTEGER NOT NULL DEFAULT '0'" +
                ");";

        final String SQL_CREATE_CHILD_VACCINATION_STATUS_TABLE = "CREATE TABLE `ChildVaccinationStatus` (" +
                "  `_id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  `childID` varchar(30) DEFAULT NULL," +
                DatabaseContract.ChildVaccinationStatus.CHILD_ROW_ID + " varchar(30) NOT NULL," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_1 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_2 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_3 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_4 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_5 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_6 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_7 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_8 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_9 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_10 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_11 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_12 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_13 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_14 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_15 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_16 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_17 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_18 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_19 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_20 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_21 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_22 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_23 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_24 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_25 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_26 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_27 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_28 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_29 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_30 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_31 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_32 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_33 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_34 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_35 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_36 + " INTEGER NOT NULL DEFAULT '0'," +
                DatabaseContract.ChildVaccinationStatus.COLUMN_VACCINE_37 + " INTEGER NOT NULL DEFAULT '0'" +
                ");";

        final String SQL_CREATE_HOSPITAL_DETAILS_TABLE = "CREATE TABLE " + DatabaseContract.HospitalDetails.TABLE_NAME + "(" +
                DatabaseContract.HospitalDetails._ID +  " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                DatabaseContract.HospitalDetails.COLUMN_NAME + " VARCHAR(200) NOT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_CATEGORY + " VARCHAR(20) DEFAULT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_ADDRESS + " VARCHAR(200) NOT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_CITY + " VARCHAR(30) DEFAULT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_PINCODE + " VARCHAR(10) DEFAULT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_STATE + " VARCHAR(30) DEFAULT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_PHONE + " VARCHAR(300) NOT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_WEBSITE + " VARCHAR(100) DEFAULT NULL" +
                ");"
                ;

        final String SQL_CREATE_VACCINE_DETAILS_TABLE = "CREATE TABLE " + DatabaseContract.VaccineDetails.TABLE_NAME + "(" +
                DatabaseContract.VaccineDetails._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DatabaseContract.VaccineDetails.COLUMN_ID + " VARCHAR(10) UNIQUE NOT NULL   ," +
                DatabaseContract.VaccineDetails.COLUMN_SHORT_FORM + " VARCHAR(10) NOT NULL ," +
                DatabaseContract.VaccineDetails.COLUMN_NAME + " VARCHAR(30) NOT NULL ," +
                DatabaseContract.VaccineDetails.COLUMN_PREVENTS + " VARCHAR(100) NOT NULL ," +
                DatabaseContract.VaccineDetails.COLUMN_INFO + " VARCHAR(200) NOT NULL ," +
                DatabaseContract.VaccineDetails.COLUMN_RECOMMEND + " VARCHAR(15) NOT NULL DEFAULT MANDATORY, " +
                DatabaseContract.VaccineDetails.COLUMN_SCHEDULE + " INTEGER NOT NULL" +
                ");";


        db.execSQL(SQL_CREATE_LOGIN_TABLE);
        db.execSQL(SQL_CREATE_CHILD_DETAILS_TABLE);
        db.execSQL(SQL_CREATE_HOSPITAL_DETAILS_TABLE);
        db.execSQL(SQL_CREATE_VACCINE_DETAILS_TABLE);
        db.execSQL(SQL_CREATE_CHILD_VACCINATION_STATUS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Login.TABLE_NAME);
        onCreate(db);
    }

}
