package com.example.android.vaccinationplan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildDetailActivityFragment extends Fragment {

    public ChildDetailActivityFragment() {
    }
    private SharedPreferences pref;
    Context mContext;
    int num_of_children;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_child_detail, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getContext();
        num_of_children =1;
        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(getString(R.string.pref_key_child_count), num_of_children + "");
        edit.commit();
        super.onCreate(savedInstanceState);
    }


}
