package com.example.android.vaccinationplan;

/**
 * Created by amit on 10/4/16.
 */
public class Child {
    String child_id ;
    String name ;
    String date_of_birth;
    String gender;
    String mother;
    String place_of_birth;
    String place_of_birthPin;
    String curr_location;
    String curr_locationPin;
    String preferred_hospital;
    String blood_group;
    String update_status;

    Child(){
        child_id="" ;
        name="" ;
        date_of_birth="";
        gender="";
        mother="";
        place_of_birth="";
        place_of_birthPin="0";
        curr_location="";
        curr_locationPin="0";
        preferred_hospital="";
        blood_group="";
        update_status="0";
    }

    Child(String id){
        /*Initialize the child from database*/
    }
    public void insertIntoChildDetails(){

    }
}


