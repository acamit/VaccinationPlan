package com.example.android.vaccinationplan;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by amit on 6/4/16.
 */
public class DatabaseContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.vaccinationplan";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_LOGIN = "login";
    public static final String PATH_CHILD_DETAILS = "childDetails";
    public static final String DATE_FORMAT = "yyyyMMdd";


    public static final class Login implements BaseColumns{
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOGIN).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_LOGIN;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_LOGIN;

        public static final String TABLE_NAME = "loginDetails";
        /*For sync with online database. Contains the ID in the Online database.*/
        public  static final String COLUMN_LOGIN_ID = "loginId";
        public static final String COLUMN_EMAIL= "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NUMBER_OF_CHILDREN = "number_of_children";
        public static final String COLUMN_STATUS = "status";

    }

    public static final class ChildDetails implements BaseColumns{
        public static final String TABLE_NAME = "children";

        public static final String COLUMN_CHILD_ID = "child_id";
        public static final String COLUMN_LOGIN_DETAILS_ID = "loginDetailsId";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DOB = "date_of_birth";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_MOTHER = "mother";
        public static final String COLUMN_LOCATION = "curr_location";
        public static final String COLUMN_LOCATION_PIN = "curr_location_pin";
        public static final String COLUMN_POB = "place_of_birth";/*Place of birth*/
        public static final String COLUMN_POB_PIN = "place_of_birth_pin";
        public static final String COLUMN_BLOOD_GROUP = "blood_group";
        public static final String COLUMN_HOSPITAL = "preferred_hospital";
        public static final String COLUMN_UPDATE_STATUS = "update_status";/*set to 1 when database is up to date with server
                                                                             if any change is made then set to 0*/

    }

    public static final class HospitalDetails implements BaseColumns{

        public static final String TABLE_NAME = "hospitals";
        public static final String COLUMN_NAME = "hospital_name";
        public static final String COLUMN_CATEGORY = "hospital_category";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone_No";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_PINCODE = "pincode";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_WEBSITE = "website";


    }

    public static final class VaccineDetails implements BaseColumns{

        public static final String TABLE_NAME = "vaccines";
        public static final String COLUMN_ID = "vac_id";
        public static final String COLUMN_SHORT_FORM = "short_form";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PREVENTS = "prevents";
        public static final String COLUMN_INFO = "info";
        public static final String COLUMN_SCHEDULE = "schedule";/*At Birth, 5 Months, 12 Months*/
        public static final String COLUMN_RECOMMEND = "recommendation";/* OPTIONAL, MANDATORY, RECOMMENDED*/
        //  public static final String COLUMN_CHILD = "child_track";/* track which vaccine is done and which is pending*/
    }
    public static final class ChildVaccinationStatus implements BaseColumns{
        public static final String TABLE_NAME = "ChildVaccinationStatus";
        public static final String CHILD_ID= "childID";
        public static final String CHILD_ROW_ID = "child_row_id";
        public static final String COLUMN_VACCINE_1 = "VACCINE_1";
        public static final String COLUMN_VACCINE_2 = "VACCINE_2";
        public static final String COLUMN_VACCINE_3 = "VACCINE_3";
        public static final String COLUMN_VACCINE_4 = "VACCINE_4";
        public static final String COLUMN_VACCINE_5 = "VACCINE_5";
        public static final String COLUMN_VACCINE_6 = "VACCINE_6";
        public static final String COLUMN_VACCINE_7 = "VACCINE_7";
        public static final String COLUMN_VACCINE_8 = "VACCINE_8";
        public static final String COLUMN_VACCINE_9 = "VACCINE_9";
        public static final String COLUMN_VACCINE_10 = "VACCINE_10";
        public static final String COLUMN_VACCINE_11 = "VACCINE_11";
        public static final String COLUMN_VACCINE_12 = "VACCINE_12";
        public static final String COLUMN_VACCINE_13 = "VACCINE_13";
        public static final String COLUMN_VACCINE_14 = "VACCINE_14";
        public static final String COLUMN_VACCINE_15 = "VACCINE_15";
        public static final String COLUMN_VACCINE_16 = "VACCINE_16";
        public static final String COLUMN_VACCINE_17 = "VACCINE_17";
        public static final String COLUMN_VACCINE_18 = "VACCINE_18";
        public static final String COLUMN_VACCINE_19 = "VACCINE_19";
        public static final String COLUMN_VACCINE_20 = "VACCINE_20";
        public static final String COLUMN_VACCINE_21 = "VACCINE_21";
        public static final String COLUMN_VACCINE_22 = "VACCINE_22";
        public static final String COLUMN_VACCINE_23 = "VACCINE_23";
        public static final String COLUMN_VACCINE_24 = "VACCINE_24";
        public static final String COLUMN_VACCINE_25 = "VACCINE_25";
        public static final String COLUMN_VACCINE_26 = "VACCINE_26";
        public static final String COLUMN_VACCINE_27 = "VACCINE_27";
        public static final String COLUMN_VACCINE_28 = "VACCINE_28";
        public static final String COLUMN_VACCINE_29 = "VACCINE_29";
        public static final String COLUMN_VACCINE_30 = "VACCINE_30";
        public static final String COLUMN_VACCINE_31 = "VACCINE_31";
        public static final String COLUMN_VACCINE_32 = "VACCINE_32";
        public static final String COLUMN_VACCINE_33 = "VACCINE_33";
        public static final String COLUMN_VACCINE_34 = "VACCINE_34";
        public static final String COLUMN_VACCINE_35 = "VACCINE_35";
        public static final String COLUMN_VACCINE_36 = "VACCINE_36";
        public static final String COLUMN_VACCINE_37 = "VACCINE_37";

    }



}
