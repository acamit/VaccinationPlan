package com.example.android.vaccinationplan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class VaccinesCompletedActivityFragment extends Fragment {

    HashMap<String , Object> temp;
    ArrayList vaccines;

    CustomAdapterCompleted adapterMain;
    LayoutInflater inflater;


    String[] vaccinationList;
    String[] vaccineFullName;
    String[] vaccineRecommendation ;
    String[] vaccineSkipOrgiven;
    String[] vaccineId ;


    // private ArrayAdapter<String> mVaccineAdapter;
    public VaccinesCompletedActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_vaccines_completed, container, false);
        try{
        int[] skipOrGiven = DatabaseOperations.skipOrGiven(getActivity().getApplicationContext());
        Cursor result = DatabaseOperations.vaccineListDone(getActivity().getApplicationContext());



        vaccinationList = new String[result.getCount()];
        vaccineFullName = new String[result.getCount()];
        vaccineRecommendation = new String[result.getCount()];
        vaccineSkipOrgiven = new String[result.getCount()];
        vaccineId = new String[result.getCount()];

        result.moveToFirst();
        int j=0;
        do{
            vaccinationList[j] = result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_SHORT_FORM));
            vaccineFullName[j] = result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_NAME));
            vaccineRecommendation[j] = result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_RECOMMEND));
            vaccineId[j] = result.getString(result.getColumnIndex(DatabaseContract.VaccineDetails.COLUMN_ID));

            if(skipOrGiven[j] == -1){
                vaccineSkipOrgiven[j] = "Skipped";
            }else if(skipOrGiven[j] == 1){
                vaccineSkipOrgiven[j] = "Given";
            }

            j++;
        }while(result.moveToNext());

        vaccines = new ArrayList<>();

        //String[] vaccinationList = {"BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG","BCG"};

        // List<String> vaccineList = new ArrayList<>(Arrays.asList(vaccinationList));

        int noOfVaccines=vaccinationList.length;

        try {
            for (int i = 0; i < noOfVaccines; ++i) {
                temp = new HashMap<>();

                temp.put("vaccine", vaccinationList[i]);
                temp.put("fullName", vaccineFullName[i]);
                temp.put("recommendation", vaccineRecommendation[i]);
                temp.put("skipOrGiven", vaccineSkipOrgiven[i]);
                temp.put("id", vaccineId[i]);

                vaccines.add(temp);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        //create an ArrayAdaptar from the String Array
       /* mVaccineAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                R.layout.vaccine_completed_list_view,
                R.id.shortForm,
                vaccineList);
*/
        ListView listView = (ListView) rootView.findViewById(R.id.listView2);
        adapterMain = new CustomAdapterCompleted(getActivity().getApplicationContext(),
                R.layout.vaccine_completed_list_view,
                vaccines);



        listView.setAdapter(adapterMain);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                temp = (HashMap) vaccines.get(position);

                Intent vaccine_detail = new Intent(getActivity().getApplicationContext(), VaccineDetailActivity.class).putExtra(Intent.EXTRA_TEXT, vaccineId[position]);
                startActivity(vaccine_detail);


            }

        });

    }catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(),"No entry for this View Yet",Toast.LENGTH_LONG).show();
            Intent intent = getActivity().getParentActivityIntent();
            getActivity().finish();
            startActivity(intent);
    }


        return rootView;
    }

    public class CustomAdapterCompleted extends ArrayAdapter<HashMap<String,Object>>{

        Context context;
        ViewHolder viewHolder;
        AlertDialog.Builder alertDialog;
        ArrayList<HashMap<String, Object>> vaccines;

        public CustomAdapterCompleted(Context context, int resource, ArrayList<HashMap<String, Object>> vaccines) {
            super(context, resource,vaccines);
            this.context = context;
            this.vaccines = vaccines;
        }

        @Override
        public View getView(final int position,View convertView,ViewGroup parent){

            // TODO Auto-generated method stub
            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(convertView==null){

                convertView = inflater.inflate(R.layout.vaccine_completed_list_view, parent, false);
                viewHolder=new ViewHolder();
                try {
                    viewHolder.shortForm= (TextView) convertView.findViewById(R.id.shortFormR);
                    viewHolder.fullForm= (TextView) convertView.findViewById(R.id.fullFormR);
                    viewHolder.recommendationText= (TextView) convertView.findViewById(R.id.recommendTextR);
                    viewHolder.timeStatus= (TextView) convertView.findViewById(R.id.timeStatus);
                    viewHolder.undoButton = (Button) convertView.findViewById(R.id.buttonUndo);

                }catch (Exception e){
                    e.printStackTrace();
                }

                //link the cached views to the convertview
                convertView.setTag( viewHolder);

            } else {
                viewHolder=(ViewHolder) convertView.getTag();
            }

            try{
                viewHolder.shortForm.setText(vaccines.get(position).get("vaccine").toString());
                viewHolder.fullForm.setText(vaccines.get(position).get("fullName").toString());
                viewHolder.recommendationText.setText(vaccines.get(position).get("recommendation").toString());
                viewHolder.timeStatus.setText(vaccines.get(position).get("skipOrGiven").toString());
            }catch (Exception e){
                e.printStackTrace();
            }

            viewHolder.undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle("Confirm");
                    alertDialog.setMessage("Are you Sure you want to revert vaccine state");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            boolean var = DatabaseOperations.vaccineUndo(getActivity().getApplicationContext(), vaccineId[position]);
                            if (var) {
                                v.setClickable(false);
                                v.setVisibility(View.INVISIBLE);
                                Toast.makeText(context,"Vaccine State Reverted",Toast.LENGTH_LONG).show();
                                viewHolder.timeStatus.setText("Pending");
                                DatabaseOperations.setUpdateStatus(getActivity().getApplicationContext(),0);
                            }
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

            return convertView;
        }

        private class ViewHolder{
            TextView shortForm,fullForm,recommendationText,timeStatus;
            Button undoButton;
        }
    }

}
