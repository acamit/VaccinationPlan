package com.example.android.vaccinationplan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity implements Preference.OnPreferenceChangeListener {
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */


    String childId;

    private String city;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add 'general' preferences, defined in the XML file
        addPreferencesFromResource(R.xml.pref_general);
        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
        // updated when the preference changes.

        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_location)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_hospital)));
        mContext = SettingsActivity.this;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        childId = pref.getString("childId", "");
        address = pref.getString(getString(R.string.pref_key_location) ,"New delhi");
        city = address.split("," , 1)[0];
        prepareHospitalList();
        setupActionBar();
    }

    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     */
    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).


            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
                String hospital = listPreference.getEntryValues()[prefIndex].toString();
                boolean isHospitalUpdated = DatabaseOperations.updateHospital(childId, hospital, getApplicationContext());

                if (!isHospitalUpdated) {
                    return false;
                }

            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.

            new GetLocationTask(stringValue , preference).execute((Void) null);
            /*preference.setSummary(stringValue);*/
        }
        return true;
    }


    String JSONStr;
    GetLocationTask getLocationTask;
    Context mContext;

    public class GetLocationTask extends AsyncTask<Void, Void, Boolean> {
        private String newCity;
        private String newAddress;
        private String pincode;
        private String region;
        private boolean networkFailed;
        EditTextPreference location;
        Preference preference;
        JSONObject jsonObject;

        GetLocationTask(String pincode, Preference locationPref) {
            this.pincode = pincode;
            newCity=city;
            this.region = "in";
            newAddress = address;
            this.location = (EditTextPreference) locationPref;
            preference = locationPref;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Uri.Builder loginUrlBuilder = new Uri.Builder();
            loginUrlBuilder.scheme("http")
                    .authority("maps.googleapis.com")
                    .appendPath("maps")
                    .appendPath("api")
                    .appendPath("geocode")
                    .appendPath("json")
                    .appendQueryParameter("address", pincode)
                    .appendQueryParameter("region", region);
            HttpURLConnection urlConnection;
            try {
                // Simulate network access.
                URL url = new URL(loginUrlBuilder.build().toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    //Nothing to do
                    return false;
                }
                InputStreamReader stream = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(stream);
                String line = "";
                StringBuffer Output = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    Output.append(line + "\n");
                }

                JSONStr = Output.toString();
                //Thread.sleep(500);


            } catch (MalformedURLException e) {
                e.printStackTrace();
                networkFailed = true;

            } catch (IOException e) {

                e.printStackTrace();
                networkFailed = true;

            } /*catch (InterruptedException e) {
                e.printStackTrace();
                networkFailed = true;
            }*/

            if (networkFailed) {
                return false;
            }

            try {
                jsonObject = new JSONObject(JSONStr);
                String error = jsonObject.getString("status");
                if (error.equals("OK")) {
                    return true;
                } else {
                    return false;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            getLocationTask = null;
            AlertDialog.Builder alertDialog;
            if (success) {
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject res1 = results.getJSONObject(0);
                    newAddress = res1.getString("formatted_address");
                    String adr[] = newAddress.split("," ,1);
                    newCity = adr[0].trim();

                    alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("Confirm");
                    alertDialog.setMessage(newAddress);

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DatabaseOperations.updateLocation(childId, city, pincode, mContext);
                            String stringValue = newAddress;
                            preference.setSummary(stringValue);
                            address = newAddress;
                            city = newCity;
                            prepareHospitalList();

                        }
                    });

                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    alertDialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (networkFailed) {
                Toast.makeText(mContext,
                        "No Network Access", Toast.LENGTH_LONG)
                        .show();

                //launchActivity();

            } else {
                Toast.makeText(mContext,
                        "Please Enter a valid pin code", Toast.LENGTH_LONG)
                        .show();
            }
        }

        @Override
        protected void onCancelled() {
            getLocationTask = null;

        }

    }

    void prepareHospitalList(){
        String HospitalEntries[] = DatabaseOperations.getHospitalList(mContext);
        String HospitalEntriesValues[] = DatabaseOperations.getHospitalValues(mContext);
        /*Toast.makeText(mContext , ""+HospitalEntries.length , Toast.LENGTH_LONG ).show();*/
        ListPreference hospitals = (ListPreference) findPreference(getString(R.string.pref_key_hospital));
        hospitals.setEntries(HospitalEntries);
        hospitals.setEntryValues(HospitalEntriesValues);
    }


}
