package com.example.android.vaccinationplan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChildDetailActivityFragment extends Fragment{
    private Calendar calendar;
    private View rootView;
    private Button changeDate;
    private SharedPreferences pref;
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    Context mContext;
    int num_of_children;
    DialogFragment dateFragment;



    public ChildDetailActivityFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_child_detail, container, false);

        changeDate = (Button)rootView.findViewById(R.id.changeDate);

        updateDateButtonText();

        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getContext();
        calendar = Calendar.getInstance();

        num_of_children =1;
        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(getString(R.string.pref_key_child_count), num_of_children + "");
        edit.commit();

        super.onCreate(savedInstanceState);
    }


    public void showDatePickerDialog(View v) {
        dateFragment = new DatePickerFragment();
        dateFragment.show(getActivity().getFragmentManager() , "Select Date");

    }



  /*  //@SuppressWarnings("deprecation")
    public void setDate(View view) {

        Toast.makeText(getActivity().getApplicationContext(), "ca", Toast.LENGTH_SHORT)
                .show();
    }*/

    public void updateDateButtonText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateForButton = dateFormat.format(calendar.getTime());
        changeDate.setText(dateForButton);
    }
    class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            updateDateButtonText();
        }
    }

}
