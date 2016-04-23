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
    private static final int DATABASE_VERSION = 2;

    
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
                "  `child_row_id` varchar(30) NOT NULL," +
                "  `BCG1` INTEGER NOT NULL DEFAULT '0'," +
                "  `OPV2` INTEGER NOT NULL DEFAULT '0'," +
                "  `HepatitisB3` INTEGER NOT NULL DEFAULT '0'," +
                "  `HPV4` INTEGER NOT NULL DEFAULT '0'," +
                "  `DPT5` INTEGER NOT NULL DEFAULT '0'," +
                "  `OPV6` INTEGER NOT NULL DEFAULT '0'," +
                "  `HiB7` INTEGER NOT NULL DEFAULT '0'," +
                "  `Pneumococcal8` INTEGER NOT NULL DEFAULT '0'," +
                "  `HepatitisB9` INTEGER NOT NULL DEFAULT '0'," +
                "  `DPT10` INTEGER NOT NULL DEFAULT '0'," +
                "  `OPV11` INTEGER NOT NULL DEFAULT '0'," +
                "  `Pneumococcal58` INTEGER NOT NULL DEFAULT '0'," +
                "  `HiB59` INTEGER NOT NULL DEFAULT '0'," +
                "  `IPV60` INTEGER NOT NULL DEFAULT '0'," +
                "  `OPV61` INTEGER NOT NULL DEFAULT '0'," +
                "  `DPT62` INTEGER NOT NULL DEFAULT '0'," +
                "  `HepatitisB63` INTEGER NOT NULL DEFAULT '0'," +
                "  `Pneumococcal64` INTEGER NOT NULL DEFAULT '0'," +
                "  `Hib65` INTEGER NOT NULL DEFAULT '0'," +
                "  `IPV66` INTEGER NOT NULL DEFAULT '0'," +
                "  `Rotavirus67` INTEGER NOT NULL DEFAULT '0'," +
                "  `Influenza70` INTEGER NOT NULL DEFAULT '0'," +
                "  `HPV71` INTEGER NOT NULL DEFAULT '0'," +
                "  `OPV02` INTEGER NOT NULL DEFAULT '0'," +
                "  `Hep-B13` INTEGER NOT NULL DEFAULT '0'," +
                "  `Hep-B29` INTEGER NOT NULL DEFAULT '0'," +
                "  `Pneumococcal12` INTEGER NOT NULL DEFAULT '0'," +
                "  `HiB13` INTEGER NOT NULL DEFAULT '0'," +
                "  `IPV14` INTEGER NOT NULL DEFAULT '0'," +
                "  `OPV15` INTEGER NOT NULL DEFAULT '0'," +
                "  `DPT16` INTEGER NOT NULL DEFAULT '0'," +
                "  `Hep-B317` INTEGER NOT NULL DEFAULT '0'," +
                "  `Pneumococcal18` INTEGER NOT NULL DEFAULT '0'," +
                "  `Hib19` INTEGER NOT NULL DEFAULT '0'," +
                "  `IPV20` INTEGER NOT NULL DEFAULT '0'," +
                "  `Rotavirus21` INTEGER NOT NULL DEFAULT '0'," +
                "  `Influenza22` INTEGER NOT NULL DEFAULT '0'," +
                "  `HPV23` INTEGER NOT NULL DEFAULT '0'," +
                "  `Rotavirus24` INTEGER NOT NULL DEFAULT '0'," +
                "  `MMR-125` INTEGER NOT NULL DEFAULT '0'," +
                "  `OPV26` INTEGER NOT NULL DEFAULT '0'," +
                "  `Typhoid27` INTEGER NOT NULL DEFAULT '0'," +
                "  `Hep-A128` INTEGER NOT NULL DEFAULT '0'," +
                "  `MMR-229` INTEGER NOT NULL DEFAULT '0'," +
                "  `VARICELLA130` INTEGER NOT NULL DEFAULT '0'," +
                "  `PCVbooster31` INTEGER NOT NULL DEFAULT '0'," +
                "  `IPV32` INTEGER NOT NULL DEFAULT '0'," +
                "  `OPVBooster33` INTEGER NOT NULL DEFAULT '0'," +
                "  `DPT34` INTEGER NOT NULL DEFAULT '0'," +
                "  `HiBBooster35` INTEGER NOT NULL DEFAULT '0'," +
                "  `Hep-A36` INTEGER NOT NULL DEFAULT '0'," +
                "  `Typhoid37` INTEGER NOT NULL DEFAULT '0'" +
                ");";

        db.execSQL(SQL_CREATE_LOGIN_TABLE);
        db.execSQL(SQL_CREATE_CHILD_DETAILS_TABLE);
        db.execSQL(SQL_CREATE_CHILD_VACCINATION_STATUS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Login.TABLE_NAME);
        onCreate(db);
    }

}
