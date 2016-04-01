package com.example.android.vaccinationplan;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

   private ArrayAdapter<String> mVaccineAdapter;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);


       String[] vaccinationList = {"asdasldalj dihfayfv","asdasldalj dihfayfv","asdasldalj dihfayfv","asdasldalj dihfayfv","asdasldalj dihfayfv","asdasldalj dihfayfv","asdasldalj dihfayfv","asdasldalj dihfayfv","asdasldalj dihfayfv",};

        List<String> vaccineList = new ArrayList<String>(Arrays.asList(vaccinationList));

        //create an ArrayAdaptar from the String Array
        mVaccineAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                                                    R.layout.main_list_view,
                                                    R.id.code,
                                                    vaccineList);

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(mVaccineAdapter);

        return rootView;
    }


}
