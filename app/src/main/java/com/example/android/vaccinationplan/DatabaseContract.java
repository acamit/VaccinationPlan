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

}
