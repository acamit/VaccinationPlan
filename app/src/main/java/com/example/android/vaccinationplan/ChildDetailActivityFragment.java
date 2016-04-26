package com.example.android.vaccinationplan;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
                changeDate.setError(null);
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
                //Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(getActivity(), description, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mContext = getContext();
        calendar = Calendar.getInstance();
       /* num_of_children = 1;
        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(getString(R.string.pref_key_child_count), num_of_children + "");
        edit.commit();*/

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
        boolean isInfoReceived;
        isInfoReceived = getChildInfo();
        if (isInfoReceived) {
            Boolean isChildDetailInserted = DatabaseOperations.insertIntoChildDetails(child, mContext);
            if(isChildDetailInserted){
                num_of_children =1;
                pref = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString(getString(R.string.pref_key_child_count), num_of_children + "");
                edit.putString(getString(R.string.pref_key_child_id) , child.child_id);
                edit.commit();
                Intent intent = new Intent(mContext , MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
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


        /*
        *  Date of Birth Validation
        *
        * */
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
            dateInput=today;
            changeDate.setError("Please set a valid Date");
            errorCount++;
            e.printStackTrace();
        }

        /*
        * Blood group validation
        * */
        TextView bloodGroup = (TextView)rootView.findViewById(R.id.bloodGroup);
        String bloodGroupValue = bloodGroup.getText().toString().trim().toUpperCase();
        String patternForBloodGroup = "^(A|B|AB|O)[+-]$";
        if(!bloodGroupValue.matches(patternForBloodGroup)){
            bloodGroup.setError("Please Enter a valid Blood Group");
            errorCount++;
            bloodGroup.setText("");
        }

        /*
        * Mothers name validation
        *
        * */

        TextView MotherName = (TextView)rootView.findViewById(R.id.MotherName);
        String MotherNameValue = MotherName.getText().toString().trim();
        if (!(MotherNameValue.matches(pattern))) {
            MotherName.setError("Only Characters and spaces allowed");
            MotherName.setText("");
            errorCount++;
        }


        /*
        * Mobile number verification
        **/

        TextView MobileNumber = (TextView)rootView.findViewById(R.id.MobileNumber);
        String MobileNumberValue = MobileNumber.getText().toString().trim();

        TelephonyManager tm = (TelephonyManager)mContext.getSystemService(mContext.TELEPHONY_SERVICE);
        String countryCode = tm.getNetworkCountryIso();


        if(validatePhoneNumber(MobileNumberValue)){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                MobileNumberValue=PhoneNumberUtils.formatNumber(MobileNumberValue , countryCode);
            }/*else{
                MobileNumberValue = PhoneNumberUtils.formatNumber(MobileNumberValue , "+91" , countryCode);
            }*/
            MobileNumber.setText(MobileNumberValue);
        }
        else{
            errorCount++;
            MobileNumber.setError("Please Enter a valid Mobile number");
        }

        /*Get Gender details*/
        String gender;
        RadioButton FemaleButton = (RadioButton)rootView.findViewById(R.id.FemaleGender);

        RadioButton MaleButton = (RadioButton)rootView.findViewById(R.id.MaleGender);
        if(FemaleButton.isChecked()){
            gender = "female";
        }else {
            gender = "male";
        }

       /*
       * Get Location Details
       *
       * */
        String PlaceOfBirthPin="0";
        String placeOfBirth= new String("");
        String currentLocationPin="0";
        String currentLocationName= new String("");

        TextView currentLocation = (TextView)rootView.findViewById(R.id.autocomplete);
        String currentLocationValue = currentLocation.getText().toString();
        if(currentLocationValue.matches("\\d{10}")){
           currentLocationPin = currentLocationValue;
        }else{
            currentLocationName= currentLocationValue;
        }

        TextView placeOfBirthView= (TextView)rootView.findViewById(R.id.place_of_birth);
        String PlaceOfBirthValue = placeOfBirthView.getText().toString();
        if(PlaceOfBirthValue.matches("\\d{10}")){
            PlaceOfBirthPin = PlaceOfBirthValue;
        }else{
            placeOfBirth = PlaceOfBirthValue;
        }

        /*
        * Get Hospital Details
        *
        * */

        /*TextView HospitalDetails = (TextView)rootView.findViewById(R.id.hospital_details);
        String HospitalDetailsValue = HospitalDetails.getText().toString();
        */
        if(errorCount>0){
            child=null;
            errorCount=0;
            return false;
        }else{
            child = new Child();
            child.name=nameValue;
            child.child_id="";
            child.date_of_birth= dateInput.toString();
            child.mother=MotherNameValue;
            child.place_of_birth=placeOfBirth;
            child.place_of_birthPin = PlaceOfBirthPin;
            child.gender=gender;
            child.curr_location = currentLocationName;
            child.blood_group = bloodGroupValue;
            child.update_status = "0";
            child.curr_locationPin = currentLocationPin;
            new getChildId().execute((Void)null);
            //child.preferred_hospital = HospitalDetailsValue;
        }

        return true;
    }

    private static boolean validatePhoneNumber(String phoneNo) {
        //validate phone numbers of format "1234567890"
        if (phoneNo.matches("\\d{10}")) return true;
            //validating phone number with -, . or spaces
        else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
            //validating phone number with extension length from 3 to 5
        /*else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
            //validating phone number where area code is in braces ()
        else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
        */    //return false if nothing matches the input
        else return false;

    }

    class getChildId extends AsyncTask<Void , Void , String>{
        private boolean networkFailed;
        private String JSONStr;
        private boolean hospitalDataLoaded;
        private boolean hospitalDataInflated;
        private String hospitalJson;

        @Override
        protected String doInBackground(Void... params) {
            Uri.Builder loginUrlBuilder = new Uri.Builder();
            loginUrlBuilder.scheme("http")
                    .authority("vaccinationplan.esy.es")
                    .appendPath("getChildId.php")
                    .appendQueryParameter("email" , "mEmail");
            HttpURLConnection urlConnection ;

            Uri.Builder hospitalDatalink = new Uri.Builder();
            hospitalDatalink.scheme("http")
                    .authority("vaccinationplan.esy.es")
                    .appendPath("hospitals.php")
                    .appendQueryParameter("city","amritsar");

            HttpURLConnection hospitalConnection;



            try {
                // Simulate network access.
                URL url = new URL(loginUrlBuilder.build().toString());
                urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    //Nothing to do
                    return "";
                }
                InputStreamReader stream = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(stream);
                String line ="";
                StringBuffer Output = new StringBuffer();
                while ((line = reader.readLine()) != null){
                    Output.append(line+"\n");
                }
                JSONStr = Output.toString();
                //Thread.sleep(500);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                networkFailed = true;

            } catch (IOException e) {

                e.printStackTrace();
                networkFailed = true;

            } /*catch (InterruptedException e) {
                e.printStackTrace();
                networkFailed = true;
            }*/

            if(networkFailed){
                return "";
            }

            try{
                URL hospitalUrl = new URL(hospitalDatalink.build().toString());

                hospitalConnection = (HttpURLConnection) hospitalUrl.openConnection();
                hospitalConnection.setRequestMethod("GET");
                hospitalConnection.connect();
                InputStream hospitalInputStream = hospitalConnection.getInputStream();
                if (hospitalInputStream == null) {
                    //Nothing to do
                    return "";
                }
                InputStreamReader hospitalStream = new InputStreamReader(hospitalInputStream);
                BufferedReader hospitalReader = new BufferedReader(hospitalStream);
                String hospitalLine ;
                StringBuffer hospitalOutput = new StringBuffer();
                while ((hospitalLine = hospitalReader.readLine()) != null) {
                    hospitalOutput.append(hospitalLine);
                }

                hospitalJson = hospitalOutput.toString();
                hospitalDataLoaded = true;


            }catch (Exception e){
                e.printStackTrace();
                //hospitalDataLoaded = false;
            }

            if(hospitalDataLoaded){
                try{

                    JSONObject hospitalObject = new JSONObject(hospitalJson);
                    JSONArray hospitalArray = hospitalObject.getJSONArray("hospitals");

                    DatabaseOperations.inflateHospitals(hospitalArray, mContext);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            try {
                JSONObject obj = new JSONObject(JSONStr);
                String child_id = obj.getString("child_id");
                return child_id;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "";
        }
        @Override
        protected void onPostExecute(String child_id){
         child.child_id=child_id;
        }
    }
}
