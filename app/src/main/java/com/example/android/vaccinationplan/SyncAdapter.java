package com.example.android.vaccinationplan;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

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
        Boolean status = DatabaseOperations.getSynchronizationStatus(mContext);
        if (status) {
            Uri.Builder syncUriBuilder = new Uri.Builder();
            syncUriBuilder.scheme("http")
                    .authority("vaccinationplan.esy.es")
                    .appendPath("synchronize.php");

            Cursor vaccinationStatus = DatabaseOperations.getVaccinationStatus(mContext);
            if(vaccinationStatus.moveToFirst() && vaccinationStatus.getCount()>0){
                Log.e("Sync Cursor" , "Cursor Found");
                for(int i =0; i<vaccinationStatus.getColumnCount();i++){
                    syncUriBuilder.appendQueryParameter(vaccinationStatus.getColumnName(i) , vaccinationStatus.getString(i));
                }
            }else{
                Log.e("Sync Cursor" , "empty cursor");
            }
            String JsonStr;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(syncUriBuilder.build().toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    Log.e("Sync", "Sync Failed");
                    return;
                }
                InputStreamReader stream = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(stream);
                String line = "";
                StringBuffer Output = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    Output.append(line + "\n");
                }
                JsonStr = Output.toString();
                JSONObject obj = new JSONObject(JsonStr);
                String issync = obj.getString("status");
                if(issync.equals("OK")){
                    Log.e("Sync" , "Sync success");
                }else{
                    Log.e("Sync", "Sync Failed");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Sync", "No Sync required");
        }
    }
}
