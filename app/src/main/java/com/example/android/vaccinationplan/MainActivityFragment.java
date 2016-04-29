package com.example.android.vaccinationplan;

import android.accounts.Account;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    // ListAdapter  mVaccineAdapter;
    CustomAdapterMain adapterMain;
    HashMap<String, Object> temp;
    ArrayList vaccines;
    // ViewHolder viewHolder;
    LayoutInflater inflater;
    boolean[] checkBoxState;
    AlertDialog.Builder alertDialog;

    TextView topVaccineSF, topVaccineFF, topVaccinetym, topVaccineRec;
    Button topVaccineButton;
    String[] vaccinationList;
    String[] vaccineFullName;
    String[] vaccineRecommendation;
    String[] vaccineTime;
    String[] vaccineId;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

      /*  String[] vaccinationList = {"BCG1","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG"};
        String[] vaccineFullName = {"Bacillus Calmette Guerin 1","Bacillus Calmette Guerin","Bacillus Calmette Guerin","Bacillus Calmette Guerin","Bacillus Calmette Guerin","Bacillus Calmette Guerin","Bacillus Calmette Guerin","Bacillus Calmette Guerin","Bacillus Calmette Guerin","Bacillus Calmette Guerin"};
        String[] vaccineRecommendation = {"mandatory 1","mandatory","mandatory","mandatory","mandatory","mandatory","mandatory","mandatory","mandatory","mandatory","mandatory"};
        String[] vaccineTime = {"1 month to go 1","1 month to go","1 month to go","1 month to go","1 month to go","1 month to go","1 month to go","1 month to go","1 month to go","1 month to go","1 month to go"};
*/
        SimpleDateFormat currSimpleDate = new SimpleDateFormat("yyyy-MM-dd");
        Date currDate = new Date();
        Date dob = null;
        long diff, diffDays = 0;
        int diffWeeks = 0;




        dob = DatabaseOperations.getChildDob(getActivity().getApplicationContext());
        if (dob != null) {
            diff = currDate.getTime() - dob.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);
            diffWeeks = (int) diffDays / (7);
        }

        Cursor result = DatabaseOperations.vaccineList(getActivity().getApplicationContext());

        vaccinationList = new String[result.getCount()];
        vaccineFullName = new String[result.getCount()];
        vaccineRecommendation = new String[result.getCount()];
        vaccineTime = new String[result.getCount()];
        vaccineId = new String[result.getCount()];

        result.moveToFirst();
        int j = 0;
        do {
            vaccinationList[j] = result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_SHORT_FORM));
            vaccineFullName[j] = result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_NAME));
            vaccineRecommendation[j] = result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_RECOMMEND));
            vaccineTime[j] = result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_SCHEDULE));
            vaccineId[j] = result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_ID));
            j++;
        } while (result.moveToNext());


        for (j = 0; j < vaccineTime.length; j++) {
            long timeLeft = Integer.parseInt(vaccineTime[j]) - diffWeeks;

            if (timeLeft < 0) {
                vaccineTime[j] = "Time Passed Away";
            } else if (timeLeft == 0) {
                vaccineTime[j] = "Last Week Left";
            } else if (timeLeft > 1 && timeLeft <= 4) {
                vaccineTime[j] = timeLeft + " weeks to go";
            } else if (timeLeft * 7 / (30) == 1) {
                vaccineTime[j] = "1 month to go";
            } else {
                int months = (int) (timeLeft * 7 / (30));
                vaccineTime[j] = months + " months to go";
            }

        }


        vaccines = new ArrayList<>();

        //temporary HashMap for populating the
        //Items in the ListView


        //total number of rows in the ListView
        int noOfVaccines = vaccinationList.length;

        //now populate the ArrayList players

        try {
            for (int i = 0; i < noOfVaccines; ++i) {
                temp = new HashMap<>();

                temp.put("vaccine", vaccinationList[i]);
                temp.put("fullName", vaccineFullName[i]);
                temp.put("recommendation", vaccineRecommendation[i]);
                temp.put("time", vaccineTime[i]);
                temp.put("id", vaccineId[i]);

                vaccines.add(temp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        checkBoxState = new boolean[vaccines.size()];

        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        //final LayoutInflater factory = getLayoutInflater(savedInstanceState);
        /*final View textEntryView = listView.inflate(getActivity().getApplicationContext(),R.layout.main_list_view,null);
        viewHolder=new ViewHolder();
        viewHolder.checkBox1 = (CheckBox) textEntryView.findViewById(R.id.checkBox1);
        viewHolder.code = (TextView) textEntryView.findViewById(R.id.code);
        viewHolder.fullNameText = (TextView) textEntryView.findViewById(R.id.fullNameText);
        viewHolder.recommendationText = (TextView) textEntryView.findViewById(R.id.recommendationText);
        viewHolder.timeText = (TextView) textEntryView.findViewById(R.id.timeText);

        textEntryView.setTag(viewHolder);
*/
        /*mVaccineAdapter = new SimpleAdapter(getActivity().getApplicationContext(),
                vaccines,
                R.layout.main_list_view,
                new String[] {"vaccine",
                                "fullName",
                                "recommendation",
                                "time",
                                "id"},
                new int[] {viewHolder.code.getId(),
                            viewHolder.fullNameText.getId(),
                            viewHolder.recommendationText.getId(),
                            viewHolder.timeText.getId(),
                            viewHolder.checkBox1.getId()}
                );
*/


        adapterMain = new CustomAdapterMain(getActivity().getApplicationContext(),
                R.layout.main_list_view,
                vaccines);




        /* Screen Height adjustment */

      /*  int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < adapterMain.getCount(); i++) {
            view = adapterMain.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapterMain.getCount() - 1));
        listView.setLayoutParams(params);
*/

        listView.setAdapter(adapterMain);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                temp = (HashMap) vaccines.get(position);
                String itemValue = temp.get("vaccine").toString();
//                int ids = Integer.parseInt(temp.get("id").toString());

                Intent vaccine_detail = new Intent(getActivity().getApplicationContext(), VaccineDetailActivity.class).putExtra(Intent.EXTRA_TEXT, vaccineId[position]);
                startActivity(vaccine_detail);


            }

        });

      /* viewHolder.checkBox1.setChecked(checkBoxState[((int) vaccines.get(Integer.parseInt("id")))]);
       viewHolder.checkBox1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(((CheckBox)v).isChecked())
                   checkBoxState[v.getVerticalScrollbarPosition()]=true;
               else
                   checkBoxState[(v.getVerticalScrollbarPosition())]=false;
           }
       });
*/
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settings = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settings);
            return true;
        } else if (id == R.id.hospital_details) {
            Intent hospitals = new Intent(getActivity(), HospitalDetailActivity.class);
            startActivity(hospitals);
        } else if (id == R.id.vaccine_record) {
            Intent vaccine_record = new Intent(getActivity(), VaccinesCompletedActivity.class);
            startActivity(vaccine_record);
        }
        return super.onOptionsItemSelected(item);
    }


    public class CustomAdapterMain extends ArrayAdapter<HashMap<String, Object>> {

        boolean[] checkBoxState;
        Context context;
        ViewHolder viewHolder;
        AlertDialog.Builder alertDialog;
        ArrayList<HashMap<String, Object>> vaccines;

        public CustomAdapterMain(Context context, int textViewResourceId, ArrayList<HashMap<String, Object>> vaccines) {
            //let android do the initializing :)
            super(context, textViewResourceId, vaccines);
            this.context = context;
            this.vaccines = vaccines;
            checkBoxState = new boolean[vaccines.size()];
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.main_list_view, parent, false);
                viewHolder = new ViewHolder();

                try {
                    viewHolder.checkBox1 = (CheckBox) convertView.findViewById(R.id.checkBox1);
                    viewHolder.code = (TextView) convertView.findViewById(R.id.code);
                    viewHolder.fullNameText = (TextView) convertView.findViewById(R.id.fullNameText);
                    viewHolder.recommendationText = (TextView) convertView.findViewById(R.id.recommendationText);
                    viewHolder.timeText = (TextView) convertView.findViewById(R.id.timeText);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //link the cached views to the convertview
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            try {
                viewHolder.timeText.setText(vaccines.get(position).get("time").toString());
                viewHolder.recommendationText.setText(vaccines.get(position).get("recommendation").toString());
                viewHolder.fullNameText.setText(vaccines.get(position).get("fullName").toString());
                viewHolder.code.setText(vaccines.get(position).get("vaccine").toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //VITAL PART!!! Set the state of the
            //CheckBox using the boolean array
            viewHolder.checkBox1.setChecked(checkBoxState[position]);


            //for managing the state of the boolean
            //array according to the state of the
            //CheckBox

            viewHolder.checkBox1.setOnClickListener(new View.OnClickListener() {

                public void onClick(final View v) {
                    if (((CheckBox) v).isChecked()) {
                        checkBoxState[position] = true;
                        alertDialog = new AlertDialog.Builder(getActivity());
                        alertDialog.setTitle("Confirm");
                        alertDialog.setMessage("Vaccine Given");
                        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                boolean var = DatabaseOperations.vaccineGiven(getActivity().getApplicationContext(), vaccineId[position]);
                                if (var) {

                                    v.setClickable(false);
                                }
                            }
                        });

                        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ((CheckBox) v).setChecked(false);
                                checkBoxState[position] = false;
                                dialog.cancel();
                            }
                        });


                        alertDialog.show();
                    } else
                        checkBoxState[position] = false;

                }
            });


            return convertView;
        }

        //class for caching the views in a row
        private class ViewHolder {

            TextView recommendationText, timeText, fullNameText, code;
            CheckBox checkBox1;
        }


    }




}


