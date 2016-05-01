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
public class SettingsActivity extends AppCompatPreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    Context mContext;
    String childId;
    private String city;
    private String address;
    GetLocationTask getLocationTask;
    String Hospital;
    ListPreference pref1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add 'general' preferences, defined in the XML file
        addPreferencesFromResource(R.xml.pref_general);

        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
        // updated when the preference changes.

        mContext = SettingsActivity.this;
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_location)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key_hospital)));
        setupActionBar();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);

        childId = pref.getString("childId", "");
        address = pref.getString(getString(R.string.pref_key_location) ,"New Delhi");
        Hospital= pref.getString(getString(R.string.pref_key_hospital) ,"");
        findPreference(getString(R.string.pref_key_location)).setSummary(address);
        prepareHospitalList();
        pref1 = (ListPreference)findPreference(getString(R.string.pref_key_hospital));
        int prefIndex = pref1.findIndexOfValue(Hospital);
        if(prefIndex>0){
            pref1.setSummary(pref1.getEntries()[prefIndex]);
        }
        city = address.split(",")[0];
    }

    /**
     * Attaches a listener so the summary is always updated with the preference value.
     * Also fires the listener once, to initialize the summary (so it shows up before the value
     * is changed.)
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
                    //Toast.makeText(this , ""+isHospitalUpdated , Toast.LENGTH_LONG).show();
                    return false;
                }else{
                    //Toast.makeText(this , ""+isHospitalUpdated , Toast.LENGTH_LONG).show();
                }
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            if(preference.getKey().equals(getString(R.string.pref_key_location))){
                new GetLocationTask(stringValue , preference).execute((Void) null);

            }else{

            }

        }
        return true;
    }


    void prepareHospitalList() {
        String HospitalEntries[] = DatabaseOperations.getHospitalList(mContext);
        String HospitalEntriesValues[] = DatabaseOperations.getHospitalValues(mContext);
        ListPreference hospitals = (ListPreference) findPreference(getString(R.string.pref_key_hospital));
        hospitals.setEntries(HospitalEntries);
        hospitals.setEntryValues(HospitalEntriesValues);
    }


    public class GetLocationTask extends AsyncTask<Void, Void, Boolean> {
        private String newCity;
        private String newAddress;
        private String pincode;
        private String region;
        private boolean networkFailed;
        EditTextPreference location;
        Preference preference;
        JSONObject jsonObject;
        String JSONStr;


        GetLocationTask(String pincode, Preference locationPref) {
            this.pincode = pincode;
            newCity = "";
            this.region = "in";
            newAddress = "";
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
                    String adr[] = newAddress.split(",");
                    newCity = adr[0].trim();

                    alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("Confirm");
                    alertDialog.setMessage(newAddress);

                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            if(DatabaseOperations.updateLocation(childId, city, pincode, mContext)) {
                                /*Toast.makeText(mContext,
                                        "Location Updated", Toast.LENGTH_LONG)
                                        .show();*/
                                String stringValue = newAddress;
                                preference.setSummary(stringValue);
                                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putString(getString(R.string.pref_key_location), newAddress);
                                edit.putString(getString(R.string.pref_key_location_pin), pincode);
                                edit.commit();

                                address = newAddress;
                                city = newCity;
                                new HospitalTask().execute();

                            }
                            else {
                                /*Toast.makeText(SettingsActivity.this,
                                        "Update Location Failed", Toast.LENGTH_LONG)
                                        .show();
*/
                            }
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


    public class HospitalTask extends AsyncTask<Void, Void, Boolean> {
        private boolean hospitalDataLoaded;
        private boolean hospitalDataInflated;
        private String hospitalJson;
        HttpURLConnection hospitalConnection;
        @Override
        protected Boolean doInBackground(Void... params) {

            Uri.Builder hospitalDatalink = new Uri.Builder();
            hospitalDatalink.scheme("http")
                    .authority("vaccinationplan.esy.es")
                    .appendPath("hospitals.php")
                    .appendQueryParameter("city", city);
            try {
                URL hospitalUrl = new URL(hospitalDatalink.build().toString());

                hospitalConnection = (HttpURLConnection) hospitalUrl.openConnection();
                hospitalConnection.setRequestMethod("GET");
                hospitalConnection.connect();
                InputStream hospitalInputStream = hospitalConnection.getInputStream();
                if (hospitalInputStream == null) {
                    //Nothing to do
                    return false;
                }
                InputStreamReader hospitalStream = new InputStreamReader(hospitalInputStream);
                BufferedReader hospitalReader = new BufferedReader(hospitalStream);
                String hospitalLine;
                StringBuffer hospitalOutput = new StringBuffer();
                while ((hospitalLine = hospitalReader.readLine()) != null) {
                    hospitalOutput.append(hospitalLine);
                }

                hospitalJson = hospitalOutput.toString();
                hospitalDataLoaded = true;


            } catch (Exception e) {

                e.printStackTrace();
                return false;
                //hospitalDataLoaded = false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (hospitalDataLoaded) {
                try {

                    JSONObject hospitalObject = new JSONObject(hospitalJson);
                    JSONArray hospitalArray = hospitalObject.getJSONArray("hospitals");

                    DatabaseOperations.inflateHospitals(hospitalArray, mContext);
                    prepareHospitalList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onCancelled() {
            getLocationTask = null;

        }

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

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

}