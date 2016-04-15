package com.example.android.vaccinationplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class VerifyAccountFragment extends Fragment {

    View rootview;
    Button verifyButton;

    public VerifyAccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_verify_account, container, false);
        verifyButton = (Button)(rootview.findViewById(R.id.verifyButton));
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });

        return rootview;
    }

    public Intent verify(){
        TextView view = (TextView)(rootview.findViewById(R.id.verificationCode));
        Intent intent = getActivity().getIntent();
        String token = intent.getStringExtra(Intent.EXTRA_TEXT);
        String enteredValue = view.getText().toString();
        Intent returnIntent = new Intent();
        String pattern = "^\\d{6}$";
        if(enteredValue.matches(pattern)){
            if(enteredValue.equals(token)){

                returnIntent.putExtra(Intent.EXTRA_TEXT, "VERIFIED");
                getActivity().setResult(1, returnIntent);
                getActivity().finish();
            }else{
                view.setError("Invalid verification code");
                view.setText("");
                view.requestFocus();
            }

        }else{
            view.setError("Please enter 6 digit verification code");
            view.setText("");
            view.requestFocus();
        }

        return  returnIntent;
    }

}
