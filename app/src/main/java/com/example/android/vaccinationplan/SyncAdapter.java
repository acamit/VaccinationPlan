package com.example.android.vaccinationplan;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by amit on 29/4/16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    Context mContext;
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContext = context;
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContext = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        if(DatabaseOperations.getSynchronizationStatus(mContext)) {
            String childId ;
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
            String Tag = mContext.getString(R.string.pref_key_child_id);
            childId = pref.getString(Tag , "000000");


            HttpURLConnection urlConnection;
            Boolean networkFailed;
            Uri.Builder loginUrlBuilder = new Uri.Builder();
            loginUrlBuilder.scheme("http")
                    .authority("vaccinationplan.esy.es")
                    .appendPath("synchronize.php");
            Cursor VaccineStatus = DatabaseOperations.getVaccinationStatus(getContext() , childId);
            int length = VaccineStatus.getColumnCount();
            String vaccineName;
            String status;
            for(int i=0;i<length;i++){
                vaccineName = VaccineStatus.getColumnName(i);
                status = VaccineStatus.getString(i);
                loginUrlBuilder.appendQueryParameter(vaccineName , status);
            }

            Log.d("Synchronization URL" , loginUrlBuilder.build().toString());
            try {
                URL url = new URL(loginUrlBuilder.build().toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream hospitalInputStream = urlConnection.getInputStream();
                if (hospitalInputStream == null) {
                    //Nothing to do

                }
                InputStreamReader hospitalStream = new InputStreamReader(hospitalInputStream);
                BufferedReader hospitalReader = new BufferedReader(hospitalStream);
                String hospitalLine;
                StringBuffer hospitalOutput = new StringBuffer();
                while ((hospitalLine = hospitalReader.readLine()) != null) {
                    hospitalOutput.append(hospitalLine);
                }

            } catch (MalformedURLException e) {
                networkFailed = true;
                e.printStackTrace();
            } catch (ProtocolException e) {
                networkFailed = true;
                e.printStackTrace();
            } catch (IOException e) {
                networkFailed = true;
                e.printStackTrace();
            }
        }
    }
}
