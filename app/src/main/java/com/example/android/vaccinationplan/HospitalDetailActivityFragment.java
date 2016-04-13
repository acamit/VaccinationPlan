package com.example.android.vaccinationplan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class HospitalDetailActivityFragment extends Fragment {

    Hospital hospital;
    public HospitalDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_hospital_detail, container, false);

        hospital = new Hospital("Fortis Escort","Majitha-Verka Bypass Road, Amritsar, Punjab 143004","Amritsar","0183 301 2222");

        hospital.nameHospital = (TextView) rootView.findViewById(R.id.nameHospital);
        hospital.addressHospital = (TextView) rootView.findViewById(R.id.addressHospital);
        hospital.phoneHospital = (TextView) rootView.findViewById(R.id.phoneHospital);
        hospital.callHospital = (Button) rootView.findViewById(R.id.callHospital);
        hospital.viewHospital = (Button) rootView.findViewById(R.id.viewHospital);

        /* Setting Data in the view */
        hospital.nameHospital.setText(hospital.name);
        hospital.phoneHospital.setText(hospital.phoneNo);
        hospital.addressHospital.setText(hospital.address);

        hospital.callHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if(hospital.phoneNos.length > 1){
                  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                  builder.setTitle("Select The Phone Number");
                  AlertDialog numberDialog;
                  builder.setSingleChoiceItems(hospital.phoneNos, -1, new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int number) {
                          try {
                              String uri = "tel:" + hospital.phoneNos[number].trim();
                              Intent intent = new Intent(Intent.ACTION_CALL);
                              intent.setData(Uri.parse(uri));
                              startActivity(intent);
                              dialog.cancel();
                          } catch (Exception e) {
                              //do nothing
                          }

                          }
                      });

                      numberDialog=builder.create();
                      numberDialog.show();
                  }else{
                  String uri = "tel:" + hospital.phoneNos[0].trim();
                  Intent intent = new Intent(Intent.ACTION_CALL);
                  intent.setData(Uri.parse(uri));
                  startActivity(intent);
              }



            }
        });

        hospital.viewHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:0.0,0.0?q=" + Uri.encode(hospital.name + "," + hospital.city));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });



        return rootView;
    }

    private class Hospital{
        String name, address, city, phoneNo;
        String[] phoneNos;
        TextView nameHospital,addressHospital,phoneHospital;
        Button callHospital,viewHospital;

        Hospital(String name,String address,String city,String phoneNo){
            this.name = name;
            this.address = address;
            this.city = city;
            this.phoneNo = phoneNo;
            this.phoneNos = phoneNo.split(",");
        }

    }
}
