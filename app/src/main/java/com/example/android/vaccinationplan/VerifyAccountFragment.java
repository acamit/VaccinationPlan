package com.example.android.vaccinationplan;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
 * A placeholder fragment containing a simple view.
 */
public class VerifyAccountFragment extends Fragment {

    View rootview;
    Button verifyButton;
    String token;
    String mEmail;
    String JSONStr;
    Intent returnIntent;
    private updateStatusOnline mAuthTask  = null;
    public VerifyAccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_verify_account, container, false);
        verifyButton = (Button)(rootview.findViewById(R.id.verifyButton));
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });

        return rootview;
    }

    public Intent verify(){
        TextView view = (TextView)(rootview.findViewById(R.id.verificationCode));
        Intent intent = getActivity().getIntent();
        token = intent.getStringExtra(Intent.EXTRA_TEXT);
        //mEmail = intent.getStringExtra("mEmail");
        String enteredValue = view.getText().toString();
        /*
            Toast.makeText(getContext(),
                token, Toast.LENGTH_LONG)
                .show();
        */
        String pattern = "^\\d{6}$";
        if(enteredValue.matches(pattern)){
            if(enteredValue.equals(token)){
                new updateStatusOnline().execute((Void) null);
            //  returnIntent.putExtra(Intent.EXTRA_TEXT, "VERIFIED");

            }else{
                view.setError("Invalid verification code");
                view.setText("");
                view.requestFocus();
            }

        }else{
            view.setError("Please enter 6 digit verification code");
            view.setText("");
            view.requestFocus();
        }

        return  returnIntent;
    }

    class updateStatusOnline extends AsyncTask<Void, Void , Boolean>{
        private boolean networkFailed;

        @Override
        protected Boolean doInBackground(Void... params) {

            Uri.Builder loginUrlBuilder = new Uri.Builder();
            loginUrlBuilder.scheme("http")
                    .authority("vaccinationplan.esy.es")
                    .appendPath("verify.php")
                    .appendQueryParameter("email" , mEmail)
                    .appendQueryParameter("code" , token);
            HttpURLConnection urlConnection ;
            try {
                // Simulate network access.
                URL url = new URL(loginUrlBuilder.build().toString());
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    //Nothing to do
                    return false;
                }
                InputStreamReader stream = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(stream);
                String line ="";
                StringBuffer Output = new StringBuffer();
                while ((line = reader.readLine()) != null){
                    Output.append(line+"\n");
                }
                JSONStr = Output.toString();
                //Thread.sleep(500);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                networkFailed = true;

            } catch (IOException e) {

                e.printStackTrace();
                networkFailed = true;

            }

            if(networkFailed){
                return  false;
            }

            try {
                JSONObject jsonObject = new JSONObject(JSONStr);
                String status = jsonObject.getString("status");
                if(status.matches("1")){
                    return  true;
                }
                else{
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            returnIntent = new Intent();
            if(aBoolean){
                returnIntent.putExtra(Intent.EXTRA_TEXT, "VERIFIED");
            }else{
                returnIntent.putExtra(Intent.EXTRA_TEXT, "Failed to Verify");
            }

            getActivity().setResult(1, returnIntent);
            getActivity().finish();

            super.onPostExecute(aBoolean);
        }
    }



    @Override
    public void onDestroy() {
        if(mAuthTask!=null && mAuthTask.getStatus()!= AsyncTask.Status.FINISHED){
            mAuthTask.cancel(true);
        }
        /*
        getActivity().setResult(1, returnIntent);
        getActivity().finish();
*/        super.onDestroy();
    }
}
