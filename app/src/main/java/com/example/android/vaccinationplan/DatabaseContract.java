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
        public static final String COLUMN_EMAIL= "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_NUMBER_OF_CHILDEREN = "number_of_children";

    }

    public static final class ChildDetails implements BaseColumns{
        public static final String TABLE_NAME = "children";

        public static final String COLUMN_CHILD_ID = "child_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DOB = "date_of_birth";
        public static final String COLUMN_MOTHER = "mother";
        public static final String COLUMN_POB = "place_of_birth";/*Place of birth*/
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_LOCATION = "curr_location";
        public static final String COLUMN_HOSPITAL = "preffered_hospital";
        public static final String COLUMN_UPDATE_STATUS = "update_status";/*set to 1 when database is up to date with server
                                                                             if any change is made then set to 0*/
        public static final String COLUMN_BLOOD_GROUP = "blood_group";

    }

    public static final class HospitalDetails implements BaseColumns{

        public static final String TABLE_NAME = "hospitals";

        public static final String COLUMN_NAME = "hospital_name";
        public static final String COLUMN_CATEGORY = "hospital_category";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_PHONE = "phone_No";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_STATE = "state";


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
    }

}
