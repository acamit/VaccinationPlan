package com.example.android.vaccinationplan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

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
        String id = intent.getStringExtra(Intent.EXTRA_TEXT);
        Toast.makeText(getActivity().getApplicationContext(),
                "Position :" + id, Toast.LENGTH_LONG)
                .show();


        holder = new VaccineDetailHolder("BCG",
                "Bacillus Calmette Guerin",
                "Tuberculosis",
                "It causes slight swelling at the injected site. Do not apply any medicine on the swollen area",
                "At Birth",
                "Mandatory");

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
            public void onClick(View v) {
                AlertDialog.Builder alertDialog;
                alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Vaccine Given");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
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
            public void onClick(View v) {
                AlertDialog.Builder alertDialog;
                alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Are You Sure you want to skip this Vaccine." +
                        " Please Consult your Doctor");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
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

        TextView vaccineShortForm,vaccineFullForm,vaccinePrevents,vaccineInfo,vaccineTime,vaccineRecommend,vaccineStatus;
        Button vaccineGiven,vaccineSkip;

        VaccineDetailHolder(String sf,String ff,String pre, String inf, String tym, String rec){
            this.shortForm = sf;
            this.fullForm = ff;
            this.prevents = pre;
            this.info = inf;
            this.time = tym;
            this.recommend = rec;
            this.status = "Pending";
        }
    }

}
