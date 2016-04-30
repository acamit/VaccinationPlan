package com.example.android.vaccinationplan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class HospitalDetailActivityFragment extends Fragment {

    Hospital hospital;
    public HospitalDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_hospital_detail, container, false);


        int hospital_id =  DatabaseOperations.getPrefferedHospital(getActivity().getApplicationContext());

        if(hospital_id == -1){
            Toast.makeText(getActivity().getApplicationContext(),"Select Prefferd Hospital",Toast.LENGTH_LONG).show();
        }else {

            Cursor result = DatabaseOperations.fetchHospitalDetails(getActivity().getApplicationContext(),hospital_id);
            String phone,address;

            phone = result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_PHONE));
            address = result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_ADDRESS));

            Log.v("phone",String.valueOf(phone));

            if(phone == null && address == null){
                Toast.makeText(getActivity().getApplicationContext(),"Fetching More Data",Toast.LENGTH_LONG).show();
                new getHospitalData().execute(String.valueOf(hospital_id));
            }


                hospital = new Hospital(result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_NAME)),
                        result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_ADDRESS)),
                        result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_CITY)),
                        result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_PHONE)));



            hospital.nameHospital = (TextView) rootView.findViewById(R.id.nameHospital);
            hospital.addressHospital = (TextView) rootView.findViewById(R.id.addressHospital);
            hospital.phoneHospital = (TextView) rootView.findViewById(R.id.phoneHospital);
            hospital.callHospital = (Button) rootView.findViewById(R.id.callHospital);
            hospital.viewHospital = (Button) rootView.findViewById(R.id.viewHospital);

            inflationFunction();

        }
        return rootView;

    }

    private class Hospital{
        String name, address, city, phoneNo;
        String[] phoneNos;
        TextView nameHospital,addressHospital,phoneHospital;
        Button callHospital,viewHospital;

        Hospital(String name,String address,String city,String phoneNo){
            this.name = name;
            this.address = address;
            this.city = city;
            this.phoneNo = phoneNo;
            if(phoneNo != null){
                this.phoneNos = phoneNo.split(",");
            }else{
                this.phoneNos = new String[1];
                this.phoneNos[0] = phoneNo;
            }

        }

    }

    class getHospitalData extends AsyncTask<String, Void, String> {
        boolean networkFailed = false;

        @Override
        protected String doInBackground(String... params) {

            String hospitalJson;
            Uri.Builder hospitalDatalink = new Uri.Builder();
            hospitalDatalink.scheme("http")
                    .authority("vaccinationplan.esy.es")
                    .appendPath("hospitals.php")
                    .appendQueryParameter("hos_id", params[0]);

            HttpURLConnection hospitalConnection;

            try{
                URL hospitalUrl = new URL(hospitalDatalink.build().toString());


                hospitalConnection = (HttpURLConnection) hospitalUrl.openConnection();
                hospitalConnection.setRequestMethod("GET");
                hospitalConnection.connect();
                InputStream hospitalInputStream = hospitalConnection.getInputStream();
                if (hospitalInputStream == null) {
                    //Nothing to do

                }
                InputStreamReader hospitalStream = new InputStreamReader(hospitalInputStream);
                BufferedReader hospitalReader = new BufferedReader(hospitalStream);
                String hospitalLine ;
                StringBuffer hospitalOutput = new StringBuffer();
                while ((hospitalLine = hospitalReader.readLine()) != null) {
                    hospitalOutput.append(hospitalLine);
                }

                hospitalJson = hospitalOutput.toString();


                JSONObject hospitalObject = new JSONObject(hospitalJson);
                JSONArray hospitalArray = hospitalObject.getJSONArray("hospitals");

                DatabaseOperations.inflateHospital(hospitalArray,getActivity().getApplicationContext());

               /* if (row){
                    db = helper.getReadableDatabase();
                    data = db.rawQuery("SELECT * FROM "+ DatabaseContract.HospitalDetails.TABLE_NAME + " WHERE `" + DatabaseContract.HospitalDetails._ID + "` = " + hospital_id,null);
                    data.moveToFirst();
                    return data;
                }else {
                    return data;
                }*/



            }catch (Exception e){
                e.printStackTrace();
                //hospitalDataLoaded = false;
                networkFailed = true;
            }


            return params[0];
        }

        @Override
        protected void onPostExecute (String hospital_id){
            if(networkFailed){
                Toast.makeText(getActivity().getApplicationContext(),"Internet Connection Required ",Toast.LENGTH_LONG).show();
                Intent intent = getActivity().getParentActivityIntent();
                getActivity().finish();
                startActivity(intent);
            }else {
                Cursor result = DatabaseOperations.fetchHospitalDetails(getActivity().getApplicationContext(),Integer.parseInt(hospital_id));
                hospital.name = result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_NAME));
                hospital.city = result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_CITY));
                hospital.address = result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_ADDRESS));
                hospital.phoneNo = result.getString(result.getColumnIndex(DatabaseContract.HospitalDetails.COLUMN_PHONE));
                hospital.phoneNos = hospital.phoneNo.split(",");
                inflationFunction();
            }
        }
    }


    public void inflationFunction(){

        /* Setting Data in the view */
        hospital.nameHospital.setText(hospital.name);
        hospital.phoneHospital.setText(hospital.phoneNo);
        hospital.addressHospital.setText(hospital.address);

        hospital.callHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hospital.phoneNos.length > 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Select The Phone Number");
                    AlertDialog numberDialog;
                    builder.setSingleChoiceItems(hospital.phoneNos, -1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int number) {
                            try {
                                String uri = "tel:" + hospital.phoneNos[number].trim();
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse(uri));
                                startActivity(intent);
                                dialog.cancel();
                            } catch (Exception e) {
                                //do nothing
                            }

                        }
                    });

                    numberDialog = builder.create();
                    numberDialog.show();
                } else {
                    String uri = "tel:" + hospital.phoneNos[0].trim();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    startActivity(intent);
                }


            }
        });

        hospital.viewHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0.0,0.0?q=" + Uri.encode(hospital.name + "," + hospital.city));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
    }

}
