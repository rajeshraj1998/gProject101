package com.example.gproject101;

import com.google.firebase.database.DatabaseReference;

public class user  {

    public String fullName, age, email, phone;
    boolean admin;

    public user(){

    }

    public user(String fullName,  String email, String phone, boolean admin){
        this.fullName =  fullName;
        this.phone = phone;
        this.email = email;
        this.admin = admin;

    }
}

