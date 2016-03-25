package com.example.android.vaccinationplan;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by amit on 25/3/16.
 */
public class SessionManager {
    Context context;

    SessionManager(Context context) {
        this.context = context;
    }

    public boolean checkSession() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this.context);
        String Tag = context.getString(R.string.pref_key_email);
        String email = pref.getString(Tag, null);
        if (pref.contains(Tag))
            return true;
        return false;
    }

}
