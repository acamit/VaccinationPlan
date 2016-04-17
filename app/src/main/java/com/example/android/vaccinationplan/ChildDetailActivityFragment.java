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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * A placeholder fragment containing a simple view.
 */
public class ChildDetailActivityFragment extends Fragment {
    private Calendar calendar;
    private View rootView;
    private Button changeDate, submitDetails;
    private SharedPreferences pref;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    Context mContext;
    int num_of_children;
    DialogFragment dateFragment;

    protected Child child;


    public ChildDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_child_detail, container, false);

        changeDate = (Button) rootView.findViewById(R.id.changeDate);

        updateDateButtonText();

        changeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        submitDetails = (Button) rootView.findViewById(R.id.SubmitDetails);
        submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addChildInfo();
            }
        });

        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) rootView.findViewById(R.id.autocomplete);
        autocompleteView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));

        autocompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });

        AutoCompleteTextView place_of_birthView = (AutoCompleteTextView) rootView.findViewById(R.id.place_of_birth);
        place_of_birthView.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.autocomplete_list_item));

        place_of_birthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getContext();
        calendar = Calendar.getInstance();
        num_of_children = 1;
        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(getString(R.string.pref_key_child_count), num_of_children + "");
        edit.commit();

        super.onCreate(savedInstanceState);
    }


    public void showDatePickerDialog(View v) {
        dateFragment = new DatePickerFragment();
        dateFragment.show(getActivity().getFragmentManager(), "Select Date");

    }


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


    public void addChildInfo() {
        boolean isInfoRecieved;
        isInfoRecieved = getChildInfo();
        if (isInfoRecieved) {
            DatabaseOperations.insertIntoChildDetails(child);

        }
    }

    private boolean getChildInfo() {

        int errorCount = 0;
        TextView nameView = (TextView) rootView.findViewById(R.id.ChildName);
        String nameValue = nameView.getText().toString().trim();
        String pattern = "^[\\p{L} .'-]+$";
        if (nameValue.equals("")) {
            nameView.setError("Please Input the name");
            errorCount++;
        } else if (!(nameValue.matches(pattern))) {
            nameView.setError("Only Characters and spaces allowed");
            nameView.setText("");
            nameValue = "";
            errorCount++;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date today = new Date(System.currentTimeMillis());
        Date dateInput;
        String dateSet = changeDate.getText().toString();
        try {
            dateInput = dateFormat.parse(dateSet);

            if (dateInput.after(today)) {
                changeDate.setError("Date must be set to past");
                errorCount++;
                calendar = Calendar.getInstance();
                updateDateButtonText();
            }
        } catch (ParseException e) {
            changeDate.setError("Please set a valid Date");
            errorCount++;
            e.printStackTrace();
        }

        return true;
    }





}
