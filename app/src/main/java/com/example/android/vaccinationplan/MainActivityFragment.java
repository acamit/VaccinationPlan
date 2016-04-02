package com.example.android.vaccinationplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main , menu);
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
            Intent hospitals = new Intent(getActivity(), HospitalDetail.class);
            startActivity(hospitals);
        }else if (id== R.id.vaccine_record){
            Intent vaccine_record = new Intent(getActivity() ,VaccinesCompletedActivity.class);
            startActivity(vaccine_record);
        }
        return super.onOptionsItemSelected(item);
    }
}
