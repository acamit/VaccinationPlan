package com.example.android.vaccinationplan;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class VaccinesCompletedActivityFragment extends Fragment {
    private ArrayAdapter<String> mVaccineAdapter;
    public VaccinesCompletedActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_vaccines_completed, container, false);

        String[] vaccinationList = {"BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG"};

        List<String> vaccineList = new ArrayList<>(Arrays.asList(vaccinationList));

        //create an ArrayAdaptar from the String Array
        mVaccineAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                R.layout.vaccine_completed_list_view,
                R.id.code,
                vaccineList);

        ListView listView = (ListView) rootView.findViewById(R.id.listView2);
        listView.setAdapter(mVaccineAdapter);


        return rootView;
    }
}
