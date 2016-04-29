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
    private static final int DATABASE_VERSION = 9;

    
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
                DatabaseContract.HospitalDetails.COLUMN_ADDRESS + " VARCHAR(200) DEFAULT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_CITY + " VARCHAR(30) DEFAULT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_PINCODE + " VARCHAR(10) DEFAULT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_STATE + " VARCHAR(30) DEFAULT NULL," +
                DatabaseContract.HospitalDetails.COLUMN_PHONE + " VARCHAR(300) DEFAULT NULL," +
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


        final String SQL_INSERT_VACCINE_DETAILS_TABLE = "\n" +
                "INSERT INTO "+ DatabaseContract.VaccineDetails.TABLE_NAME +" (`_id`, `vaccine_id`, `short_form`, `name`, `prevents`, `detail_info`, `schedule`, `recommendation`) VALUES\n" +
                "(1, 'Vaccine_1', 'BCG', 'Bacillus Calmette Guerin', 'Tuberculosis', 'It causes slight swelling at the injected site. Do not apply any medicine on the swollen area.', 0, 'MANDATORY'),\n" +
                "(2, 'Vaccine_2', 'OPV 0', 'Oral Polio Vaccine', 'Poliomyelitis (Polio), which damages nervous system, causes muscle weakness or paralysis.', 'Given for FREE in Govt. Hospitals upto the age of 5 yrs on Pulse Immunization Day.', 0, 'MANDATORY'),\n" +
                "(3, 'Vaccine_3', 'Hep-B 1', 'Hepatitis B', 'Liver infection caused by Hepatitis B virus.', ' Essential within 12 hours of birth to prevent transmission from mother', 0, 'RECOMMENDED'),\n" +
                "(4, 'Vaccine_4', 'HPV', 'Human Papilloma Virus', 'Cervical Cancer & other genital cancers.', 'Only for girls', 0, 'OPTIONAL'),\n" +
                "(5, 'Vaccine_5', 'DPT', 'DPT', 'Diphtheria (upper respiratory illness), Pertussis (Whooping Cough) & Tetanus.', 'There may be mild fever, pain & swelling at the injected site.', 6, 'MANDATORY'),\n" +
                "(6, 'Vaccine_6', 'OPV', 'Oral Polio Vaccine', 'Poliomyelitis (Polio), which damages nervous system, causes muscle weakness or paralysis.', 'Given for FREE in Govt Hospitals upto the age of 5 yrs on Pulse Immunization Day.', 6, 'MANDATORY'),\n" +
                "(7, 'Vaccine_7', 'HiB', 'Haemophilus Influenzae Type B', 'Menangitis, which affect membranes surrounding the brain & the spinal cord.', 'There might be slight redness, swelling or pain at the injected site.', 6, 'OPTIONAL'),\n" +
                "(8, 'Vaccine_8', 'Pneumococcal', 'Pneumococcal', 'Pneumococcal disease, caused byh Streptococcous Pneumonia bacteria.', 'Prevenar was the first vaccine against Pneumococcal disease in India.', 6, 'OPTIONAL'),\n" +
                "(9, 'Vaccine_9', 'Hep-B 2', 'Hepatitis B', 'Liver infection caused by Hepatitis B virus.', '  The final (third or fourth) dose in the HepB vaccine series should be administered no earlier than age 24 weeks and at least 16 weeks after the first dose', 6, 'MANDATORY'),\n" +
                "(10, 'Vaccine_10', 'DPT', 'DPT', 'Diphtheria (upper respiratory illness), Pertussis (Whooping Cough) & Tetanus.', 'There may be mild fever, pain & swelling at the injected site.', 10, 'MANDATORY'),\n" +
                "(11, 'Vaccine_11', 'OPV', 'Oral Polio Vaccine', 'Poliomyelitis (Polio), which damages nervous system, causes muscle weakness or paralysis.', 'Given FREE in Government Hospitals upto age of 5 years on every Pulse Immunization Day.', 10, 'MANDATORY'),\n" +
                "(12, 'Vaccine_12', 'Pneumococcal', 'Pneumococcal', 'Pneumococcal disease, caused by Streptococcous Pneumonia bacteria.', 'Prevenar was the first vaccine against Pneumococcal disease in India.', 10, 'OPTIONAL'),\n" +
                "(13, 'Vaccine_13', 'HiB', 'Haemophilus Influenzae Type B', 'Menangitis, which affect membranes surrounding the brain & the spinal cord.', 'There might be slight redness, swelling or pain at the injected spot.', 10, 'OPTIONAL'),\n" +
                "(14, 'Vaccine_14', 'IPV', 'Inactivated Polio Vaccine', 'Poliomyelitis (Polio), which damages nervous system, causes muscle weakness or paralysis.', 'Given in the form of injection.', 10, 'OPTIONAL'),\n" +
                "(15, 'Vaccine_15', 'OPV', 'Oral Polio Vaccine', 'Poliomyelitis (Polio), which damages nervous system, causes muscle weakness or paralysis.', 'Given for FREE in Govt Hospitals upto the age of 5 yrs on Pulse Immunization Day.', 14, 'MANDATORY'),\n" +
                "(16, 'Vaccine_16', 'DPT', 'DPT', 'Diphtheria (upper respiratory illness), Pertussis (Whooping Cough) & Tetanus.', 'There may be mild fever, pain & swelling at the injected site.', 14, 'MANDATORY'),\n" +
                "(17, 'Vaccine_17', 'Hep-B 3', 'Hepatitis B', 'Liver infection caused by Hepatitis B virus.', '  The final (third or fourth) dose in the HepB vaccine series should be administeredno earlier than age 24 weeks and at least 16 weeks after the first dose', 24, 'MANDATORY'),\n" +
                "(18, 'Vaccine_18', 'Pneumococcal', 'Pneumococcal', 'Pneumococcal disease, caused by Streptococcous Pneumonia bacteria.', 'Prevenar was the first vaccine against Pneumococcal disease in India.', 14, 'OPTIONAL'),\n" +
                "(19, 'Vaccine_19', 'Hib', 'Haemophilus Influenzae Type B', 'Menangitis, which affect membranes surrounding the brain & the spinal cord.', 'There might be slight redness, swelling or pain at the injected site.', 14, 'OPTIONAL'),\n" +
                "(20, 'Vaccine_20', 'IPV', 'Inactivated Polio Vaccine', 'Poliomyelitis (Polio), which damages nervous system, causes muscle weakness or paralysis.', 'Given in the form of injection.', 14, 'OPTIONAL'),\n" +
                "(21, 'Vaccine_21', 'Rotavirus', 'Rotavirus', 'Rotavirus infection, which causes severe Diarrhoea among infants.', 'First dose is given before the age of 6 months & second dose at a gap of 1 month.', 10, 'RECOMMENDED'),\n" +
                "(22, 'Vaccine_22', 'Influenza', 'Influenza', 'Influenza virus which causes infection of the respiratory system.', 'The most common vaccine is Trivalent Influenza Vaccine (TIV).', 24, 'MANDATORY'),\n" +
                "(23, 'Vaccine_23', 'HPV', 'Human Papilloma Virus', 'Cervical Cancer & other genital cancers.', 'only for girls', 24, 'OPTIONAL'),\n" +
                "(24, 'Vaccine_24', 'Rotavirus', 'Rotavirus', 'Rotavirus infection, which causes severe Diarrhoea among infants.', 'First dose is given before the age of 6 months & second dose at a gap of 1 month.', 14, 'RECOMMENDED'),\n" +
                "(25, 'Vaccine_25', 'MMR-1', ' Measles-containing vaccine ', 'Measles, which causes infection of the respiratory system.', 'Highly contagious disease. Hence its very important to get the vaccination done on time.Ideally should not be administered before completing270 days or 9 months of life.', 38, 'MANDATORY'),\n" +
                "(26, 'Vaccine_26', 'OPV', 'Oral Polio Vaccine', 'Poliomyelitis (Polio), which damages nervous system, causes muscle weakness or paralysis.', 'Given for FREE in Govt Hospitals upto the age of 5 yrs on Pulse Immunization Day.', 38, 'RECOMMENDED'),\n" +
                "(27, 'Vaccine_27', 'Typhoid ', 'Conjugate Vaccine', 'Typhoid', 'n interval of at least 4 weeks with the MMR vaccine should be maintained whileadministering this vaccine', 52, 'RECOMMENDED'),\n" +
                "(28, 'Vaccine_28', 'Hep-A 1', 'Hepatitis A', 'Liver infection caused by HAV (Hepatitis A virus).', 'Since Hepatitis A is common in young children India, it is advisable to get the vaccination done.', 51, 'RECOMMENDED'),\n" +
                "(29, 'Vaccine_29', 'MMR-2', 'Measles, Mumps, Rubella.', 'Measles, which causes infection of the respiratory system.', 'The 2nd dose must follow in 2nd year of life', 64, 'MANDATORY'),\n" +
                "(30, 'Vaccine_30', 'VARICELLA 1', 'Varicella', 'Chickenpox', ' Varicella is the primary vaccine for Chickenpox.The risk of breakthrough varicella is lower if given 15 months onwards.', 64, 'RECOMMENDED'),\n" +
                "(31, 'Vaccine_31', 'PCV booster', 'Pneumococcal Booster', 'Pneumococcal disease, caused by Streptococcous Pneumonia bacteria', 'Prevenar was the first vaccine against Pneumococcal disease in India. ', 52, 'RECOMMENDED'),\n" +
                "(32, 'Vaccine_32', 'IPV', 'Inactivated Polio Vaccine', 'Poliomyelitis (Polio), which damages nervous system, causes muscle weakness or paralysis.', 'Given in the form of injection.', 52, 'OPTIONAL'),\n" +
                "(33, 'Vaccine_33', 'OPV Booster', 'Oral Polio Vaccine', ' Poliomyelitis (Polio), which damages nervous system, causes muscle weakness or paralysis.', ' Given for FREE in Govt Hospitals upto the age of 5 yrs on Pulse Immunization Day.', 78, 'MANDATORY'),\n" +
                "(34, 'Vaccine_34', 'DPT ', 'DPT Booster', 'Diphtheria (upper respiratory illness), Pertussis (Whooping Cough) & Tetanus.', ' There may be mild fever, pain & swelling at the injection site.', 78, 'MANDATORY'),\n" +
                "(35, 'Vaccine_35', 'HiB Booster', 'Haemophilus Influenzae Type B', ' Menangitis, which affect membranes surrounding the brain & the spinal cord.', 'There might be slight redness, swelling or pain at the injected site. ', 78, 'OPTIOAL'),\n" +
                "(36, 'Vaccine_36', 'Hep-A ', 'Hepatitis A', 'Liver infection caused by HAV (Hepatitis A virus).', 'Since Hepatitis A is common in young children India, it is advisable to get the vaccination done.', 78, 'OPTIONAL'),\n" +
                "(37, 'Vaccine_37', 'Typhoid', 'Typhoid Vaccine', 'Typhoid, a fever caused by Typhoid bacillus.', 'There are 2 types of Typhoid vaccines- Inactivated Vaccine (Shot) & Weakened Vaccine. ', 104, 'MANDATORY');\n";

        db.execSQL(SQL_CREATE_LOGIN_TABLE);
        db.execSQL(SQL_CREATE_CHILD_DETAILS_TABLE);
        db.execSQL(SQL_CREATE_HOSPITAL_DETAILS_TABLE);
        db.execSQL(SQL_CREATE_VACCINE_DETAILS_TABLE);
        db.execSQL(SQL_CREATE_CHILD_VACCINATION_STATUS_TABLE);
        db.execSQL(SQL_INSERT_VACCINE_DETAILS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Login.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.HospitalDetails.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.VaccineDetails.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ChildVaccinationStatus.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ChildDetails.TABLE_NAME);

        onCreate(db);
    }

}
