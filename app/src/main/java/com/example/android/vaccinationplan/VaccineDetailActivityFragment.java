package com.example.android.vaccinationplan;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class VaccineDetailActivityFragment extends Fragment {

    public VaccineDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vaccine_detail, container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_vaccine_complete , menu);
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


}
