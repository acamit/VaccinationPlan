package com.example.android.vaccinationplan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class VaccineDetailActivityFragment extends Fragment {

    VaccineDetailHolder holder;
    public VaccineDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_vaccine_detail, container, false);

        Intent intent = getActivity().getIntent();
        final String vacId = intent.getStringExtra(Intent.EXTRA_TEXT);

        Cursor result = DatabaseOperations.getFullVaccineDetail(getActivity().getApplicationContext(), vacId);


        holder = new VaccineDetailHolder(result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_SHORT_FORM)),
                result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_NAME)),
                result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_PREVENTS)),
                result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_INFO)),
                result.getInt(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_SCHEDULE)),
                result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_RECOMMEND)),
                result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_ID))
        );

        holder.vaccineShortForm = (TextView) rootView.findViewById(R.id.vaccineShortForm);
        holder.vaccineFullForm = (TextView) rootView.findViewById(R.id.vaccineFullForm);
        holder.vaccinePrevents = (TextView) rootView.findViewById(R.id.vaccinePrevents);
        holder.vaccineInfo = (TextView) rootView.findViewById(R.id.vaccineInfo);
        holder.vaccineRecommend = (TextView) rootView.findViewById(R.id.vaccineRecommend);
        holder.vaccineTime = (TextView) rootView.findViewById(R.id.vaccineTime);
        holder.vaccineStatus = (TextView) rootView.findViewById(R.id.vaccineStatus);

        holder.vaccineGiven = (Button) rootView.findViewById(R.id.vaccineGiven);
        holder.vaccineSkip = (Button) rootView.findViewById(R.id.vaccineSkip);

        holder.vaccineShortForm.setText(holder.shortForm);
        holder.vaccineFullForm.setText(holder.fullForm);
        holder.vaccinePrevents.setText(holder.prevents);
        holder.vaccineInfo.setText(holder.info);
        holder.vaccineRecommend.setText(holder.recommend);
        holder.vaccineTime.setText(holder.time);
        holder.vaccineStatus.setText("Status : " + holder.status);

        holder.vaccineGiven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder alertDialog;
                alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Vaccine Given");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean var = DatabaseOperations.vaccineGiven(getActivity().getApplicationContext(),vacId);
                        if (var) {
                            v.setClickable(false);
                            v.setVisibility(View.INVISIBLE);
                            holder.vaccineSkip.setVisibility(View.INVISIBLE);
                            holder.status = "Given";
                            holder.vaccineStatus.setText("Status : " + holder.status);
                            DatabaseOperations.setUpdateStatus(getActivity().getApplicationContext() , 0);
                        }
                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


                alertDialog.show();


            }
        });

        holder.vaccineSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( final View v) {
                AlertDialog.Builder alertDialog;
                alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Are You Sure you want to skip this Vaccine." +
                        " Please Consult your Doctor");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean var = DatabaseOperations.vaccineSkip(getActivity().getApplicationContext(),vacId);
                        if (var) {
                            v.setClickable(false);
                            v.setVisibility(View.INVISIBLE);
                            holder.status = "Skipped";
                            holder.vaccineStatus.setText("Status : " + holder.status);
                            DatabaseOperations.setUpdateStatus(getActivity().getApplicationContext() , 0);
                        }

                    }
                });

                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


                alertDialog.show();


            }
        });

        if (holder.i == 1){
            holder.vaccineGiven.setVisibility(View.INVISIBLE);
            holder.vaccineSkip.setVisibility(View.INVISIBLE);
        }else if(holder.i == -1){
            holder.vaccineSkip.setVisibility(View.INVISIBLE);
        }



        return rootView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_vaccine_complete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settings = new Intent(getActivity() , SettingsActivity.class);
            startActivity(settings);
            return true;
        } else if (id == R.id.hospital_details) {
            Intent hospitals = new Intent(getActivity(), HospitalDetailActivity.class);
            startActivity(hospitals);
        }else if (id== R.id.home){
            Intent home = new Intent(getActivity() ,MainActivity.class);
            startActivity(home);
        }
        return super.onOptionsItemSelected(item);
    }

    private class VaccineDetailHolder{

        String shortForm,fullForm,prevents,info,time,recommend,status;
        int i;
        TextView vaccineShortForm,vaccineFullForm,vaccinePrevents,vaccineInfo,vaccineTime,vaccineRecommend,vaccineStatus;
        Button vaccineGiven,vaccineSkip;

        VaccineDetailHolder(String sf,String ff,String pre, String inf, int tym, String rec,String id){
            this.shortForm = sf;
            this.fullForm = ff;
            this.prevents = pre;
            this.info = inf;
            this.recommend = rec;

            if(tym == 0){
                this.time = "At Birth";
            }else if(tym < 14){
                this.time = tym + " weeks from Birth";
            }else{
                tym = tym*7/30;
                this.time = tym + " months from Birth";
            }
            i = DatabaseOperations.getVaccineStatus(getActivity().getApplicationContext(),id);

            if(i == 0){
                this.status = "Pending";
            }else if(i == 1){
                this.status = "Given";
            }else{
                this.status = "Skipped";
            }


        }
    }

}
